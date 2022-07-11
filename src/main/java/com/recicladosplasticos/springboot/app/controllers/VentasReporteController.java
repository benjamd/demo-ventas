package com.recicladosplasticos.springboot.app.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recicladosplasticos.springboot.app.models.dto.VentasReporteDTO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;
import com.recicladosplasticos.springboot.app.models.entity.Documento;
import com.recicladosplasticos.springboot.app.models.service.ClienteService;
import com.recicladosplasticos.springboot.app.models.service.VentasFacturaService;
import com.recicladosplasticos.springboot.app.models.service.VentasNotaDeCreditoService;
import com.recicladosplasticos.springboot.app.models.service.VentasNotaDeDebitoService;
import com.recicladosplasticos.springboot.app.models.service.VentasReciboService;
 


@Controller
@RequestMapping("/ventas/reporte")
@SessionAttributes("reporte")
public class VentasReporteController {
	
	@Autowired
	private VentasFacturaService ventasFacturaService;
	
	@Autowired
	private VentasNotaDeCreditoService creditoService;
	
	@Autowired
	private VentasNotaDeDebitoService debitoService;
	
	@Autowired
	private VentasReciboService reciboService;
	
	@Autowired
	private ClienteService clienteService;
	
	
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value = "/buscar")
	public String searchReporteVentasDTO(Map<String, Object> model) {
		VentasReporteDTO reporte = new VentasReporteDTO();
		model.put("reporte", reporte);
	 	model.put("titulo", "Ventas - Reportes");
	 	model.put("boton", "Generar Reporte");
		return "/ventas/reporte/buscar";
	}
	
	
	
	@PostMapping(value = "/buscar-iva-ventas")
	public String reporteIvaVentasDTO( @Valid @ModelAttribute("reporte") VentasReporteDTO reporte, BindingResult result , Model model,
			@RequestParam(name = "fechainicio", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio, 
			@RequestParam(name = "fechafin", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			Authentication authentication, RedirectAttributes flash,
			HttpServletRequest request) {
		if(result.hasErrors()) {
				log.info("error redireccionando a busqueda");
				return "redirect:/ventas/reporte/buscar";
		} 
		if((fechainicio != null &&  fechafin != null ) && fechainicio.after(fechafin)) {
			if(!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/reporte/buscar";
			}
		}
		List<Documento> documentos = buscarDocumentos(reporte);
		model.addAttribute("titulo", "Reporte - IVA Ventas");
		model.addAttribute("documentos", documentos);
		model.addAttribute("fechainicio", reporte.getFechainicio());
		model.addAttribute("fechafin", reporte.getFechafin());
		return "/ventas/reporte/listar";
	}



	private List<Documento> buscarDocumentos(VentasReporteDTO reporte) {
		List<Documento> documentos = new ArrayList<Documento>();
		List<VentasFactura> ventasFacturas = null;
		List<VentasNotaDeCredito> creditos = null;
		List<VentasNotaDeDebito> debitos = null;
		if(reporte.getFechainicio() != null && reporte.getFechafin()  != null) {
			 ventasFacturas = ventasFacturaService.findFacturasEntreFechas(reporte.getFechainicio(), reporte.getFechafin());
			 creditos = creditoService.findCreditosEntreFechas(reporte.getFechainicio(), reporte.getFechafin());
			 debitos = debitoService.findDebitosEntreFechas(reporte.getFechainicio(), reporte.getFechafin());
		}
		if(reporte.getFechainicio() == null && reporte.getFechafin()  == null) {
			 ventasFacturas = ventasFacturaService.findAll();
			 creditos = creditoService.findAll();
			 debitos = debitoService.findAll();
		} 
		
		if(reporte.getFechainicio() != null && reporte.getFechafin() == null) {		 
			ventasFacturas = ventasFacturaService.findFacturasDesde(reporte.getFechainicio());
			creditos = creditoService.findCreditosDesde(reporte.getFechainicio());
			debitos = debitoService.findDebitosDesde(reporte.getFechainicio());
		}
		if(reporte.getFechainicio() == null && reporte.getFechafin() != null) {
			ventasFacturas = ventasFacturaService.findFacturasHasta(reporte.getFechafin());	
			creditos = creditoService.findCreditosHasta(reporte.getFechafin());	
			debitos = debitoService.findDebitosHasta(reporte.getFechafin());	
		}
		if(ventasFacturas != null) documentos.addAll(ventasFacturas);
		if(creditos != null) documentos.addAll(creditos);
		if(debitos != null) documentos.addAll(debitos);
		documentos.sort(Comparator.comparing(Documento::getFecha));
		return documentos;
	}
	
	
 
	
	@GetMapping(value = "/exportarreporteiva") 
	public String exportarIvaDTO( @Valid @ModelAttribute("reporte") VentasReporteDTO reporte, 
			BindingResult result , Model  model,
			@RequestParam(name = "fechainicio", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio, 
			@RequestParam(name = "fechafin", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			Authentication authentication, RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if(result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/reporte/buscar";
		} 
		if((fechainicio != null &&  fechafin != null ) && fechainicio.after(fechafin)) {
			if(!fechainicio.equals(fechafin)) {
			flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
			return "redirect:/ventas/reporte/buscar";
			}
		}
		List<Documento> documentos = buscarDocumentos(reporte);
		model.addAttribute("fechainicio", reporte.getFechainicio());
		model.addAttribute("fechafin", reporte.getFechafin());
		model.addAttribute("titulo", "Listado de Facturas");
		model.addAttribute("documentos", documentos);
		return "/ventas/reporte/exportarivaventas.xlsx";
	}
	
	@PostMapping(value = "/saldo-pendiente")
	public String saldopendienteCtaCte( @Valid @ModelAttribute("reporte") VentasReporteDTO reporte, BindingResult result , Model model,
			Authentication authentication, RedirectAttributes flash,
			HttpServletRequest request) {
		if(result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/reporte/buscar";
		} 
		Map<String, Double> saldopendiente = obtenerClientesYSaldosPendientes();
		Map<String, Double> documentos = new TreeMap<String, Double>(saldopendiente); 
		model.addAttribute("documentos", documentos);
		model.addAttribute("titulo", "Reporte Ventas - Saldos Cuenta Corriente");	
		return "/ventas/reporte/saldopendientectacte";
	}
	
	
	
	@GetMapping(value = "/saldopendientectacte") 
	public String saldopendienteCtaCte( @Valid @ModelAttribute("reporte") VentasReporteDTO reporte,BindingResult result, Map<String, Object> model,  
			Authentication authentication, RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
			response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\" ");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			Map<String, Double> saldopendiente = obtenerClientesYSaldosPendientes();
			Map<String, Double> documentos = new TreeMap<String, Double>(saldopendiente);
			model.put("tipodoc", "");
 			model.put("documentos", documentos);
 			model.put("titulo", "Reporte Ventas - Saldos Cuenta Corriente");
 			return "/ventas/reporte/saldopendientectacte.xlsx";
	}



	private Map<String, Double> obtenerClientesYSaldosPendientes() {
		Map<String, Double> saldopendiente = new HashedMap<String, Double>();
		List<Cliente> clientes = clienteService.findAll();
		clientes.sort(Comparator.comparing(Cliente::getNombre));
		for (Cliente cliente : clientes) {
			BigDecimal  saldo =  BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
			List<VentasFactura> ventasFacturas = ventasFacturaService.fetchFacturaPendienteByIdWithCliente(cliente.getId());
			if(!ventasFacturas.isEmpty()) {
				for(VentasFactura f : ventasFacturas) {
					saldo = saldo.add(f.getSaldopendiente()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}	
			List<VentasNotaDeCredito> creditos = creditoService.fetchCreditoPendienteByIdWithCliente(cliente.getId());
			if(!creditos.isEmpty()) {
				for(VentasNotaDeCredito c : creditos) {
					saldo = saldo.subtract(c.getSaldopendiente()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}	
			List<VentasNotaDeDebito> debitos = debitoService.fetchDebitoPendienteByIdWithCliente(cliente.getId());	
			if(!debitos.isEmpty()) {
				for(VentasNotaDeDebito d : debitos) {
					saldo = saldo.add(d.getSaldopendiente()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}
			List<VentasRecibo> recibos = reciboService.fetchReciboPendienteByIdWithCliente(cliente.getId());
			if(!recibos.isEmpty()) {
				for(VentasRecibo r : recibos) {
					saldo = saldo.subtract(r.getSaldopendiente()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}	
			saldopendiente.put(cliente.getNombre(), saldo.doubleValue());
		}
		return saldopendiente;
	}
	
	@PostMapping(value = "/buscar-reporte-productos-completo")
	public String buscarreporteproductoscompleto( @Valid @ModelAttribute("reporte") VentasReporteDTO reporte, BindingResult result , Model model,
			@RequestParam(name = "fechainicio", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio, 
			@RequestParam(name = "fechafin", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			Authentication authentication, RedirectAttributes flash,
			HttpServletRequest request) {
		if(result.hasErrors()) {
				log.info("error redireccionando a busqueda");
				return "redirect:/ventas/reporte/buscar";
		} 
		if((fechainicio != null &&  fechafin != null ) && fechainicio.after(fechafin)) {
			if(!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/reporte/buscar";
			}
		}
		List<Documento> documentos = buscarDocumentos(reporte);
		model.addAttribute("titulo", "Reporte Ventas - Productos y Materiales");
		model.addAttribute("documentos", documentos);
		model.addAttribute("fechainicio", reporte.getFechainicio());
		model.addAttribute("fechafin", reporte.getFechafin());
		return "/ventas/reporte/listarproductoscompleto";
	}
	
	@GetMapping(value = "/exportar-reporte-productos-completo") 
	public String exportarreporteproductoscompleto( @Valid @ModelAttribute("reporte") VentasReporteDTO reporte, 
			BindingResult result , Model  model,
			@RequestParam(name = "fechainicio", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio, 
			@RequestParam(name = "fechafin", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			Authentication authentication, RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if(result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/reporte/buscar";
		} 
		if((fechainicio != null &&  fechafin != null ) && fechainicio.after(fechafin)) {
			if(!fechainicio.equals(fechafin)) {
			flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
			return "redirect:/ventas/reporte/buscar";
			}
		}
		List<Documento> documentos = buscarDocumentos(reporte);
		model.addAttribute("fechainicio", reporte.getFechainicio());
		model.addAttribute("fechafin", reporte.getFechafin());
		model.addAttribute("titulo", "Reporte Ventas - Productos y Materiales");
		model.addAttribute("documentos", documentos);
		return "/ventas/reporte/exportarreporteproductos.xlsx";
	}

}
