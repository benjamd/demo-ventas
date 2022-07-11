package com.recicladosplasticos.springboot.app.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.recicladosplasticos.springboot.app.models.dto.UsuarioEditarDTO;
import com.recicladosplasticos.springboot.app.models.dto.UsuarioNuevoDTO;
import com.recicladosplasticos.springboot.app.models.entity.Role;
import com.recicladosplasticos.springboot.app.models.entity.Usuario;
import com.recicladosplasticos.springboot.app.models.service.RoleService;
import com.recicladosplasticos.springboot.app.models.service.UsuarioService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/usuarios")
@SessionAttributes("usuario")
public class UsuarioController {
	

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

/*	
	@ModelAttribute("listaroles")
	public List<Role> listaroles() {
		
		List<Role> listaroles =  new ArrayList<Role>(); 
		Role adminRole = new Role();
		adminRole.setId(1L);
		adminRole.setAuthority("ROLE_ADMIN");
		Role userRole = new Role();
		userRole.setId(2L);
		userRole.setAuthority("ROLE_USER");
		
		listaroles.add(adminRole);
		listaroles.add(userRole);
		listaroles.sort(Comparator.comparing(Role::getAuthority));
		
		return  listaroles;
		
	}
*/	
	@ModelAttribute("listaroles")
	public List<String> listaroles(){
		
		return Arrays.asList("ROLE_ADMIN", "ROLE_USER");
	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = usuarioService.findOne(id);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El usuario no existe en la base de datos");
			return "redirect:/usuarios/listar";
		}

