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
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeCreditoDetalleNuevaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeCreditoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeCreditoNuevaDTO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.service.ClienteService;
import com.recicladosplasticos.springboot.app.models.service.PuntoDeVentaService;
import com.recicladosplasticos.springboot.app.models.service.VentasNotaDeCreditoService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/ventas/credito")
@SessionAttributes("credito")
public class VentasNotaDeCreditoController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PuntoDeVentaService puntoDeVentaService;

	@Autowired
	private VentasNotaDeCreditoService notaDeCredito;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ModelAttribute("credito")
	public VentasNotaDeCredito populateFormNotaDeCredito() {
		return new VentasNotaDeCredito();
	}

	@ModelAttribute("clientes")
	public List<Cliente> clientes() {
		List<Cliente> clientes = clienteService.findAll();
		clientes.sort(Comparator.comparing(Cliente::getNombre));
		return clientes;
	}

	@ModelAttribute("puntosdeventa")
	public List<PuntoDeVenta> puntosdeventa() {
		List<PuntoDeVenta> puntosDeVenta = puntoDeVentaService.findByDocumento("Nota de Crédito");
		puntosDeVenta.sort(Comparator.comparing(PuntoDeVenta::getNombre));
		return puntosDeVenta;
	}

	@ModelAttribute("condicionesdeventa")
	public List<String> condicionesdeventa() {
		return Arrays.asList("Contado", "Cuenta Corriente", "Cheque", "ECHEQ", "Tarjeta de Débito",
				"Tarjeta de Crédito", "Ticket", "Otra");
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		VentasNotaDeCredito credito = clienteService.findCreditoById(id);
		if (credito == null) {
			flash.addFlashAttribute("error", "La Nota de Crédito no existe en la base de datos!");
			return "redirect:/ventas/credito/listar";
		}
		model.addAttribute("credito", credito);
		model.addAttribute("titulo", "NOTA DE CRÉDITO DE VENTA");
		DecimalFormat df = new DecimalFormat("00000000");
		model.addAttribute("prefijonumero",
				"Numero: " + credito.getPrefijo().toString() + " - " + df.format(credito.getNumero()));
		return "/ventas/credito/ver";
	}

	@GetMapping("/nuevo")
	public String crearCredito(Model model, RedirectAttributes flash) {
		PuntoDeVenta puntosDeVenta = puntoDeVentaService.findOne(1L);
		if (puntosDeVenta == null) {
			flash.addFlashAttribute("error", "No existen puntos de venta en la base de datos");
			return "redirect:/ventas/credito/buscar";
		}
		VentasNotaDeCreditoNuevaDTO credito = new VentasNotaDeCreditoNuevaDTO();
		credito.addPuntoDeVentaDisponible(puntosDeVenta);
		model.addAttribute("credito", credito);
		model.addAttribute("titulo", "Crear Nota de Crédito");
		return "/ventas/credito/nuevo";
	}

	@GetMapping("/nuevodetalle")
	public String crearCreditoDetalle(Model model, RedirectAttributes flash) {
		PuntoDeVenta puntosDeVenta = puntoDeVentaService.findOne(1L);
		if (puntosDeVenta == null) {
			flash.addFlashAttribute("error", "No existen puntos de venta en la base de datos");
			return "redirect:/ventas/credito/buscar";
		}
		VentasNotaDeCreditoDetalleNuevaDTO credito = new VentasNotaDeCreditoDetalleNuevaDTO();
		credito.addPuntoDeVentaDisponible(puntosDeVenta);
		model.addAttribute("credito", credito);
		model.addAttribute("titulo", "Crear Nota de Crédito Detalle");
		return "/ventas/credito/nuevodetalle";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		List<Producto> productos = clienteService.findByNombre(term);
		productos.sort(Comparator.comparing(Producto::getNombre));
		return productos;
	}

	@PostMapping("/nuevo")
	public String guardarCredito(@Valid @ModelAttribute("credito") VentasNotaDeCreditoNuevaDTO creditoDTO,
			BindingResult result, Model model, @RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "price[]", required = false) Double[] precio,
			@RequestParam(name = "cantidad[]", required = false) Double[] cantidad,
			@RequestParam(name = "descuento[]", required = false) Double[] descuento,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId, @RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en la nota de crédito (ventas)");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Nota de Crédito con errores");
			model.addAttribute("titulo", "Crear Nota de Crédito");
			return "/ventas/credito/nuevo";
		}
		if (itemId == null || itemId.length == 0) {
			flash.addFlashAttribute("error",
					"Hay errores en la Nota de Crédito: la Nota de Crédito no tiene lineas! Debe cargar Productos");
			log.info("Hay errores en la Nota de Crédito: la Nota de Crédito no tiene lineas");
			model.addAttribute("titulo", "Crear Nota de Crédito");
			model.addAttribute("error", "Error: La Nota de Crédito no puede no tener líneas! Debe cargar Productos");
			return "/ventas/credito/nuevo";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasNotaDeCredito creditoExistente = null;
		creditoExistente = notaDeCredito.findCreditoByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		if (creditoExistente != null) {
			log.info("Nota de Crédito nueva: La Nota de Crédito ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error",
					"Nota de Crédito Duplicada! No ha sido cargado, ya existe una Nota de Crédito con mismo prefijo y numero");
			VentasNotaDeCreditoNuevaDTO credito = new VentasNotaDeCreditoNuevaDTO();
			model.addAttribute("credito", credito);
			model.addAttribute("titulo", "Crear Nota de Crédito");
			return "redirect:/ventas/credito/nuevo";
		}
		VentasNotaDeCredito credito = crearNotaDeCredito(creditoDTO, itemId, precio, cantidad, descuento, fecha);
		Cliente cliente = clienteService.findOne(clienteId);
		credito.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveCreditoProductos(puntoDeVentaId, credito);
		if (puntoDeVenta == null) {
			log.info("Nota de Crédito nueva: No existe el punto de venta para la nota de crédito  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para la nota de crédito  que desea cargar");
			VentasNotaDeCreditoNuevaDTO creditonuevo = new VentasNotaDeCreditoNuevaDTO();
			model.addAttribute("credito", creditonuevo);
			model.addAttribute("titulo", "Crear Nota de Crédito");
			return "redirect:/ventas/credito/nuevo";
		}
		status.setComplete();
		log.info("se creo Nota de Crédito exitosamente");
		flash.addFlashAttribute("success", "Nota de Crédito creada con éxito!");
		return "redirect:/index";
	}

	private VentasNotaDeCredito crearNotaDeCredito(VentasNotaDeCreditoNuevaDTO creditoDTO, Long[] itemId,
			Double[] precio, Double[] cantidad, Double[] descuento, Date fecha) {
		VentasNotaDeCredito credito = new VentasNotaDeCredito();
		credito.setCliente(creditoDTO.getCliente());
		credito.setObservacion(creditoDTO.getObservacion());
		credito.setCondventa(creditoDTO.getCondventa());
		credito.setAlicuotaiva(0.21);
		if (fecha == null) {
			credito.setFecha(new Date());
		} else {
			credito.setFecha(fecha);
		}
		BigDecimal importe = calcularImporteConItems(itemId, precio, cantidad, descuento, credito);

		credito.setImporte(importe);
		BigDecimal importeIVA = importe.multiply(BigDecimal.valueOf(0.21)).setScale(2, RoundingMode.HALF_EVEN);
		credito.setImporteiva(importeIVA);
		BigDecimal importeTotal = importe.add(importeIVA).setScale(2, RoundingMode.HALF_EVEN);
		credito.setImportetotal(importeTotal);
		credito.setSaldopendiente(importeTotal);
		return credito;
	}

	private BigDecimal calcularImporteConItems(Long[] itemId, Double[] precio, Double[] cantidad, Double[] descuento,
			VentasNotaDeCredito credito) {
		BigDecimal importe = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemVentasNotaDeCredito linea = crearItemNotaDeCredito(producto, precio, cantidad, descuento, i);
			BigDecimal dto = BigDecimal.valueOf(1).subtract(linea.getDescuento().divide(BigDecimal.valueOf(100))).setScale(2,
					RoundingMode.HALF_EVEN);
			BigDecimal precioneto = linea.getPrecio().multiply(dto).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal iNeto = linea.getCantidad().multiply(precioneto).setScale(2, RoundingMode.HALF_EVEN);
			linea.setImporteNeto(iNeto);
			credito.addItemCredito(linea);
			importe = importe.add(iNeto).setScale(2, RoundingMode.HALF_EVEN);
		}
		return importe;
	}
	
	private ItemVentasNotaDeCredito crearItemNotaDeCredito(Producto producto, Double[] precio, Double[] cantidad, Double[] descuento,
			int i) {
		ItemVentasNotaDeCredito linea = new ItemVentasNotaDeCredito();
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
	public String guardarCreditoDetalle(@Valid @ModelAttribute("credito") VentasNotaDeCreditoDetalleNuevaDTO creditoDTO,
			BindingResult result, Model model,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId, @RequestParam(name = "iva") Double iva,
			@RequestParam(name = "pVenta") Long puntoDeVentaId, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en la Nota de Crédito Detalle");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Nota de Crédito Detalle con errores");
			model.addAttribute("titulo", "Nota de Crédito Detalle");
			return "/ventas/credito/nuevodetalle";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasNotaDeCredito creditoExistente = null;
		creditoExistente = notaDeCredito.findCreditoByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		if (creditoExistente != null) {
			log.info("Nota de Crédito nueva: La Nota de Crédito ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error",
					"Nota de Crédito Duplicada! No ha sido cargado, ya existe una Nota de Crédito con mismo prefijo y numero");
			VentasNotaDeCreditoDetalleNuevaDTO credito = new VentasNotaDeCreditoDetalleNuevaDTO();
			model.addAttribute("credito", credito);
			model.addAttribute("titulo", "Crear Nota de Crédito");
			return "redirect:/ventas/credito/nuevodetalle";
		}
		VentasNotaDeCredito credito = crearCreditoDetalle(creditoDTO, fecha, iva);
		log.info("Creando Nota de Crédito con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		credito.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveCredito(puntoDeVentaId, credito);
		if (puntoDeVenta == null) {
			log.info("Nota de Crédito nueva: No existe el punto de venta para la nota de crédito  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para la nota de crédito  que desea cargar");
			VentasNotaDeCreditoDetalleNuevaDTO creditonueva = new VentasNotaDeCreditoDetalleNuevaDTO();
			model.addAttribute("credito", creditonueva);
			model.addAttribute("titulo", "Crear Nota de Crédito");
			return "redirect:/ventas/credito/nuevodetalle";
		}
		status.setComplete();
		log.info("se creo Nota de Crédito Detalle exitosamente");
		flash.addFlashAttribute("success", "Nota de Crédito Detalle creada con éxito!");
		return "redirect:/index";
	}

	private VentasNotaDeCredito crearCreditoDetalle(VentasNotaDeCreditoDetalleNuevaDTO creditoDTO, Date fecha,
			Double iva) {
		VentasNotaDeCredito credito = new VentasNotaDeCredito();
		credito.setCliente(creditoDTO.getCliente());
		credito.setObservacion(creditoDTO.getObservacion());
		credito.setDetalle(creditoDTO.getDetalle());
		credito.setAlicuotaiva(iva);
		credito.setCondventa(creditoDTO.getCondventa());
		if (fecha == null) {
			credito.setFecha(new Date());
		} else {
			credito.setFecha(fecha);
		}
		Double importeDTO = creditoDTO.getImporte();
		BigDecimal importeBase = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		if (importeDTO != null)
			importeBase = BigDecimal.valueOf(importeDTO).setScale(2, RoundingMode.HALF_EVEN);
		credito.setImporte(importeBase);
		BigDecimal importeIVA = importeBase.multiply(BigDecimal.valueOf(iva)).setScale(2, RoundingMode.HALF_EVEN);
		credito.setImporteiva(importeIVA);
		BigDecimal importeTotal = importeBase.add(importeIVA).setScale(2, RoundingMode.HALF_EVEN);
		credito.setImportetotal(importeTotal);
		credito.setSaldopendiente(importeTotal);
		return credito;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash,
			Authentication authentication) {
		if (id > 0) {
			VentasNotaDeCredito credito = clienteService.findCreditoById(id);
			if (credito != null) {
				clienteService.deleteCredito(credito, id);
				flash.addFlashAttribute("success", "Nota de Crédito eliminada con éxito!");
				return "redirect:/ventas/credito/listar";
			}
		}
		flash.addFlashAttribute("error", "La Nota de Crédito no existe en la base de datos, no se pudo eliminar!");
		return "redirect:/ventas/credito/listar";
	}

	@GetMapping(value = "/listar")
	public String listar(@Valid @ModelAttribute("credito") VentasNotaDeCreditoBusquedaDTO credito,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50,	Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasNotaDeCredito> creditos = notaDeCredito.buscarNotasDeCredito(credito, pageRequest);
		PageRender<VentasNotaDeCredito> pageRender = new PageRender<VentasNotaDeCredito>("/ventas/credito/listar", creditos);
		model.addAttribute("titulo", "Listado de Notas de Crédito");
		model.addAttribute("creditos", creditos);
		model.addAttribute("page", pageRender);
		return "/ventas/credito/listar";
	}

	// ===================================== BUSCAR
	// ===========================================

	@RequestMapping(value = "/buscar")
	public String searchCredito(Map<String, Object> model) {
		VentasNotaDeCreditoBusquedaDTO credito = new VentasNotaDeCreditoBusquedaDTO();
		model.put("credito", credito);
		model.put("titulo", "Buscar Nota de Crédito");
		return "/ventas/credito/buscar";
	}

	@GetMapping(value = "/exportar")
	public String exportarCredito(@Valid @ModelAttribute("credito") VentasNotaDeCreditoBusquedaDTO creditoDTO, BindingResult result,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"credito_view.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/credito/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/credito/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/credito/buscar";
		}
		List<VentasNotaDeCredito> creditos = notaDeCredito.buscarNotasDeCredito(creditoDTO);
		model.addAttribute("fechainicio", creditoDTO.getFechainicio());
		model.addAttribute("fechafin", creditoDTO.getFechafin());
		model.addAttribute("titulo", "Listado de Notas de Crédito");
		model.addAttribute("creditos", creditos);
		return "/ventas/credito/exportar.xlsx";
	}

	@PostMapping(value = "/buscar")
	public String resultadoCredito(@Valid @ModelAttribute("credito") VentasNotaDeCreditoBusquedaDTO credito,
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
			return "redirect:/ventas/credito/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/credito/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/credito/buscar";
		}
		Pageable pageRequest = PageRequest.of(page, 50,	Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasNotaDeCredito> creditos = notaDeCredito.buscarNotasDeCredito(credito, pageRequest);
		PageRender<VentasNotaDeCredito> pageRender = new PageRender<VentasNotaDeCredito>("/ventas/credito/listar", creditos);
		model.addAttribute("titulo", "Listado de Notas de Crédito");
		model.addAttribute("creditos", creditos);
		model.addAttribute("page", pageRender);
		return "/ventas/credito/listar";
	}

}
