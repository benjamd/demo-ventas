package com.recicladosplasticos.springboot.app.models.dto;


import javax.validation.constraints.Size;


public class UsuarioBusquedaDTO {
	
	
	private String nombre;
	
	private String apellido;
	
	@Size(min = 4, max = 30) 
	private String username;
	
	
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



}
