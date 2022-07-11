package com.recicladosplasticos.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, Principal principal, RedirectAttributes flash) {
		model.addAttribute("titulo", "INICIAR SESION");
		flash.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente");
		if(principal != null) return "redirect:/";
		if(error != null) {
			model.addAttribute("error","Error de ingreso: usuario o contraseña incorrecta. Vuelva a intentar!");
		}
		if(logout != null) {
			model.addAttribute("success","Ha cerrado la sesión correctamente");
		}
		return "login";
	}
	
	
}