		model.put("usuario", usuario);
		model.put("titulo", "Detalle de Usuario");
		return "/usuarios/usuario";
	}	


	

	@GetMapping(value = "/listar")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, 50,Sort.by("username"));

		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);  

		PageRender<Usuario> pageRender = new PageRender<Usuario>("/usuarios/listar", usuarios);
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		return "/usuarios/listar";
	}
	
		

	@RequestMapping(value = "/buscar")
	public String search(Map<String, Object> model) {

		UsuarioNuevoDTO usuario = new UsuarioNuevoDTO();
		model.put("usuario", usuario);
		model.put("titulo", "Buscar Usuario");
		return "/usuarios/buscar";
	}
	
	@RequestMapping(value = "/nuevo")
	public String crear(Map<String, Object> model) {

		UsuarioNuevoDTO usuario = new UsuarioNuevoDTO();
		model.put("usuario", usuario);
		model.put("titulo", "Crear Usuario");
		return "/usuarios/nuevo";
	}


	@RequestMapping(value = "/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = null;

		if (id > 0) {
			usuario = usuarioService.findOne(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del usuario no existe en la BBDD!");
				return "redirect:/usuarios/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del usuario no puede ser cero!");
			return "redirect:/usuario/listar";
		}
		
		UsuarioEditarDTO usuarioDto = new UsuarioEditarDTO();
		usuarioDto.setUsername(usuario.getUsername());
		usuarioDto.setNombre(usuario.getNombre());
		usuarioDto.setApellido(usuario.getApellido());
		usuarioDto.setEnabled(usuario.getEnabled());
		usuarioDto.setRoles(usuario.getRoles());
		usuarioDto.setId(usuario.getId());
		if(usuario.isAdmin())
			usuarioDto.setAdmin(true);
		
		
		model.put("usuario", usuarioDto);
		model.put("titulo", "Editar Usuario");
		return "/usuarios/editar";
	}
	
    @PostMapping(value = "/nuevo")
	public String guardar(@Valid @ModelAttribute("usuario") UsuarioNuevoDTO usuarioDto, BindingResult result, Model model,
			SessionStatus status,  RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Usuario");
			return "/usuarios/nuevo";
		}
		
		if((!usuarioDto.getPassword().equals(usuarioDto.getConfirmpassword())) &&
			(usuarioDto.getPassword() != null && (!usuarioDto.getPassword().equals(""))) &&
			(usuarioDto.getConfirmpassword() != null && (!usuarioDto.getConfirmpassword().equals("")))  ) {
					
				flash.addFlashAttribute("error", "Error: El campo de Password y Confirmar Password no coinciden!");
					
				model.addAttribute("titulo", "Crear Usuario");
				model.addAttribute("error", "Error: El campo de Password y Confirmar Password no coinciden!");
				return "/usuarios/nuevo";
			
		}
		
		
		

		Usuario usuario = new Usuario();
		usuario.setUsername(usuarioDto.getUsername());
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellido(usuarioDto.getApellido());
		usuario.setEnabled(true);
		
		if(usuarioDto.getPassword() != null && (!usuarioDto.getPassword().equals("")))
				usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
		
		Role rolUser = new Role();
		rolUser.setAuthority("ROLE_USER");
		usuario.addRole(rolUser);
		
		if(usuarioDto.getAdmin() != null && usuarioDto.getAdmin()) {
			Role rolAdmin = new Role();
			rolAdmin.setAuthority("ROLE_ADMIN");
			usuario.addRole(rolAdmin);
		}
			
		
		usuarioService.save(usuario);
		status.setComplete();
 
		return "redirect:/usuarios/listar";
	}
    
    
    @PostMapping(value = "/editar")
	public String editar(@Valid @ModelAttribute("usuario") UsuarioEditarDTO usuarioDto, BindingResult result,
			Model model, SessionStatus status, RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Editar Usuario");
			return "/usuarios/editar";
		}
		
		if((!usuarioDto.getPassword().equals(usuarioDto.getConfirmpassword())) &&
		   (usuarioDto.getPassword() != null && (!usuarioDto.getPassword().equals(""))) &&
		   (usuarioDto.getConfirmpassword() != null && (!usuarioDto.getConfirmpassword().equals("")))  ) {
			
				flash.addFlashAttribute("error", "Error: El campo de Password y Confirmar Password no coinciden!");
			
				model.addAttribute("titulo", "Editar Usuario");
				model.addAttribute("error", "Error: El campo de Password y Confirmar Password no coinciden!");
				return "/usuarios/editar";
	
		}
		
		Usuario usuario = null;

		if (usuarioDto.getId() > 0) {
			usuario = usuarioService.findOne(usuarioDto.getId());
			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del usuario no existe en la BBDD!");
				return "redirect:/usuarios/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del usuario no puede ser cero!");
			return "redirect:/usuario/listar";
		}
	
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellido(usuarioDto.getApellido());
		usuario.setEnabled(usuarioDto.getEnabled());
		
		//usuario.setRoles(usuarioDto.getRoles());
		if(usuarioDto.getAdmin() && !usuario.isAdmin()) {
			Role rolAdmin = new Role();
			rolAdmin.setAuthority("ROLE_ADMIN");
			usuario.addRole(rolAdmin);
		} 
		
		if(!usuarioDto.getAdmin()) {
			Role roleAdmin = null;
			for (Role rol: usuario.getRoles()) {
				if(rol.getAuthority().equals("ROLE_ADMIN"))
					roleAdmin = rol;
			}
			if(roleAdmin != null) {
				usuario.getRoles().remove(roleAdmin);
				roleService.delete(roleAdmin.getId());
		
			}
		}
		
		
		if(usuarioDto.getPassword() != null && (!usuarioDto.getPassword().equals(""))) {
			usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
			
		}
		
		usuarioService.save(usuario);
		status.setComplete();
 
		return "redirect:/usuarios/listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
	 
			usuarioService.delete(id);
			flash.addFlashAttribute("success", "Usuario eliminado con éxito!");


		}
		return "redirect:/usuarios/listar";
	}


	@PostMapping(value = "/buscar")
	public String buscarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, @Valid @ModelAttribute("usuario") UsuarioNuevoDTO usuarioDto,
			BindingResult result, Model model, Authentication authentication,
			HttpServletRequest request) {

 
		Pageable pageRequest = PageRequest.of(page, 50,Sort.by("username"));
		
		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);
		
		PageRender<Usuario> pageRender = new PageRender<Usuario>("/usuarios/listar", usuarios);
		model.addAttribute("titulo", "Listado de Usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		return "/usuarios/listar";
	}

	 

}
