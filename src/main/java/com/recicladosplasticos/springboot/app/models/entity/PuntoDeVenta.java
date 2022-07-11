package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;

 
import javax.persistence.Column;
import javax.persistence.Entity;
 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

 
@Entity
@Table(name = "puntos_de_venta")
public class PuntoDeVenta  implements Serializable  {

 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "NOMBRE: no puede ser un campo vacío")
	private String nombre;
	
	
	//@OneToMany(mappedBy = "punto_de_venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private  Factura factura;
	
	private  String documento;
	
	@NotEmpty(message = "TIPO: no puede ser un campo vacío")
	private String tipo; //TIPO "A" o "B" si es fc
	
	@NotEmpty(message = "PREFIJO: no puede ser un campo vacío")
	private String prefijo;
	
	@NotNull(message = "NÚMERO: no puede tener un valor nulo")
	@Positive 
	private Long numero;
	
	@NotEmpty(message = "DIRECCIÓN: no puede ser un campo vacío")
	private String direccion;
	
	@NotEmpty(message = "CÓDIGO POSTAL: no puede ser un campo vacío")
	@Column(name = "codigo_postal")
	private String codigopostal;
	
	@NotEmpty(message = "LOCALIDAD: no puede ser un campo vacío")
	private String localidad;
	
	@NotEmpty(message = "PARTIDO no puede ser un campo vacío")
	private String partido;
	
	
	private String provincia;
	
 	private String pais;
 
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
	
}
