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
import javax.validation.constraints.Future;
import javax.validation.constraints.PastOrPresent;

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

import com.recicladosplasticos.springboot.app.models.dto.VentasPresupuestoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasPresupuestoDetalleNuevoDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasPresupuestoNuevoDTO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasPresupuesto;
import com.recicladosplasticos.springboot.app.models.entity.VentasPresupuesto;
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.service.ClienteService;
import com.recicladosplasticos.springboot.app.models.service.VentasPresupuestoService;
import com.recicladosplasticos.springboot.app.models.service.PuntoDeVentaService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/ventas/presupuesto")
@SessionAttributes("presupuesto")
public class VentasPresupuestoController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PuntoDeVentaService puntoDeVentaService;

	@Autowired
	private VentasPresupuestoService ventasPresupuestoService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ModelAttribute("clientes")
	public List<Cliente> clientes() {
		List<Cliente> clientes = clienteService.findAll();
		clientes.sort(Comparator.comparing(Cliente::getNombre));
		return clientes;
	}

	@ModelAttribute("puntosdeventa")
	public List<PuntoDeVenta> puntosdeventa() {
		List<PuntoDeVenta> puntosDeVenta = puntoDeVentaService.findByDocumento("Presupuesto de Venta");
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
		VentasPresupuesto presupuesto = clienteService.findOrdenDeEntregaById(id);
		if (presupuesto == null) {
			flash.addFlashAttribute("error", "El Presupuesto no existe en la base de datos!");
			return "redirect:/ventas/presupuesto/listar";
		}
		model.addAttribute("presupuesto", presupuesto);
		model.addAttribute("titulo", "PRESUPUESTO DE VENTA");
		DecimalFormat df = new DecimalFormat("00000000");
		model.addAttribute("prefijonumero",
				"Numero: " + presupuesto.getPrefijo().toString() + " - " + df.format(presupuesto.getNumero()));
		return "/ventas/presupuesto/ver";
	}

	// NUEVA VERSION
	@GetMapping("/nuevo")
	// public String crearNuevaOrdenDeEntrega( Map<String, Object> model,
	// RedirectAttributes flash) {
	public String crearNuevaOrdenDeEntrega(Model model, RedirectAttributes flash) {
		PuntoDeVenta puntosDeVenta = puntoDeVentaService.findOne(1L);
		if (puntosDeVenta == null) {
			flash.addFlashAttribute("error", "No existen puntos de venta en la base de datos");
			return "redirect:/ventas/presupuesto/buscar";
		}
		VentasPresupuestoNuevoDTO presupuesto = new VentasPresupuestoNuevoDTO();
		presupuesto.addPuntoDeVentaDisponible(puntosDeVenta);
		model.addAttribute("presupuesto", presupuesto);
		model.addAttribute("titulo", "Crear presupuesto");
		return "/ventas/presupuesto/nuevo";
	}

	@GetMapping("/nuevodetalle")
	public String crearNuevaOrdenDeEntregaDetalleDTO(Model model, RedirectAttributes flash) {
		PuntoDeVenta puntosDeVenta = puntoDeVentaService.findOne(1L);
		if (puntosDeVenta == null) {
			flash.addFlashAttribute("error", "No existen puntos de venta en la base de datos");
			return "redirect:/ventas/presupuesto/buscar";
		}
		VentasPresupuestoDetalleNuevoDTO presupuesto = new VentasPresupuestoDetalleNuevoDTO();
		presupuesto.addPuntoDeVentaDisponible(puntosDeVenta);
		model.addAttribute("presupuesto", presupuesto);
		model.addAttribute("titulo", "Crear presupuesto");
		return "/ventas/presupuesto/nuevodetalle";
	}

	@PostMapping("/nuevo")
	public String guardarPresupuesto(@Valid @ModelAttribute("presupuesto") VentasPresupuestoNuevoDTO presupuestoDTO,
			BindingResult result, Model model, @RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "price[]", required = false) Double[] precio,
			@RequestParam(name = "cantidad[]", required = false) Double[] cantidad,
			@RequestParam(name = "descuento[]", required = false) Double[] descuento,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId, @RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en el Presupuesto de Venta");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Presupuesto de Venta con errores");
			model.addAttribute("titulo", "Crear Presupuesto de Venta");
			return "/ventas/presupuesto/nuevo";
		}
		if (itemId == null || itemId.length == 0) {
			log.info(
					"Hay errores en el  Presupuesto de Venta: el  Presupuesto de Venta no tiene líneas!. Debe cargar Productos");
			model.addAttribute("titulo", "Crear  Presupuesto de Venta");
			model.addAttribute("error",
					"Error: El  Presupuesto de Venta no puede no tener líneas!. Debe cargar Productos");
			return "/ventas/presupuesto/nuevo";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasPresupuesto presupuestoExistente = null;
		presupuestoExistente = ventasPresupuestoService.findOrdenDeEntregaByPrefijoAndNumero(ptovta.getPrefijo(),
				ptovta.getNumero());
		if (presupuestoExistente != null) {
			log.info("Presupuesto nueva: El Presupuesto ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error",
					"Presupuesto Duplicado! No ha sido cargado, ya existe un Presupuesto con mismo prefijo y numero");
			VentasPresupuestoNuevoDTO presupuesto = new VentasPresupuestoNuevoDTO();
			model.addAttribute("presupuesto", presupuesto);
			model.addAttribute("titulo", "Crear Presupuesto");
			return "redirect:/ventas/presupuesto/nuevo";
		}

		VentasPresupuesto presupuesto = crearPrespuestoConItems(presupuestoDTO, itemId, precio, cantidad, descuento,fecha);
		log.info("Creando Presupuesto de Venta con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		presupuesto.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveOrdenDeEntregaProductos(puntoDeVentaId, presupuesto);
		if (puntoDeVenta == null) {
			log.info("Presupuesto nuevo: No existe el punto de venta para El Presupuesto de entrega  que desea cargar");
			flash.addFlashAttribute("error",
					"No existe el punto de venta para El Presupuesto de entrega  que desea cargar");
			VentasPresupuestoNuevoDTO presupuestoNuevo = new VentasPresupuestoNuevoDTO();
			model.addAttribute("presupuesto", presupuestoNuevo);
			model.addAttribute("titulo", "Crear Presupuesto");
			return "redirect:/ventas/presupuesto/nuevo";
		}
		status.setComplete();
		log.info("Se creo Presupuesto exitosamente");
		flash.addFlashAttribute("success", "Presupuesto creada con éxito!");
		return "redirect:/index";
	}

	private VentasPresupuesto crearPrespuestoConItems(VentasPresupuestoNuevoDTO presupuestoDTO, Long[] itemId,
			Double[] precio, Double[] cantidad, Double[] descuento, Date fecha) {
		VentasPresupuesto presupuesto = new VentasPresupuesto();
		presupuesto.setCliente(presupuestoDTO.getCliente());
		presupuesto.setObservacion(presupuestoDTO.getObservacion());
		presupuesto.setCondventa(presupuestoDTO.getCondventa());
		if (fecha == null) {
			presupuesto.setFecha(new Date());

		} else {
			presupuesto.setFecha(fecha);
		}
		BigDecimal importe = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemVentasPresupuesto linea = crearItemPresupuesto(producto, precio, cantidad, descuento, i);
			BigDecimal iNeto = linea.getCantidad()
					.multiply(linea.getPrecio()
							.multiply(BigDecimal.valueOf(1).subtract(linea.getDescuento().divide(BigDecimal.valueOf(100)))))
					.setScale(2, RoundingMode.HALF_EVEN);
			linea.setImporteNeto(iNeto);
			presupuesto.addItemVentasPresupuesto(linea);
			importe = importe.add(iNeto).setScale(2, RoundingMode.HALF_EVEN);
		}
		presupuesto.setImporte(importe);
		presupuesto.setSaldopendiente(importe);
		return presupuesto;
	}

	private ItemVentasPresupuesto crearItemPresupuesto(Producto producto, Double[] precio, Double[] cantidad, Double[] descuento,
			int i) {
		BigDecimal bgCantidad = BigDecimal.valueOf(cantidad[i]).setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal bgPrecio = BigDecimal.valueOf(precio[i]).setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal bgDescuento = BigDecimal.valueOf(descuento[i]).setScale(2, RoundingMode.HALF_EVEN);
		ItemVentasPresupuesto linea = new ItemVentasPresupuesto();
		linea.setProducto(producto);
		linea.setCantidad(bgCantidad);
		linea.setPrecio(bgPrecio);
		linea.setDescuento(bgDescuento);
		return linea;
	}
	@PostMapping("/nuevodetalle")
	public String guardarPresupuestoDetalle(@Valid @ModelAttribute("presupuesto") VentasPresupuestoDetalleNuevoDTO ventasPresupuestoDTO,
			BindingResult result, Model model,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId, @RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en la Presupuesto Detalle ");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Presupuesto Detalle con errores");
			model.addAttribute("titulo", "Crear Presupuesto Detalle");
			return "/ventas/presupuesto/nuevodetalle";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasPresupuesto presupuestoExistente = null;
		presupuestoExistente = ventasPresupuestoService.findOrdenDeEntregaByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		if (presupuestoExistente != null) {
			log.info("Presupuesto nueva: El Presupuesto ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error",
					"Presupuesto Duplicada! No ha sido cargado, ya existe un Presupuesto con mismo prefijo y numero");
			VentasPresupuestoDetalleNuevoDTO presupuesto = new VentasPresupuestoDetalleNuevoDTO();
			model.addAttribute("presupuesto", presupuesto);
			model.addAttribute("titulo", "Crear Presupuesto Detalle");
			return "redirect:/ventas/presupuesto/nuevodetalle";
		}
		VentasPresupuesto presupuesto = crearPresupuestoDetalle(ventasPresupuestoDTO, fecha);
		log.info("Creando factura con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		presupuesto.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveOrdenDeEntrega(puntoDeVentaId, presupuesto);
		if (puntoDeVenta == null) {
			log.info("Presupuesto nueva: No existe el punto de venta para El Presupuesto de entrega  que desea cargar");
			flash.addFlashAttribute("error",
					"No existe el punto de venta para El Presupuesto de entrega  que desea cargar");
			VentasPresupuestoDetalleNuevoDTO presupuestoNuevo = new VentasPresupuestoDetalleNuevoDTO();
			model.addAttribute("presupuesto", presupuestoNuevo);
			model.addAttribute("titulo", "Crear Presupuesto Detalle");
			return "redirect:/ventas/presupuesto/nuevodetalle";
		}
		status.setComplete();
		log.info("se creo Presupuesto Detalle exitosamente");
		flash.addFlashAttribute("success", "Presupuesto Detalle creada con éxito!");
		return "redirect:/index";
	}

	private VentasPresupuesto crearPresupuestoDetalle(VentasPresupuestoDetalleNuevoDTO ventasPresupuestoDTO,
			Date fecha) {
		VentasPresupuesto presupuesto = new VentasPresupuesto();
		presupuesto.setCliente(ventasPresupuestoDTO.getCliente());
		presupuesto.setObservacion(ventasPresupuestoDTO.getObservacion());
		presupuesto.setDetalle(ventasPresupuestoDTO.getDetalle());
		presupuesto.setCondventa(ventasPresupuestoDTO.getCondventa());
		if (fecha == null) {
			presupuesto.setFecha(new Date());
		} else {
			presupuesto.setFecha(fecha);
		}
		Double importeDTO = ventasPresupuestoDTO.getImporte();
		BigDecimal importeBase = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		if (importeDTO != null)
			importeBase = BigDecimal.valueOf(importeDTO).setScale(2, RoundingMode.HALF_EVEN);
		presupuesto.setImporte(importeBase);
		presupuesto.setSaldopendiente(importeBase);
		return presupuesto;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash,
			Authentication authentication) {
		if (id > 0) {
			VentasPresupuesto presupuesto = clienteService.findOrdenDeEntregaById(id);
			if (presupuesto != null) {
				clienteService.deleteOrdenDeEntrega(presupuesto, id);
				flash.addFlashAttribute("success", "Presupuesto de Venta eliminado con éxito!");
				return "redirect:/ventas/presupuesto/listar";
			}
		}
		flash.addFlashAttribute("error", "El Presupuesto no existe en la base de datos, no se pudo eliminar!");
		return "redirect:/ventas/presupuesto/listar";
	}

	@GetMapping(value = "/listar")
	public String listar(@Valid @ModelAttribute("presupuesto") VentasPresupuestoBusquedaDTO ventasPresupuestoDTO, BindingResult result,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50,Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasPresupuesto> presupuestos = ventasPresupuestoService.buscarVentasPresupuestos(ventasPresupuestoDTO, pageRequest);
		PageRender<VentasPresupuesto> pageRender = new PageRender<VentasPresupuesto>("/ventas/presupuesto/listar", presupuestos);
		model.addAttribute("titulo", "Listado de Presupuestos de Venta");
		model.addAttribute("presupuestos", presupuestos);
		model.addAttribute("page", pageRender);
		return "/ventas/presupuesto/listar";
	}

	@RequestMapping(value = "/buscar")
	public String buscarPresupuestos(Map<String, Object> model) {
		VentasPresupuestoBusquedaDTO presupuesto = new VentasPresupuestoBusquedaDTO();
		model.put("presupuesto", presupuesto);
		model.put("titulo", "Buscar Presupuesto de Venta");
		return "/ventas/presupuesto/buscar";
	}

	@GetMapping(value = "/exportar")
	public String exportarPresupuestos(@Valid @ModelAttribute("presupuesto") VentasPresupuestoBusquedaDTO ventasPresupuestoDTO,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"ordenentrega_view.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/presupuesto/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/presupuesto/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/presupuesto/buscar";
		}
		List<VentasPresupuesto> presupuestos = ventasPresupuestoService.buscarVentasPresupuestos(ventasPresupuestoDTO);
		model.addAttribute("fechainicio", ventasPresupuestoDTO.getFechainicio());
		model.addAttribute("fechafin", ventasPresupuestoDTO.getFechafin());
		model.addAttribute("titulo", "Listado de Presupuestos de Venta");
		model.addAttribute("presupuestos", presupuestos);
		return "/ventas/presupuesto/exportar.xlsx";
	}

	@PostMapping(value = "/buscar")
	public String resultadoPresupuesto(@Valid @ModelAttribute("presupuesto") VentasPresupuestoBusquedaDTO ventasPresupuestoDTO, BindingResult result,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "fechainicio", required = false) @PastOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @Future @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request) {
			if (result.hasErrors()) {
				log.info("error redireccionando a busqueda");
				return "redirect:/ventas/presupuesto/buscar";
			}
			if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
				if (!fechainicio.equals(fechafin)) {
					flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
					return "redirect:/ventas/presupuesto/buscar";
				}
			}
			if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
				flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
				return "redirect:/ventas/presupuesto/buscar";
			}
		Pageable pageRequest = PageRequest.of(page, 50,Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasPresupuesto> presupuestos = ventasPresupuestoService.buscarVentasPresupuestos(ventasPresupuestoDTO, pageRequest);
		PageRender<VentasPresupuesto> pageRender = new PageRender<VentasPresupuesto>("/ventas/presupuesto/listar",presupuestos);
		model.addAttribute("titulo", "Listado de Presupuestos de Venta");
		model.addAttribute("presupuestos", presupuestos);
		model.addAttribute("page", pageRender);
		return "/ventas/presupuesto/listar";
	}


}
