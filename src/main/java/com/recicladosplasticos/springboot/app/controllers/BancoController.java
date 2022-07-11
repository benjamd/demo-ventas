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

import com.recicladosplasticos.springboot.app.models.entity.Banco;
import com.recicladosplasticos.springboot.app.models.service.BancoService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/bancos")
@SessionAttributes("banco")
public class BancoController {
	
	@Autowired
	private BancoService bancoService;
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 50, Sort.by("nombre"));
		Page<Banco> bancos = bancoService.findAll(pageRequest);
		PageRender<Banco> pageRender = new PageRender<Banco>("/bancos/listar", bancos);
		model.addAttribute("titulo", "Listado de Bancos");
		model.addAttribute("bancos", bancos);
		model.addAttribute("page", pageRender);
		return "bancos/listar";
	}
	
	@RequestMapping(value = "/nuevo")
	public String crear(Map<String, Object> model) {
		Banco  banco = new Banco();
		model.put("banco", banco);
		model.put("titulo", "Crear Banco");
		return "/bancos/nuevo";
	}
	
	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String guardar(@Valid Banco banco, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Banco");
			return "/bancos/nuevo";
		}
		bancoService.save(banco);
		model.addAttribute("banco", banco);	
		status.setComplete();
		return "redirect:/bancos/listar";
	}
	
	
	@RequestMapping(value = "/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Banco banco = null;
		if (id > 0) {
			 banco = bancoService.findOne(id);
			if (banco == null) {
				flash.addFlashAttribute("error", "El ID del Banco no existe en la BBDD!");
				return "redirect:/bancos/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del banco no puede ser cero!");
			return "redirect:/bancos/listar";
		}
		model.put("banco", banco);
		model.put("titulo", "Editar Banco");
		return "/bancos/nuevo";
	}
	
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
		    bancoService.delete(id);
			flash.addFlashAttribute("success", "Banco eliminado con éxito!");
		}
		return "redirect:/bancos/listar";
	}
}
