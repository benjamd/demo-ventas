package com.recicladosplasticos.springboot.app.controllers;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.service.PuntoDeVentaService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;
 

@Controller
@RequestMapping("/puntodeventa")
@SessionAttributes("puntodeventa")
public class PuntoDeVentaController {

	@Autowired
	private PuntoDeVentaService puntodeventaService;
	
	
	@ModelAttribute("documentos")
	public List<String> documentos(){
		return Arrays.asList("Devolución", "Factura", "Nota de Crédito", 
				"Nota de Débito", "Presupuesto de Venta", "Remito");
	}
	
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
	
	
	
	
	@RequestMapping(value = "/nuevo")
	public String crear(Map<String, Object> model) {
		PuntoDeVenta  puntodeventa = new PuntoDeVenta();
		model.put("puntodeventa", puntodeventa);
		model.put("titulo", "Crear Punto de Venta");
		return "/puntodeventa/nuevo";
	}
	
	
	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("puntodeventa") PuntoDeVenta puntodeventa, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Punto de Venta");
			return "/puntodeventa/nuevo";
		}
		puntodeventaService.save(puntodeventa);
		status.setComplete();
		return "redirect:/puntodeventa/listar";
	}
	
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page,50, Sort.by("nombre"));
		Page<PuntoDeVenta> puntosdeventa = puntodeventaService.findAll(pageRequest);
		PageRender<PuntoDeVenta> pageRender = new PageRender<PuntoDeVenta>("/puntodeventa/listar", puntosdeventa);
		model.addAttribute("titulo", "Listado de Puntos de Venta");
		model.addAttribute("puntosdeventa", puntosdeventa);
		model.addAttribute("page", pageRender);
		return "/puntodeventa/listar";
	}
	
	
	@RequestMapping(value = "/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		PuntoDeVenta puntodeventa = null;
		if (id > 0) {
			puntodeventa = puntodeventaService.findOne(id);
			if (puntodeventa == null) {
				flash.addFlashAttribute("error", "El ID del Punto de Venta no existe en la BBDD!");
				return "redirect:/puntodeventa/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del Punto de Venta no puede ser cero!");
			return "redirect:/puntodeventa/listar";
		}
		model.put("puntodeventa", puntodeventa);
		model.put("titulo", "Editar Punto de Venta");
		return "/puntodeventa/ptovta";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
		    puntodeventaService.delete(id);
			flash.addFlashAttribute("success", "Punto de Venta eliminado con éxito!");
		}
		return "redirect:/puntodeventa/listar";
	}
}
