package com.recicladosplasticos.springboot.app.controllers;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recicladosplasticos.springboot.app.models.entity.Unidad;
import com.recicladosplasticos.springboot.app.models.service.UnidadService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/unidades")
@SessionAttributes("unidad")
public class UnidadController {
	
	@Autowired
	private UnidadService unidadService;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 50, Sort.by("nombre"));
		Page<Unidad> unidades = unidadService.findAll(pageRequest);
		PageRender<Unidad> pageRender = new PageRender<Unidad>("/unidades/listar", unidades);
		model.addAttribute("titulo", "Listado de Unidades");
		model.addAttribute("unidades", unidades);
		model.addAttribute("page", pageRender);
		return "unidades/listar";
	}
	
	@RequestMapping(value = "/nuevo")
	public String crear(Map<String, Object> model) {
		Unidad  unidad = new Unidad();
		model.put("unidad", unidad);
		model.put("titulo", "Crear Unidad");
		return "/unidades/nuevo";
	}
	
	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String guardar(@Valid Unidad unidad, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Unidad");
			return "/unidades/nuevo";
		}
		unidadService.save(unidad);
		model.addAttribute("unidad", unidad);	
		status.setComplete();
		return "redirect:/unidades/listar";
	}
	
	
	@RequestMapping(value = "/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Unidad unidad = null;
		if (id > 0) {
			 unidad = unidadService.findOne(id);
			if (unidad == null) {
				flash.addFlashAttribute("error", "El ID del Unidad no existe en la BBDD!");
				return "redirect:/unidades/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del unidad no puede ser cero!");
			return "redirect:/unidades/listar";
		}
		model.put("unidad", unidad);
		model.put("titulo", "Editar Unidad");
		return "/unidades/nuevo";
	}
	
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
		    unidadService.delete(id);
			flash.addFlashAttribute("success", "Unidad eliminado con éxito!");
		}
		return "redirect:/unidades/listar";
	}
}
