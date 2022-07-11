package com.recicladosplasticos.springboot.app.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
import com.recicladosplasticos.springboot.app.models.dto.VentasDevolucionBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasDevolucionDetalleNuevaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasDevolucionNuevaDTO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.VentasDevolucion;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasDevolucion;
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.service.ClienteService;
import com.recicladosplasticos.springboot.app.models.service.PuntoDeVentaService;
import com.recicladosplasticos.springboot.app.models.service.VentasDevolucionService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/ventas/devolucion")
@SessionAttributes("devolucion")
public class VentasDevolucionController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PuntoDeVentaService puntoDeVentaService;
	
	@Autowired
	private VentasDevolucionService devolucionService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	
	@ModelAttribute("clientes")
	public List<Cliente> clientes(){
		List<Cliente> clientes =    clienteService.findAll();
		clientes.sort(Comparator.comparing(Cliente::getNombre));
		return  clientes;
	}
	
	@ModelAttribute("puntosdeventa")
	public List<PuntoDeVenta> puntosdeventa(){
		List<PuntoDeVenta>  puntosDeVenta =  puntoDeVentaService.findByDocumento("Devolución");
		puntosDeVenta.sort(Comparator.comparing(PuntoDeVenta::getNombre));
		return  puntosDeVenta;
	}
	
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		List<Producto> productos = clienteService.findByNombre(term);
		productos.sort(Comparator.comparing(Producto::getNombre));
		return productos;
	}
  	

	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id") Long id,Model model,RedirectAttributes flash) {
		VentasDevolucion devolucion  = clienteService.findDevolucionVentasById(id);
		if(devolucion == null) {
			flash.addFlashAttribute("error", "La Devolución no existe en la base de datos!");
			return "redirect:/ventas/devolucion/listar";
		}
		model.addAttribute("devolucion", devolucion);
		model.addAttribute("titulo", "Devolución de Venta");
		DecimalFormat df = new DecimalFormat("00000000");
		model.addAttribute("prefijonumero", "Numero: " + devolucion.getPrefijo().toString() + " - " + df.format(devolucion.getNumero())); 
 		return "/ventas/devolucion/ver";
	}

	@GetMapping("/nuevo")
 	public String crearNuevaDevolucion( Model model, RedirectAttributes flash) {
		VentasDevolucionNuevaDTO devolucion = new VentasDevolucionNuevaDTO();
	 	model.addAttribute("devolucion", devolucion);
	 	model.addAttribute("titulo", "Crear Devolucion");
		return "/ventas/devolucion/nuevo";
	}
	
	@GetMapping("/nuevodetalle")
 	public String crearNuevoDevolucionDetalle( Model model, RedirectAttributes flash) {
		VentasDevolucionDetalleNuevaDTO devolucion = new VentasDevolucionDetalleNuevaDTO();
	 	model.addAttribute("devolucion", devolucion);
	 	model.addAttribute("titulo", "Crear Devolucion");
		return "/ventas/devolucion/nuevodetalle";
	}
	

	@PostMapping("/nuevo")
	public String guardarDevolucion(@Valid @ModelAttribute("devolucion") VentasDevolucionNuevaDTO devolucionDTO, 
			BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId, 
			@RequestParam(name = "cantidad[]", required = false) Double[] cantidad, 
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId,
			@RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en el remito");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Devolucion con errores");
			model.addAttribute("titulo", "Crear Devolucion");
			return "/ventas/devolucion/nuevo";
		}
		if (itemId == null || itemId.length == 0) {
			log.info("Hay errores en el Remito: la remito no tiene lineas");
			model.addAttribute("titulo", "Crear Devolucion");
			model.addAttribute("error", "Error: La Devolución NO puede no tener líneas!");
			flash.addFlashAttribute("error", "Error: La Devolución NO puede no tener líneas!");
			return "/ventas/devolucion/nuevo";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasDevolucion devolucionExistente = null;
		devolucionExistente = devolucionService.findDevolucionByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		 if(devolucionExistente != null) {
			log.info("Devolución nuevo: El gasto ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error", "Devolución Duplicada! No ha sido cargado, ya existe una devolución con mismo prefijo y numero");
	 		VentasDevolucionNuevaDTO devolucion = new VentasDevolucionNuevaDTO();
		 	model.addAttribute("devolucion", devolucion);
			model.addAttribute("titulo", "Crear Devolución");
			return "redirect:/ventas/devolucion/nuevo";
		}
		if (cantidad != null && cantidad.length != 0) {
			boolean cantcero = false;
			for(int i = 0; i< cantidad.length ; i++) {
				if(cantidad[i]== 0) {
					cantcero = true;
					break;
				}
			}
			if(cantcero) {
				log.info("Hay errores en la Devolución: La Devolución no puede tener 0 en la cantidad a enviar");
				model.addAttribute("titulo", "Crear Devolución");
				flash.addFlashAttribute("error", "Error: La Devolución no puede tener 0 en la cantidad a enviar");
				model.addAttribute("error", "Error: La Devolución no puede tener 0 en la cantidad a enviar");
				return "/ventas/devolucion/nuevo";
			}
		}
		VentasDevolucion devolucion = new VentasDevolucion();
		BigDecimal cantidadtotal = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemVentasDevolucion linea = new ItemVentasDevolucion();
			linea.setProducto(producto);
			BigDecimal bdCantidad =  BigDecimal.valueOf(cantidad[i]).setScale(2, RoundingMode.HALF_EVEN);
			linea.setCantidad(bdCantidad);
			devolucion.addItemDevolucionVentas(linea);
			cantidadtotal = cantidadtotal.add(bdCantidad).setScale(2, RoundingMode.HALF_EVEN);
		}
		devolucion.setCantidadtotal(cantidadtotal);
		devolucion.setLineas(itemId.length); 
		devolucion.setObservacion(devolucionDTO.getObservacion());
 		if(fecha == null) {
 			devolucion.setFecha(new  Date());
 		} else {
 			devolucion.setFecha(fecha);
 		}
		log.info("Creando Remito con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		devolucion.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveDevolucionVentasProductos(puntoDeVentaId, devolucion);
		if(puntoDeVenta == null) {
			log.info("Devolución nuevo: No existe el punto de venta para la devolución  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para el devolución que desea cargar");
			VentasDevolucionNuevaDTO devolucionnueva = new VentasDevolucionNuevaDTO();
		 	model.addAttribute("devolucion", devolucionnueva);
			model.addAttribute("titulo", "Crear Devolución");
			return "redirect:/ventas/devolucion/nuevo";
		}	
		status.setComplete();
		log.info("se creo DE exitosamente");
		flash.addFlashAttribute("success", "Devolución creada con éxito!");
		return "redirect:/index";
	}
	
	@PostMapping("/nuevodetalle")
	public String guardarRemitoDetalle(@Valid @ModelAttribute("devolucion") VentasDevolucionDetalleNuevaDTO devolucionDTO, 
			BindingResult result, Model model,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId,
			@RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash,SessionStatus status) {
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en la Devolución");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Remito con errores");
			model.addAttribute("titulo", "Crear Devolución Detalle");
			return "/ventas/devolucion/nuevodetalle";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasDevolucion devolucionExistente = null;
		devolucionExistente = devolucionService.findDevolucionByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		 if(devolucionExistente != null) {
			log.info("Devolución nuevo: La Devolución ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error", "Devolución Duplicado! No ha sido cargado, ya existe una devolución con mismo prefijo y numero");
			VentasDevolucionDetalleNuevaDTO devolucion = new VentasDevolucionDetalleNuevaDTO();
		 	model.addAttribute("devolucion", devolucion);
			model.addAttribute("titulo", "Crear Devolución Detalle");
			return "redirect:/ventas/devolucion/nuevodetalle";
		}
		VentasDevolucion devolucion = new VentasDevolucion();
		devolucion.setDetalle(devolucionDTO.getDetalle());
		BigDecimal cantidadtotal = BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_EVEN);
		devolucion.setCantidadtotal(cantidadtotal);
		devolucion.setLineas(1); 
		devolucion.setObservacion(devolucionDTO.getObservacion());
 		if(fecha == null) {
 			devolucion.setFecha(new  Date());
 		} else {
 			devolucion.setFecha(fecha);
 		}
		log.info("Creando Remito con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		devolucion.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveDevolucionVentasProductos(puntoDeVentaId, devolucion);
		if(puntoDeVenta == null) {
			log.info("Devolución nuevo: No existe el punto de venta para la devolución  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para el devolución que desea cargar");
			VentasDevolucionDetalleNuevaDTO devolucionnueva = new VentasDevolucionDetalleNuevaDTO();
		 	model.addAttribute("devolucion", devolucionnueva);
			model.addAttribute("titulo", "Crear Devolución Detalle");
			return "redirect:/ventas/devolucion/nuevodetalle";
		}	
		status.setComplete();
		log.info("se creo DE exitosamente");
		flash.addFlashAttribute("success", "Devolución Detalle creada con éxito!");
		return "redirect:/index";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			VentasDevolucion devolucion = clienteService.findDevolucionVentasById(id);
			if(devolucion != null) {
				clienteService.deleteDevolucionVentas(devolucion, id);
				flash.addFlashAttribute("success", "devolucion eliminado con éxito!");
				return "redirect:/ventas/devolucion/listar";
			}
		}	
		flash.addFlashAttribute("error", "El devolucion no existe en la base de datos, no se pudo eliminar!");
		return "redirect:/ventas/devolucion/listar";
	}
	
	@GetMapping(value = "/listar")
	public String listar(@Valid @ModelAttribute("devolucion") VentasDevolucionBusquedaDTO devolucionDTO, BindingResult result ,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model, HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50, Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasDevolucion> devoluciones = devolucionService.buscarVentasDevoluciones(devolucionDTO, pageRequest);
		PageRender<VentasDevolucion> pageRender = new PageRender<VentasDevolucion>("/ventas/devolucion/listar", devoluciones);
		model.addAttribute("titulo", "Listado de devoluciones");
		model.addAttribute("devoluciones", devoluciones);
		model.addAttribute("page", pageRender);
		return "/ventas/devolucion/listar";
	}
	
	@RequestMapping(value = "/buscar")
	public String search(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		VentasDevolucionBusquedaDTO devolucion = new VentasDevolucionBusquedaDTO();
		model.put("devolucion", devolucion);
		model.put("titulo", "Buscar devolucion");
		return "/ventas/devolucion/buscar";
	}
	
	@GetMapping(value = "/exportar")
	public String exportarDevoluciones(@Valid @ModelAttribute("devolucion") VentasDevolucionBusquedaDTO devolucionDTO, BindingResult result,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"listado_remitos.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/devolucion/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/devolucion/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/devolucion/buscar";
		}
		List<VentasDevolucion> devoluciones = devolucionService.buscarVentasDevoluciones(devolucionDTO);
		model.addAttribute("fechainicio", devolucionDTO.getFechainicio());
		model.addAttribute("fechafin", devolucionDTO.getFechafin());
		model.addAttribute("titulo", "Listado de Devoluciones");
		model.addAttribute("devoluciones", devoluciones);
		return "/ventas/devolucion/exportar.xlsx";
	}
	
	@PostMapping(value = "/buscar")
	public String resultadoBusquedaDevolucionDTO( @Valid @ModelAttribute("devolucion") VentasDevolucionBusquedaDTO devolucionDTO, BindingResult result ,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, RedirectAttributes flash,
			HttpServletRequest request) {
			if(result.hasErrors()) {
				log.info("error redireccionando a busqueda");
				return "redirect:/ventas/devolucion/buscar";
			}
			if ((devolucionDTO.getFechainicio() != null && devolucionDTO.getFechafin() != null) && devolucionDTO.getFechainicio().after(devolucionDTO.getFechafin())) {
				if (!devolucionDTO.getFechainicio().equals(devolucionDTO.getFechafin())) {
					flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
					return "redirect:/ventas/devolucion/buscar";
				}
			}
			if ((devolucionDTO.getNumeromin() != null && devolucionDTO.getNumeromax() != null) && devolucionDTO.getNumeromax() < devolucionDTO.getNumeromin()) {
				flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
				return "redirect:/ventas/devolucion/buscar";
			}
			if ((devolucionDTO.getNumeromin() != null && devolucionDTO.getNumeromax() != null) && devolucionDTO.getNumeromax() < devolucionDTO.getNumeromin()) {
				flash.addFlashAttribute("error", "El rango de importe mínimo no puede ser mayor que el máximo!");
				return "redirect:/ventas/devolucion/buscar";
			}
		Pageable pageRequest = PageRequest.of(page, 50, Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasDevolucion> devoluciones = devolucionService.buscarVentasDevoluciones(devolucionDTO, pageRequest);
		PageRender<VentasDevolucion> pageRender = new PageRender<VentasDevolucion>("/ventas/devolucion/listar", devoluciones);
		model.addAttribute("titulo", "Listado de Devoluciones");
		model.addAttribute("devoluciones", devoluciones);
		model.addAttribute("page", pageRender);
		return "/ventas/devolucion/listar";
	}
	
}
