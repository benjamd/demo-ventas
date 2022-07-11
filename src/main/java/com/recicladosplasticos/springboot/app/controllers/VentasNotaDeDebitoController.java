package com.recicladosplasticos.springboot.app.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeDebitoDetalleNuevaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeDebitoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeDebitoNuevaDTO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.service.ClienteService;
import com.recicladosplasticos.springboot.app.models.service.PuntoDeVentaService;
import com.recicladosplasticos.springboot.app.models.service.VentasNotaDeDebitoService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/ventas/debito")
@SessionAttributes("debito")
public class VentasNotaDeDebitoController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PuntoDeVentaService puntoDeVentaService;

	@Autowired
	private VentasNotaDeDebitoService notaDeDebitoService;
	

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ModelAttribute("clientes")
	public List<Cliente> clientes() {
		List<Cliente> clientes = clienteService.findAll();
		clientes.sort(Comparator.comparing(Cliente::getNombre));
		return clientes;
	}

	@ModelAttribute("puntosdeventa")
	public List<PuntoDeVenta> puntosdeventa() {
		List<PuntoDeVenta> puntosDeVenta = puntoDeVentaService.findByDocumento("Nota de Débito");
		puntosDeVenta.sort(Comparator.comparing(PuntoDeVenta::getNombre));
		return puntosDeVenta;
	}

	@ModelAttribute("condicionesdeventa")
	public List<String> condicionesdeventa() {
		return Arrays.asList("Contado", "Cuenta Corriente", "Cheque", "ECHEQ", "Tarjeta de Débito",
				"Tarjeta de Crédito", "Ticket", "Otra");
	}
	
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		List<Producto> productos = clienteService.findByNombre(term);
		productos.sort(Comparator.comparing(Producto::getNombre));
		return productos;
	}


	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		VentasNotaDeDebito debito = clienteService.findDebitoById(id);
		if (debito == null) {
			flash.addFlashAttribute("error", "La Nota de Crédito no existe en la base de datos!");
			return "redirect:/ventas/debito/listar";
		}
		model.addAttribute("debito", debito);
		model.addAttribute("titulo", "NOTA DE DÉBITO DE VENTA");
		DecimalFormat df = new DecimalFormat("00000000");
		model.addAttribute("prefijonumero",
				"Numero: " + debito.getPrefijo().toString() + " - " + df.format(debito.getNumero()));
		return "/ventas/debito/ver";
	}

	@GetMapping("/nuevo/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/ventas/debito/buscar";
		}
		VentasNotaDeDebito debito = new VentasNotaDeDebito();
		debito.setCliente(cliente);
		model.put("debito", debito);
		model.put("titulo", "Crear Nota de Dédito");
		return "/ventas/debito/nuevo";
	}

	@GetMapping("/nuevodetalle")
	public String crearDebitoDetalle(Model model, RedirectAttributes flash) {
		PuntoDeVenta puntosDeVenta = puntoDeVentaService.findOne(1L);
		if (puntosDeVenta == null) {
			flash.addFlashAttribute("error", "No existen puntos de venta en la base de datos");
			return "redirect:/ventas/debito/buscar";
		}
		VentasNotaDeDebitoDetalleNuevaDTO debito = new VentasNotaDeDebitoDetalleNuevaDTO();
		debito.addPuntoDeVentaDisponible(puntosDeVenta);
		model.addAttribute("debito", debito);
		model.addAttribute("titulo", "Crear Nota de Débito Detalle");
		return "/ventas/debito/nuevodetalle";
	}

	@GetMapping("/nuevo")
	public String crearDebito(Model model, RedirectAttributes flash) {
		PuntoDeVenta puntosDeVenta = puntoDeVentaService.findOne(1L);
		if (puntosDeVenta == null) {
			flash.addFlashAttribute("error", "No existen puntos de venta en la base de datos");
			return "redirect:/ventas/debito/buscar";
		}
		VentasNotaDeDebitoNuevaDTO debito = new VentasNotaDeDebitoNuevaDTO();
		debito.addPuntoDeVentaDisponible(puntosDeVenta);
		model.addAttribute("debito", debito);
		model.addAttribute("titulo", "Crear Nota de Débito");
		return "/ventas/debito/nuevo";
	}


	@PostMapping("/nuevo")
	public String guardarDebito(@Valid @ModelAttribute("debito") VentasNotaDeDebitoNuevaDTO debitoDTO, BindingResult result,
			Model model, @RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "price[]", required = false) Double[] precio,
			@RequestParam(name = "cantidad[]", required = false) Double[] cantidad,
			@RequestParam(name = "descuento[]", required = false) Double[] descuento,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId, @RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en la nota de débito");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Nota de Dédito con errores");
			model.addAttribute("titulo", "Crear Nota de Dédito");
			return "/ventas/debito/nuevo";
		}
		if (itemId == null || itemId.length == 0) {
			log.info("Hay errores en la Nota de Débito: la Nota de Débito no tiene lineas");
			model.addAttribute("titulo", "Crear Nota de Débito");
			model.addAttribute("error", "Error: La Nota de Débito NO puede no tener líneas!");
			return "/ventas/debito/nuevo";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasNotaDeDebito debitoExistente = null;
		debitoExistente = notaDeDebitoService.findDebitoByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		if (debitoExistente != null) {
			log.info("Nota de Débito nueva: La Nota de Débito ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error",
					"Nota de Débito Duplicada! No ha sido cargado, ya existe una Nota de Débito con mismo prefijo y numero");
			VentasNotaDeDebitoNuevaDTO debito = new VentasNotaDeDebitoNuevaDTO();
			model.addAttribute("debito", debito);
			model.addAttribute("titulo", "Crear Nota de Débito");
			return "redirect:/ventas/debito/nuevo";
		}
		VentasNotaDeDebito debito = crearNotaDeDebitoConItems(debitoDTO, itemId, precio, cantidad, descuento, fecha);
		log.info("Creando Nota de Débito con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		debito.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveDebitoProductos(puntoDeVentaId, debito);
		if (puntoDeVenta == null) {
			log.info("Nota de Débito nueva: No existe el punto de venta para la nota de crédito  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para la nota de débito  que desea cargar");
			VentasNotaDeDebitoNuevaDTO debitonuevo = new VentasNotaDeDebitoNuevaDTO();
			model.addAttribute("debito", debitonuevo);
			model.addAttribute("titulo", "Crear Nota de Débito");
			return "redirect:/ventas/debito/nuevo";
		}
		status.setComplete();
		log.info("se creo ND exitosamente");
		flash.addFlashAttribute("success", "Nota de Débito creada con éxito!");
		return "redirect:/index";
	}

	private VentasNotaDeDebito crearNotaDeDebitoConItems(VentasNotaDeDebitoNuevaDTO debitoDTO, Long[] itemId, Double[] precio,
			Double[] cantidad, Double[] descuento, Date fecha) {
		VentasNotaDeDebito debito = new VentasNotaDeDebito();
		debito.setCliente(debitoDTO.getCliente());
		debito.setObservacion(debitoDTO.getObservacion());
		debito.setCondventa(debitoDTO.getCondventa());
		debito.setAlicuotaiva(0.21);
		if (fecha == null) {
			debito.setFecha(new Date());
		} else {
			debito.setFecha(fecha);
		}
		BigDecimal importe = calcularImporteConItems(itemId, precio, cantidad, descuento, debito);
		debito.setImporte(importe);
		BigDecimal importeIVA = importe.multiply(BigDecimal.valueOf(0.21)).setScale(2, RoundingMode.HALF_EVEN);
		debito.setImporteiva(importeIVA);
		BigDecimal importeTotal = importe.add(importeIVA).setScale(2, RoundingMode.HALF_EVEN);
		debito.setImportetotal(importeTotal);
		debito.setSaldopendiente(importeTotal);
		return debito;
	}

	private BigDecimal calcularImporteConItems(Long[] itemId, Double[] precio, Double[] cantidad, Double[] descuento,
			VentasNotaDeDebito debito) {
		BigDecimal importe = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemVentasNotaDeDebito linea = crearItemNotaDeDebito(producto, precio, cantidad, descuento, i);
			BigDecimal iNeto = linea.getCantidad()
					.multiply(linea.getPrecio()
							.multiply(BigDecimal.valueOf(1).subtract(linea.getDescuento().divide(BigDecimal.valueOf(100)))))
					.setScale(2, RoundingMode.HALF_EVEN);
			linea.setImporteNeto(iNeto);
			debito.addItemDebito(linea);
			importe = importe.add(iNeto);
		}
		return importe;
	}
	
	private ItemVentasNotaDeDebito crearItemNotaDeDebito(Producto producto, Double[] precio, Double[] cantidad, Double[] descuento,
			int i) {
		ItemVentasNotaDeDebito linea = new ItemVentasNotaDeDebito();
		BigDecimal bgCantidad = BigDecimal.valueOf(cantidad[i]).setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal bgPrecio = BigDecimal.valueOf(precio[i]).setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal bgDescuento = BigDecimal.valueOf(descuento[i]).setScale(2, RoundingMode.HALF_EVEN);
		linea.setProducto(producto);
		linea.setCantidad(bgCantidad);
		linea.setPrecio(bgPrecio);
		linea.setDescuento(bgDescuento);
		return linea;
	}

	@PostMapping("/nuevodetalle")
	public String guardarDebitoDetalle(@Valid @ModelAttribute("debito") VentasNotaDeDebitoDetalleNuevaDTO debitoDTO,
			BindingResult result, Model model,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId, 
			@RequestParam(name = "iva") Double iva,
			@RequestParam(name = "pVenta") Long puntoDeVentaId, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en la Nota de Débito Detalle");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Nota de Débito Detalle con errores");
			model.addAttribute("titulo", "Nota de Débito Detalle");
			return "/ventas/debito/nuevodetalle";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasNotaDeDebito debitoExistente = null;
		debitoExistente = notaDeDebitoService.findDebitoByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		if (debitoExistente != null) {
			log.info("Nota de Débito nueva: La Nota de Débito ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error",
					"Nota de Débito Duplicada! No ha sido cargado, ya existe una Nota de Débito con mismo prefijo y numero");
			VentasNotaDeDebitoDetalleNuevaDTO debito = new VentasNotaDeDebitoDetalleNuevaDTO();
			model.addAttribute("debito", debito);
			model.addAttribute("titulo", "Crear Nota de Débito");
			return "redirect:/ventas/debito/nuevodetalle";
		}
		VentasNotaDeDebito debito = crearDebitoDetalle(debitoDTO, fecha, iva);
		log.info("Creando Nota de Crédito con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		debito.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveDebito(puntoDeVentaId, debito);
		if (puntoDeVenta == null) {
			log.info("Nota de Débito nueva: No existe el punto de venta para la nota de crédito  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para la nota de débito  que desea cargar");
			VentasNotaDeDebitoDetalleNuevaDTO debitonuevo = new VentasNotaDeDebitoDetalleNuevaDTO();
			model.addAttribute("debito", debitonuevo);
			model.addAttribute("titulo", "Crear Nota de Débito");
			return "redirect:/ventas/debito/nuevodetalle";
		}
		status.setComplete();
		log.info("se creo Nota de Débito Detalle exitosamente");
		flash.addFlashAttribute("success", "Nota de Débito Detalle creada con éxito!");
		return "redirect:/index";
	}

	private VentasNotaDeDebito crearDebitoDetalle(VentasNotaDeDebitoDetalleNuevaDTO debitoDTO, Date fecha, Double iva) {
		VentasNotaDeDebito debito = new VentasNotaDeDebito();
		debito.setCliente(debitoDTO.getCliente());
		debito.setObservacion(debitoDTO.getObservacion());
		debito.setDetalle(debitoDTO.getDetalle());
		debito.setAlicuotaiva(iva);
		debito.setCondventa(debitoDTO.getCondventa());
		if (fecha == null) {
			debito.setFecha(new Date());
		} else {
			debito.setFecha(fecha);
		}
		Double importeDTO = debitoDTO.getImporte();
		BigDecimal importeBase = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		if (importeDTO != null)
			importeBase = BigDecimal.valueOf(importeDTO).setScale(2, RoundingMode.HALF_EVEN);
		debito.setImporte(importeBase);
		BigDecimal importeIVA = importeBase.multiply(BigDecimal.valueOf(iva)).setScale(2, RoundingMode.HALF_EVEN);
		debito.setImporteiva(importeIVA);
		BigDecimal importeTotal = importeBase.add(importeIVA).setScale(2, RoundingMode.HALF_EVEN);
		debito.setImportetotal(importeTotal);
		debito.setSaldopendiente(importeTotal);
		return debito;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash,
			Authentication authentication) {
		if (id > 0) {
			VentasNotaDeDebito debito = clienteService.findDebitoById(id);
			if (debito != null) {
				clienteService.deleteDebito(debito, id);
				flash.addFlashAttribute("success", "La Nota de Débito fue eliminada con éxito!");
				return "redirect:/ventas/debito/listar";
			}
		}
		flash.addFlashAttribute("error", "La Nota de Débito no existe en la base de datos, no se pudo eliminar!");
		return "redirect:/ventas/debito/listar";
	}

	@GetMapping(value = "/listar")
	public String listar(@Valid @ModelAttribute("debito") VentasNotaDeDebitoBusquedaDTO debitoDTO,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50,	Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasNotaDeDebito> debitos = notaDeDebitoService.buscarVentasNotasDeDebito(debitoDTO, pageRequest);
		PageRender<VentasNotaDeDebito> pageRender = new PageRender<VentasNotaDeDebito>("/ventas/debito/listar", debitos);
		model.addAttribute("titulo", "Listado de Notas de Débito");
		model.addAttribute("debitos", debitos);
		model.addAttribute("page", pageRender);
		return "/ventas/debito/listar";
	}

	@RequestMapping(value = "/buscar")
	public String searchDebito(Map<String, Object> model) {
		VentasNotaDeDebitoBusquedaDTO debito = new VentasNotaDeDebitoBusquedaDTO();
		model.put("debito", debito);
		model.put("titulo", "Buscar Nota de Débito");
		return "/ventas/debito/buscar";
	}

	@GetMapping(value = "/exportar")
	public String exportarDebito(@Valid @ModelAttribute("debito") VentasNotaDeDebitoBusquedaDTO debitoDTO, BindingResult result,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"debito_view.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/debito/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/debito/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/debito/buscar";
		}
		List<VentasNotaDeDebito> debitos = notaDeDebitoService.buscarVentasNotasDeDebito(debitoDTO);
		model.addAttribute("fechainicio", debitoDTO.getFechainicio());
		model.addAttribute("fechafin", debitoDTO.getFechafin());
		model.addAttribute("titulo", "Listado de Notas de Débito");
		model.addAttribute("debitos", debitos);
		return "/ventas/debito/exportar.xlsx";

	}

	@PostMapping(value = "/buscar")
	public String resultadoDebito(@Valid @ModelAttribute("debito") VentasNotaDeDebitoBusquedaDTO debitoDTO,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request) {
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/debito/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/debito/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/debito/buscar";
		}
		Pageable pageRequest = PageRequest.of(page, 50,	Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasNotaDeDebito> debitos = notaDeDebitoService.buscarVentasNotasDeDebito(debitoDTO, pageRequest);
		PageRender<VentasNotaDeDebito> pageRender = new PageRender<VentasNotaDeDebito>("/ventas/debito/listar", debitos);
		model.addAttribute("titulo", "Listado de Notas de Débito");
		model.addAttribute("debitos", debitos);
		model.addAttribute("page", pageRender);
		return "/ventas/debito/listar";
	}

 

}
