package com.recicladosplasticos.springboot.app.controllers;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.Unidad;
import com.recicladosplasticos.springboot.app.models.service.ProductoService;
import com.recicladosplasticos.springboot.app.models.service.UnidadService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/producto")
@SessionAttributes("producto")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UnidadService unidadService;
	
	@ModelAttribute("unidades")
	public List<Unidad> unidades(){
 		List<Unidad> unidades =    unidadService.findAll();
 		unidades.sort(Comparator.comparing(Unidad::getNombre));
 		return  unidades;
	}
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@Valid @ModelAttribute("producto") Producto producto, 
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page,50, Sort.by("nombre"));
		Page<Producto> productos = productoService.findByNombreAndDescripcion(producto.getNombre(), producto.getDescripcion(), pageRequest);
		
		PageRender<Producto> pageRender = new PageRender<Producto>("/producto/listar", productos);
		model.addAttribute("titulo", "Listado de productos y materiales");
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		return "producto/listar";
	}
	
	@RequestMapping(value = "/nuevo")
	public String crear(Map<String, Object> model) {
		Producto  producto = new Producto();
		Unidad unidad = new Unidad();
		producto.setUnidad(unidad);
		model.put("producto", producto);
		model.put("titulo", "Crear Producto");
		return "/producto/nuevo";
	}
	
	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String guardar(@ModelAttribute("producto") @Valid Producto producto, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Producto");
			return "/producto/nuevo";
		}
		productoService.save(producto);
		model.addAttribute("producto", producto);	
		status.setComplete();
		return "redirect:/producto/listar";
	}
	
	
	@RequestMapping(value = "/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Producto producto = null;
		if (id > 0) {
			 producto = productoService.findOne(id);
			if (producto == null) {
				flash.addFlashAttribute("error", "El ID del Producto no existe en la BBDD!");
				return "redirect:/producto/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/producto/listar";
		}
		model.put("producto", producto);
		model.put("titulo", "Editar Producto");
		return "/producto/nuevo";
	}
	
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			try {
				  productoService.delete(id);
				  flash.addFlashAttribute("success", "Producto eliminado con éxito!");
			} catch (Exception e) {
				e.printStackTrace(System.out);
				flash.addFlashAttribute("error", "No puede borrar el producto, puede que existan documentos asociados!");
				return "redirect:/producto/listar";
			}  
		}
		return "redirect:/producto/listar";
		
	}
	
	@RequestMapping(value = "/buscar")
	public String search(Map<String, Object> model) {
		Producto producto = new Producto();
		model.put("producto", producto);
		model.put("titulo", "Buscar Producto");
		return "/producto/buscar";
	}
	
	
	@PostMapping(value = "/buscar")
	public String buscarProductos(@Valid @ModelAttribute("producto") Producto producto, 
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "nombre", required = false) String nombre, 
			@RequestParam(name = "descripcion", required = false) String descripcion, 
			Authentication authentication, HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50,Sort.by("nombre"));
		Page<Producto> productos = productoService.findByNombreAndDescripcion(producto.getNombre(), producto.getDescripcion(), pageRequest);
		PageRender<Producto> pageRender = new PageRender<Producto>("/producto/listar", productos);
		
		model.addAttribute("titulo", "Listado de Productos y Materiales");
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		return "/producto/listar";
	}

}

