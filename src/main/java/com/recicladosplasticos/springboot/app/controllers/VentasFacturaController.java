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

import com.recicladosplasticos.springboot.app.models.dto.VentasFacturaBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasFacturaNuevaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasFacturaDetalleNuevaDTO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.service.ClienteService;
import com.recicladosplasticos.springboot.app.models.service.VentasFacturaService;
import com.recicladosplasticos.springboot.app.models.service.PuntoDeVentaService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/ventas/factura")
@SessionAttributes("factura")
public class VentasFacturaController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PuntoDeVentaService puntoDeVentaService;

	@Autowired
	private VentasFacturaService ventasFacturaService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ModelAttribute("clientes")
	public List<Cliente> clientes() {
		List<Cliente> clientes = clienteService.findAll();
		clientes.sort(Comparator.comparing(Cliente::getNombre));
		return clientes;
	}

	@ModelAttribute("puntosdeventa")
	public List<PuntoDeVenta> puntosdeventa() {
		List<PuntoDeVenta> puntosDeVenta = puntoDeVentaService.findByDocumento("Factura");
		puntosDeVenta.sort(Comparator.comparing(PuntoDeVenta::getNombre));
		return puntosDeVenta;
	}

	@ModelAttribute("condicionesdeventa")
	public List<String> condicionesdeventa() {
		return Arrays.asList("Contado", "Cuenta Corriente", "Cheque", "ECHEQ", "Tarjeta de Débito",
				"Tarjeta de Crédito", "Ticket", "Otra");
	}

	@ModelAttribute("tipodocumentos")
	public List<String> tipodocumentos() {
		return Arrays.asList("Ventas Factura", "Ventas N. de Crédito", "Ventas N. de Débito", "Ventas Recibo",
				"Compras Factura", "Compras N. de Crédito", "Compras N. de Débito", "Compras Orden de Pago");
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		List<Producto> productos = clienteService.findByNombre(term);
		productos.sort(Comparator.comparing(Producto::getNombre));
		return productos;
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		VentasFactura ventasFactura = clienteService.findFacturaById(id);
		if (ventasFactura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos!");
			return "redirect:/ventas/factura/listar";
		}
		model.addAttribute("titulo", "FACTURA DE VENTA");
		DecimalFormat df = new DecimalFormat("00000000");
		model.addAttribute("prefijonumero",
				"Numero: " + ventasFactura.getPrefijo().toString() + " - " + df.format(ventasFactura.getNumero()));
		model.addAttribute("factura", ventasFactura);
		return "/ventas/factura/ver";
	}

	@GetMapping("/nuevo")
	public String crearFactura(Model model, RedirectAttributes flash) {
		VentasFacturaNuevaDTO factura = new VentasFacturaNuevaDTO();
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear Factura");
		return "/ventas/factura/nuevo";
	}

	@GetMapping("/nuevodetalle")
	public String crearFacturaDetalleDTO(Model model, RedirectAttributes flash) {
		VentasFacturaDetalleNuevaDTO factura = new VentasFacturaDetalleNuevaDTO();
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear Factura Detalle");
		return "/ventas/factura/nuevodetalle";
	}

	@PostMapping("/nuevo")
	public String guardarFactura(@Valid @ModelAttribute("factura") VentasFacturaNuevaDTO facturaDTO, BindingResult result,
			Model model, @RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "price[]", required = false) Double[] precio,
			@RequestParam(name = "cantidad[]", required = false) Double[] cantidad,
			@RequestParam(name = "descuento[]", required = false) Double[] descuento,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId, @RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash, SessionStatus status) {

		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasFactura facturaExistente = null;
		facturaExistente = ventasFacturaService.findFacturaByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		if (facturaExistente != null) {
			log.info("Factura nuevo: El gasto ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error",
					"Factura Duplicada! No ha sido cargado, ya existe una factura con mismo prefijo y numero");
			VentasFacturaNuevaDTO factura = new VentasFacturaNuevaDTO();
			model.addAttribute("factura", factura);
			model.addAttribute("titulo", "Crear Factura");
			return "redirect:/ventas/factura/nuevo";
		}
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en la facura");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Factura con errores");
			model.addAttribute("titulo", "Crear Factura");
			return "/ventas/factura/nuevo";
		}
		if (itemId == null || itemId.length == 0) {
			flash.addFlashAttribute("error",
					"Hay errores en la facura: la factura no tiene lineas! Debe cargar Productos");
			log.info("Hay errores en la facura: la factura no tiene lineas");
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error", "Error: La factura no puede no tener líneas! Debe cargar Productos");
			return "/ventas/factura/nuevo";
		}
		VentasFactura ventasFactura = crearNuevaFactura(facturaDTO, itemId, precio, cantidad, descuento, fecha);
		log.info("Creando factura con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		ventasFactura.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveFacturaProductos(puntoDeVentaId, ventasFactura);
		if (puntoDeVenta == null) {
			log.info("Factura nueva: No existe el punto de venta para la factura que desea cargar Factura");
			flash.addFlashAttribute("error", "No existe el punto de venta para la factura que desea cargar Factura");
			VentasFactura nuevafactura = new VentasFactura();
			model.addAttribute("factura", nuevafactura);
			model.addAttribute("titulo", "Crear Factura");
			return "redirect:/ventas/factura/nuevo";
		}
		status.setComplete();
		log.info("se creo fc exitosamente");
		flash.addFlashAttribute("success", "Factura creada con éxito!");
		return "redirect:/index";
	}

	private VentasFactura crearNuevaFactura(VentasFacturaNuevaDTO facturaDTO, Long[] itemId, Double[] precio, Double[] cantidad,
			Double[] descuento, Date fecha) {
		VentasFactura ventasFactura = new VentasFactura();
		ventasFactura.setCliente(facturaDTO.getCliente());
		ventasFactura.setObservacion(facturaDTO.getObservacion());
		ventasFactura.setCondventa(facturaDTO.getCondventa());
		ventasFactura.setAlicuotaiva(0.21);
		if (fecha == null) {
			ventasFactura.setFecha(new Date());
		} else {
			ventasFactura.setFecha(fecha);
		}
		BigDecimal importe = calcularImporteConItems(itemId, precio, cantidad, descuento, ventasFactura);
		setearImportesConIVAYSaldoPendiente(ventasFactura, importe);
		return ventasFactura;
	}

	private void setearImportesConIVAYSaldoPendiente(VentasFactura ventasFactura, BigDecimal importe) {
		ventasFactura.setImporte(importe);
		BigDecimal importeIVA = importe.multiply(BigDecimal.valueOf(0.21)).setScale(2, RoundingMode.HALF_EVEN);
		ventasFactura.setImporteiva(importeIVA);
		BigDecimal importeTotal = importe.add(importeIVA).setScale(2, RoundingMode.HALF_EVEN);
		ventasFactura.setImportetotal(importeTotal);
		ventasFactura.setSaldopendiente(importeTotal);
	}

	private BigDecimal calcularImporteConItems(Long[] itemId, Double[] precio, Double[] cantidad, Double[] descuento,
			VentasFactura ventasFactura) {
		BigDecimal importe = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemVentasFactura linea = crearItemFactura(producto, precio, cantidad, descuento, i);
			BigDecimal iNeto = linea.getCantidad()
					.multiply(linea.getPrecio().multiply(
							BigDecimal.valueOf(1).subtract(linea.getDescuento().divide(BigDecimal.valueOf(100)))))
					.setScale(2, RoundingMode.HALF_EVEN);
			linea.setImporteNeto(iNeto);
			ventasFactura.addItemFactura(linea);
			importe = importe.add(iNeto).setScale(2, RoundingMode.HALF_EVEN);
		}
		return importe;
	}

	private ItemVentasFactura crearItemFactura(Producto producto, Double[] precio, Double[] cantidad, Double[] descuento,
			int i) {
		BigDecimal bgCantidad = BigDecimal.valueOf(cantidad[i]).setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal bgPrecio = BigDecimal.valueOf(precio[i]).setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal bgDescuento = BigDecimal.valueOf(descuento[i]).setScale(2, RoundingMode.HALF_EVEN);
		ItemVentasFactura linea = new ItemVentasFactura();
		linea.setProducto(producto);
		linea.setCantidad(bgCantidad);
		linea.setPrecio(bgPrecio);
		linea.setDescuento(bgDescuento);
		return linea;
	}

	@PostMapping("/nuevodetalle")
	public String guardarFacturaDetalle(@Valid @ModelAttribute("factura") VentasFacturaDetalleNuevaDTO facturaDTO,
			BindingResult result, Model model,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId, @RequestParam(name = "iva") Double iva,
			@RequestParam(name = "pVenta") Long puntoDeVentaId, RedirectAttributes flash, SessionStatus status) {
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasFactura facturaExistente = null;
		facturaExistente = ventasFacturaService.findFacturaByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		if (facturaExistente != null) {
			log.info("Factura nuevo: La Factura ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error",
					"Factura Duplicada! No ha sido cargado, ya existe una factura con mismo prefijo y numero");
			VentasFacturaDetalleNuevaDTO factura = new VentasFacturaDetalleNuevaDTO();
			model.addAttribute("factura", factura);
			model.addAttribute("titulo", "Crear Factura Detalle");
			return "redirect:/ventas/factura/nuevodetalle";
		}
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en la facura");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Factura con errores");
			model.addAttribute("titulo", "Crear Factura");
			return "/ventas/factura/nuevodetalle";
		}
		VentasFactura ventasFactura = crearFacturaDetalle(facturaDTO, fecha, iva);
		log.info("Creando factura con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		ventasFactura.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveFactura(puntoDeVentaId, ventasFactura);
		if (puntoDeVenta == null) {
			log.info("Factura nueva: No existe el punto de venta para la factura que desea cargar Factura");
			flash.addFlashAttribute("error", "No existe el punto de venta para la factura que desea cargar Factura");
			VentasFacturaDetalleNuevaDTO nuevafactura = new VentasFacturaDetalleNuevaDTO();
			model.addAttribute("factura", nuevafactura);
			model.addAttribute("titulo", "Crear Factura Detalle");
			return "redirect:/ventas/factura/nuevodetalle";
		}
		status.setComplete();
		log.info("se creo fc exitosamente");
		flash.addFlashAttribute("success", "Factura detalle creada con éxito!");
		return "redirect:/index";
	}

	private VentasFactura crearFacturaDetalle(VentasFacturaDetalleNuevaDTO facturaDTO, Date fecha, Double iva) {
		VentasFactura ventasFactura = new VentasFactura();
		ventasFactura.setCliente(facturaDTO.getCliente());
		ventasFactura.setObservacion(facturaDTO.getObservacion());
		ventasFactura.setDetalle(facturaDTO.getDetalle());
		ventasFactura.setAlicuotaiva(iva);
		ventasFactura.setCondventa(facturaDTO.getCondventa());
		if (fecha == null) {
			ventasFactura.setFecha(new Date());
		} else {
			ventasFactura.setFecha(fecha);
		}
		BigDecimal importeBase = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		Double importeDTO = facturaDTO.getImporte();
		if (importeDTO != null)
			importeBase = BigDecimal.valueOf(importeDTO).setScale(2, RoundingMode.HALF_EVEN);
		ventasFactura.setImporte(importeBase);
		BigDecimal importeIVA = importeBase.multiply(BigDecimal.valueOf(iva)).setScale(2, RoundingMode.HALF_EVEN);
		ventasFactura.setImporteiva(importeIVA);
		BigDecimal importeTotal = importeBase.add(importeIVA).setScale(2, RoundingMode.HALF_EVEN);
		ventasFactura.setImportetotal(importeTotal);
		ventasFactura.setSaldopendiente(importeTotal);
		return ventasFactura;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash,
			Authentication authentication) {
		VentasFactura ventasFactura = clienteService.findFacturaById(id);
		if (ventasFactura != null) {
			clienteService.deleteFactura(ventasFactura, id);
			flash.addFlashAttribute("success", "Factura eliminada con éxito!");
			return "redirect:/ventas/factura/listar";
		}
		flash.addFlashAttribute("error", "La factura no existe en la base de datos, no se pudo eliminar!");
		return "redirect:/ventas/factura/listar";
	}

	@GetMapping(value = "/listar")
	public String listar(@Valid @ModelAttribute("factura") VentasFacturaBusquedaDTO factura,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50,
				Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasFactura> ventasFacturas = ventasFacturaService.buscarVentasFacturas(factura, pageRequest);
		PageRender<VentasFactura> pageRender = new PageRender<VentasFactura>("/ventas/factura/listar", ventasFacturas);
		model.addAttribute("titulo", "Listado de Facturas");
		model.addAttribute("facturas", ventasFacturas);
		model.addAttribute("page", pageRender);
		return "/ventas/factura/listar";
	}

	@RequestMapping(value = "/buscar")
	public String searchFactura(Map<String, Object> model) {
		VentasFacturaBusquedaDTO factura = new VentasFacturaBusquedaDTO();
		model.put("factura", factura);
		model.put("titulo", "Buscar Factura");
		return "/ventas/factura/buscar";
	}

	@GetMapping(value = "/exportar")
	public String exportarFactura(@Valid @ModelAttribute("factura") VentasFacturaBusquedaDTO facturaDTO, BindingResult result,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/factura/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/factura/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/factura/buscar";
		}
		List<VentasFactura> ventasFacturas = ventasFacturaService.buscarVentasFacturas(facturaDTO);
		model.addAttribute("fechainicio", facturaDTO.getFechainicio());
		model.addAttribute("fechafin", facturaDTO.getFechafin());
		model.addAttribute("titulo", "Listado de Facturas");
		model.addAttribute("facturas", ventasFacturas);
		return "/ventas/factura/exportar.xlsx";
	}

	@PostMapping(value = "/buscar")
	public String resultadoFactura(@Valid @ModelAttribute("factura") VentasFacturaBusquedaDTO factura,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "importemin", required = false) Double importemin,
			@RequestParam(name = "importemax", required = false) Double importemax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "prefijo", required = false) String prefijo,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request) {
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/factura/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/factura/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/factura/buscar";
		}
		if ((importemin != null && importemax != null) && importemax < importemin) {
			flash.addFlashAttribute("error", "El rango de importe mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/factura/buscar";
		}
		Pageable pageRequest = PageRequest.of(page, 50,Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasFactura> ventasFacturas = ventasFacturaService.buscarVentasFacturas(factura, pageRequest);
		PageRender<VentasFactura> pageRender = new PageRender<VentasFactura>("/ventas/factura/listar", ventasFacturas);
		model.addAttribute("titulo", "Listado de Facturas");
		model.addAttribute("facturas", ventasFacturas);
		model.addAttribute("page", pageRender);
		return "/ventas/factura/listar";
	}

}
