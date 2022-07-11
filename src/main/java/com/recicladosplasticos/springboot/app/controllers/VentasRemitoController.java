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
 
import com.recicladosplasticos.springboot.app.models.dto.VentasRemitoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasRemitoDetalleNuevoDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasRemitoNuevoDTO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasRemito;
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta; 
import com.recicladosplasticos.springboot.app.models.entity.VentasRemito;
import com.recicladosplasticos.springboot.app.models.service.ClienteService;
import com.recicladosplasticos.springboot.app.models.service.PuntoDeVentaService;
import com.recicladosplasticos.springboot.app.models.service.VentasRemitoService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/ventas/remito")
@SessionAttributes("remito")
public class VentasRemitoController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PuntoDeVentaService puntoDeVentaService;
	
	@Autowired
	private VentasRemitoService remitoVentasService;
	
private final Logger log = LoggerFactory.getLogger(getClass());
	
	
	@ModelAttribute("clientes")
	public List<Cliente> clientes(){
		List<Cliente> clientes =    clienteService.findAll();
		clientes.sort(Comparator.comparing(Cliente::getNombre));
		return  clientes;
		
	}
	
	@ModelAttribute("puntosdeventa")
	public List<PuntoDeVenta> puntosdeventa(){
		List<PuntoDeVenta>  puntosDeVenta =  puntoDeVentaService.findByDocumento("Remito");
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
	public String ver(@PathVariable(value="id") Long id, Model model,RedirectAttributes flash) {
		VentasRemito remito = clienteService.findRemitoVentasById(id);
		if(remito == null) {
			flash.addFlashAttribute("error", "El Remito no existe en la base de datos!");
			return "redirect:/ventas/remito/listar";
		}
		model.addAttribute("remito", remito);
		model.addAttribute("titulo", "Remito de Venta");
		DecimalFormat df = new DecimalFormat("00000000");
		model.addAttribute("prefijonumero", "Numero: " + remito.getPrefijo().toString() + " - " + df.format(remito.getNumero())); 
		return "/ventas/remito/ver";
	}

	@GetMapping("/nuevo")
 	public String crearNuevoRemito( Model model, RedirectAttributes flash) {
		VentasRemitoNuevoDTO remito = new VentasRemitoNuevoDTO();
	 	model.addAttribute("remito", remito);
		model.addAttribute("titulo", "Crear Remito");
		return "/ventas/remito/nuevo";
	}
	
	@GetMapping("/nuevodetalle")
 	public String crearNuevoRemitoDetalle( Model model, RedirectAttributes flash) {
		VentasRemitoDetalleNuevoDTO remito = new VentasRemitoDetalleNuevoDTO();
	 	model.addAttribute("remito", remito);
		model.addAttribute("titulo", "Crear Remito Detalle");
		return "/ventas/remito/nuevodetalle";
	}
	
	

	
	@PostMapping("/nuevo")
	public String guardarRemito(@Valid @ModelAttribute("remito") VentasRemitoNuevoDTO remitoDTO, 
			BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId, 
			@RequestParam(name = "cantidad[]", required = false) Double[] cantidad, 
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId,
			@RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash,
			SessionStatus status) {
		log.info("CONTROLLER POST /nuevo2");
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en el remito");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Remito con errores");
			model.addAttribute("titulo", "Crear Remito");
			return "/ventas/remito/nuevo";
		}
		if (itemId == null || itemId.length == 0) {
			log.info("Hay errores en el Remito: la remito no tiene lineas");
			model.addAttribute("titulo", "Crear Remito");
			model.addAttribute("error", "Error: El Remito NO puede no tener líneas!");
			flash.addFlashAttribute("error", "Error: El Remito NO puede no tener líneas!");
			return "/ventas/remito/nuevo";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasRemito remitoExistente = null;
		remitoExistente = remitoVentasService.findRemitoByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		 if(remitoExistente != null) {
			log.info("Remito nuevo: El gasto ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error", "Remito Duplicado! No ha sido cargado, ya existe un remito con mismo prefijo y numero");
	 		VentasRemitoNuevoDTO remito = new VentasRemitoNuevoDTO();
		 	model.addAttribute("remito", remito);
			model.addAttribute("titulo", "Crear Remito");
			return "redirect:/ventas/remito/nuevo";
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
				log.info("Hay errores en el Remito: El remito no puede tener 0 en la cantidad a enviar");
				model.addAttribute("titulo", "Crear Remito");
				flash.addFlashAttribute("error", "Error: El remito no puede tener 0 en la cantidad a enviar");
				model.addAttribute("error", "Error: El remito no puede tener 0 en la cantidad a enviar");
				return "/ventas/remito/nuevo";
			}
		}
		
		VentasRemito remito = new VentasRemito();
		BigDecimal cantidadtotal = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemVentasRemito linea = new ItemVentasRemito();
			linea.setProducto(producto);
			BigDecimal bdCantidad =  BigDecimal.valueOf(cantidad[i]).setScale(2, RoundingMode.HALF_EVEN);
			linea.setCantidad(bdCantidad);
			remito.addItemRemitoVentas(linea);
			cantidadtotal = cantidadtotal.add(bdCantidad).setScale(2, RoundingMode.HALF_EVEN);
		}
		remito.setCantidadtotal(cantidadtotal);
		remito.setLineas(itemId.length); 
		remito.setObservacion(remitoDTO.getObservacion());
 		if(fecha == null) {
 			remito.setFecha(new  Date());
 		} else {
 			remito.setFecha(fecha);
 		}
		log.info("Creando Remito con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		remito.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveRemitoVentasProductos(puntoDeVentaId, remito);
		if(puntoDeVenta == null) {
			log.info("Remito nuevo: No existe el punto de venta para el remito  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para el remito que desea cargar");
	 		VentasRemitoNuevoDTO remitonuevo = new VentasRemitoNuevoDTO();
		 	model.addAttribute("remito", remitonuevo);
			model.addAttribute("titulo", "Crear Remito");
			return "redirect:/ventas/remito/nuevo";
		}	
		status.setComplete();
		log.info("se creo RE exitosamente");
		flash.addFlashAttribute("success", "Remito creada con éxito!");
		return "redirect:/index";
	}
	 
	
	@PostMapping("/nuevodetalle")
	public String guardarRemitoDetalle(@Valid @ModelAttribute("remito") VentasRemitoDetalleNuevoDTO remitoDTO, 
			BindingResult result, Model model,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente") Long clienteId,
			@RequestParam(name = "pVenta") Long puntoDeVentaId,
			RedirectAttributes flash,
			SessionStatus status) {
		log.info("CONTROLLER POST /nuevo2");
		if (result.hasErrors()) {
			log.info("result.hasErrors(): Hay errores en el remito");
			log.info("result.hasErrors() - cliente: " + clienteId.toString());
			flash.addFlashAttribute("error", "Remito con errores");
			model.addAttribute("titulo", "Crear Remito Detalle");
			return "/ventas/remito/nuevodetalle";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasRemito remitoExistente = null;
		remitoExistente = remitoVentasService.findRemitoByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		 if(remitoExistente != null) {
			log.info("Remito nuevo: El gasto ya fue cargado para el provedor (prefijo y numero)");
			flash.addFlashAttribute("error", "Remito Duplicado! No ha sido cargado, ya existe un remito con mismo prefijo y numero");
	 		VentasRemitoDetalleNuevoDTO remito = new VentasRemitoDetalleNuevoDTO();
		 	model.addAttribute("remito", remito);
			model.addAttribute("titulo", "Crear Remito Detalle");
			return "redirect:/ventas/remito/nuevodetalle";
 		}
	  
		
		VentasRemito remito = new VentasRemito();
		remito.setDetalle(remitoDTO.getDetalle());
		BigDecimal cantidadtotal = BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_EVEN);
		remito.setCantidadtotal(cantidadtotal);
		remito.setLineas(1); 
		remito.setObservacion(remitoDTO.getObservacion());
 		if(fecha == null) {
 			remito.setFecha(new  Date());
 		} else {
 			remito.setFecha(fecha);
 		}
		 
		log.info("Creando Remito con cliente id: " + clienteId.toString());
		Cliente cliente = clienteService.findOne(clienteId);
		remito.setCliente(cliente);
		PuntoDeVenta puntoDeVenta = clienteService.saveRemitoVentas(puntoDeVentaId, remito);
		if(puntoDeVenta == null) {
			log.info("Remito nuevo: No existe el punto de venta para la remito  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para el remito que desea cargar");
	 		VentasRemitoDetalleNuevoDTO remitonuevo = new VentasRemitoDetalleNuevoDTO();
		 	model.addAttribute("remito", remitonuevo);
			model.addAttribute("titulo", "Crear Remito Detalle");
			return "redirect:/ventas/remito/nuevodetalle";
		}	
		status.setComplete();
		log.info("se creo RE exitosamente");
		flash.addFlashAttribute("success", "Remito Detalle creada con éxito!");
		return "redirect:/index";
	}
	 
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		VentasRemito remito = clienteService.findRemitoVentasById(id);
		if(remito != null) {
			clienteService.deleteRemitoVentas(remito, id);
			flash.addFlashAttribute("success", "Remito eliminado con éxito!");
			return "redirect:/ventas/remito/listar" ;
		}
		flash.addFlashAttribute("error", "El remito no existe en la base de datos, no se pudo eliminar!");
		return "redirect:/ventas/remito/listar";
	}
	
	@GetMapping(value = "/listar")
	public String listar( @Valid @ModelAttribute("remito") VentasRemitoBusquedaDTO remitoDTO, BindingResult result ,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50, Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasRemito> remitos = remitoVentasService.buscarVentasRemitos(remitoDTO, pageRequest);
		PageRender<VentasRemito> pageRender = new PageRender<VentasRemito>("/ventas/remito/listar", remitos);
		model.addAttribute("titulo", "Listado de Remitos");
		model.addAttribute("remitos", remitos);
		model.addAttribute("page", pageRender);
		return "/ventas/remito/listar";
	}
	
	@RequestMapping(value = "/buscar")
	public String search(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		VentasRemitoBusquedaDTO remito = new VentasRemitoBusquedaDTO();
		model.put("remito", remito);
		model.put("titulo", "Buscar Remito");
		return "/ventas/remito/buscar";
	}
	
	@GetMapping(value = "/exportar")
	public String exportarRemitos(@Valid @ModelAttribute("remito") VentasRemitoBusquedaDTO remitoDTO, BindingResult result,
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
			return "redirect:/ventas/remito/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/remito/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/remito/buscar";
		}
		List<VentasRemito> remitos = remitoVentasService.buscarVentasRemitos(remitoDTO);
		model.addAttribute("fechainicio", remitoDTO.getFechainicio());
		model.addAttribute("fechafin", remitoDTO.getFechafin());
		model.addAttribute("titulo", "Listado de Remitos");
		model.addAttribute("remitos", remitos);
		return "/ventas/remito/exportar.xlsx";
	}
	
	
	@PostMapping(value = "/buscar")
	public String resultadoBusquedaRemito( @Valid @ModelAttribute("remito") VentasRemitoBusquedaDTO remitoDTO, BindingResult result ,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, RedirectAttributes flash,
			HttpServletRequest request) {
			if(result.hasErrors()) {
				log.info("error redireccionando a busqueda");
				return "redirect:/ventas/remito/buscar";
			}
			if ((remitoDTO.getFechainicio() != null && remitoDTO.getFechafin() != null) && remitoDTO.getFechainicio().after(remitoDTO.getFechafin())) {
				if (!remitoDTO.getFechainicio().equals(remitoDTO.getFechafin())) {
					flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
					return "redirect:/ventas/remito/buscar";
				}
			}
			if ((remitoDTO.getNumeromin() != null && remitoDTO.getNumeromax() != null) && remitoDTO.getNumeromax() < remitoDTO.getNumeromin()) {
				flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
				return "redirect:/ventas/remito/buscar";
			}
			if ((remitoDTO.getImportemin() != null && remitoDTO.getImportemax() != null) && remitoDTO.getImportemax() < remitoDTO.getImportemin()) {
				flash.addFlashAttribute("error", "El rango de importe mínimo no puede ser mayor que el máximo!");
				return "redirect:/ventas/remito/buscar";
			}
			
		Pageable pageRequest = PageRequest.of(page, 50, Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasRemito> remitos = remitoVentasService.buscarVentasRemitos(remitoDTO, pageRequest);
		PageRender<VentasRemito> pageRender = new PageRender<VentasRemito>("/ventas/remito/listar", remitos);
		model.addAttribute("titulo", "Listado de Remitos");
		model.addAttribute("remitos", remitos);
		model.addAttribute("page", pageRender);
		return "/ventas/remito/listar";
	}
	
	
}
