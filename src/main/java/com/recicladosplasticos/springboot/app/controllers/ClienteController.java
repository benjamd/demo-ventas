package com.recicladosplasticos.springboot.app.controllers;


 
import java.util.ArrayList;
import java.util.Arrays;

 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recicladosplasticos.springboot.app.models.dto.ClienteBusquedaDTO;
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
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;
 
 

@Controller
@RequestMapping("/ventas/cliente")
@SessionAttributes("cliente")
public class ClienteController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private VentasFacturaService ventasFacturaService;
	
	@Autowired
	private VentasNotaDeCreditoService creditoService;
	
	@Autowired
	private VentasNotaDeDebitoService debitoService;
	
	
	@Autowired
	private VentasReciboService reciboService;
	

	@ModelAttribute("paises")
	public List<String> paises(){
		
		return Arrays.asList("Argentina");
		
	}
	
	@ModelAttribute("provincias")
	public List<String> provincias(){
		
		return Arrays.asList("Buenos Aires", "CABA", "Catamarca", "Chaco", "Chubut", "Córdoba",
				"Corrientes", "Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja",
				"Mendoza", "Misiones", "Neuquén", "Salta", "San Juan", "San Luis", "Santa Cruz",
				"Santa Fe", "Santiago del Estero", "Río Negro", "Tierra del Fuego", "Tucumán");
	}
	
	@ModelAttribute("ivas")
	public List<String> responsablesIvas(){
		
		return Arrays.asList("Responsable Inscripto", "IVA Exento",
				"Consumidor Final", "Responsable Monotributo");
	}
	
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/ventas/cliente/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		return "/ventas/cliente/cliente";
	}
	
	@GetMapping(value = "/listar")
	public String listar(@Valid @ModelAttribute("cliente") ClienteBusquedaDTO cliente,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50,Sort.by("nombre").ascending());

		Page<Cliente> clientes = clienteService.findCliente("", cliente.getNombre(), cliente.getRazonsocial(), cliente.getCuit(), cliente.getActividad(), 
				cliente.getDireccion(), cliente.getCodigopostal(), cliente.getLocalidad(), cliente.getPartido(), cliente.getProvincia(), cliente.getPais(), cliente.getContacto(), cliente.getEmail(),  cliente.getTelefono(), pageRequest);
		PageRender<Cliente> pageRender = new PageRender<Cliente>("/ventas/cliente/listar", clientes);
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "/ventas/cliente/listar";
	}

	@RequestMapping(value = "/buscar")
	public String search(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Buscar Cliente");
		return "/ventas/cliente/buscar";
	}
	
	
	@RequestMapping(value = "/nuevo")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Crear Cliente");
		return "/ventas/cliente/nuevo";
	}

	@RequestMapping(value = "/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				return "redirect:/ventas/cliente/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/ventas/cliente/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "/ventas/cliente/nuevo";
	}
	
    @PostMapping(value = "/nuevo")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Cliente");
			return "/ventas/cliente/nuevo";
		}
 		clienteService.save(cliente);
		status.setComplete();
		return "redirect:/ventas/cliente/listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Authentication authentication) {
		
		if (id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
			return "redirect:/ventas/cliente/listar";
		}
		
		flash.addFlashAttribute("error", "Cliente no existe en la base de datos, no se pudo eliminar!");
		return "redirect:/ventas/cliente/listar";
	}
	
	
	@PostMapping(value = "/buscar")
	public String buscarClientes(@Valid @ModelAttribute("cliente") ClienteBusquedaDTO cliente, BindingResult result,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre, 
			@RequestParam(name = "razonsocial", required = false) String razonsocial, 
			@RequestParam(name = "cuit", required = false) String cuit,
			@RequestParam(name = "actividad", required = false) String actividad,
			@RequestParam(name = "direccion", required = false) String direccion,
			@RequestParam(name = "codigopostal", required = false) String codigopostal,
			@RequestParam(name = "localidad", required = false) String localidad,
			@RequestParam(name = "partido", required = false) String partido,
			@RequestParam(name = "provincia", required = false) String provincia,
			@RequestParam(name = "pais", required = false) String pais,
			@RequestParam(name = "contacto", required = false) String contacto,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "telefono", required = false) String telefono,
			Authentication authentication,
			HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50,Sort.by("nombre").ascending());
		Page<Cliente> clientes = clienteService.findCliente(codigo, nombre, razonsocial, cuit, actividad, direccion, codigopostal, localidad, partido, provincia, pais, contacto,  email,  telefono, pageRequest);
		PageRender<Cliente> pageRender = new PageRender<Cliente>("/ventas/cliente/listar", clientes);
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "/ventas/cliente/listar";
	}
	
	@GetMapping(value = "/ctacteventas/") 
	public String exportarFacturaVentasDTO( Map<String, Object> model, Authentication authentication, RedirectAttributes flash,
			HttpServletRequest request, HttpServletResponse response) {
			response.setHeader("Content-Disposition", "attachment; filename=\"cliente_ctacte.xlsx\" ");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
 			Cliente cliente = (Cliente) model.get("cliente");
			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				return "redirect:/ventas/cliente/listar";
			}
 			List<VentasFactura> ventasFacturas = ventasFacturaService.fetchFacturaByIdWithCliente(cliente.getId());
 			List<VentasNotaDeCredito> creditos = creditoService.fetchCreditoByIdWithCliente(cliente.getId());	
 			List<VentasNotaDeDebito> debitos = debitoService.fetchDebitoByIdWithCliente(cliente.getId());	
 			List<VentasRecibo> recibos = reciboService.fetchReciboByIdWithCliente(cliente.getId());
 		 	List<Documento> documentos = new ArrayList<Documento>();
 			if(ventasFacturas != null) documentos.addAll(ventasFacturas);
 			if(creditos != null) documentos.addAll(creditos);
 			if(debitos != null) documentos.addAll(debitos);
 			if(recibos != null)documentos.addAll(recibos);
 			model.put("documentos", documentos);
			model.put("cliente", cliente);
			return "/ventas/cliente/ctacte.xlsx";
 	}
	
	
}
