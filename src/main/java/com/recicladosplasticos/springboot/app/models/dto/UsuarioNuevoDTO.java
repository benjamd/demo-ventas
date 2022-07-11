package com.recicladosplasticos.springboot.app.models.dto;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.recicladosplasticos.springboot.app.models.entity.Role;


public class UsuarioNuevoDTO {
	
	private Long id;
	
	@NotBlank
	private String nombre;
	
	@NotBlank
	private String apellido;
	
	@NotBlank
	@Size(min = 4, max = 30) 
	private String username;
	
	@NotBlank
	@Size(min = 8, max = 30)
	private String password;
	
	@NotBlank
	@Size(min = 8, max = 30)
	private String confirmpassword;
	
	private Boolean enabled;
	
	private Boolean admin;
	
	private List<Role> roles;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	} 

	public Boolean tieneRole(String role) {
		
		 for (Role userRole: this.roles ) {
			if(userRole.getAuthority().equals(role)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities(); 
		
		 return authorities.contains(new SimpleGrantedAuthority(role));
		
		/*
		 * 
		 for (GrantedAuthority authority: authorities ) {
			if(role.equals(authority.getAuthority())) {
				return true;
			}
		}
		
		return false;
		*/
	}

	

}
