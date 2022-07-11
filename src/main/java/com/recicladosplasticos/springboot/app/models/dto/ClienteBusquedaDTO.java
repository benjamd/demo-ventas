package com.recicladosplasticos.springboot.app.models.dto;
 

public class ClienteBusquedaDTO {
	
	private String codigo;
	private String nombre; 
	private String razonsocial; 
	private String cuit;
	private String actividad;
	private String direccion;
	private String codigopostal;
	private String localidad;
	private String partido;
	private String provincia;
	private String pais;
	private String contacto;
	private String email;
	private String telefono;
	
	
	
	
	public ClienteBusquedaDTO() {
	
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRazonsocial() {
		return razonsocial;
	}
	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodigopostal() {
		return codigopostal;
	}
	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getPartido() {
		return partido;
	}
	public void setPartido(String partido) {
		this.partido = partido;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
	

}
