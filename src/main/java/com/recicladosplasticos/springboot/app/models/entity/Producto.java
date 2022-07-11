package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "productos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto  implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Nombre no puede ser un campo vacío")
	private String nombre;
	
	@Digits(integer=12, fraction=2)
	private BigDecimal precio;
	 
	@Digits(integer=12, fraction=2)
	private BigDecimal stock;
	
	@NotEmpty(message = "Descripción no puede ser un campo vacío")
	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	//@NotEmpty(message = "Descripción no puede ser un campo vacío")
	@ManyToOne(fetch = FetchType.EAGER)
	private Unidad unidad;

	
	private static final long serialVersionUID = 1L;
	
	public Producto() {
		
		this.unidad = new Unidad();
	 
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
		precio =  new BigDecimal(0);
		stock = new BigDecimal(0);
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

 	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

 	
	public String getDescripcion() {
		return descripcion;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
