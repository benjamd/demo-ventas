package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

@Entity
@Table(name = "ventas_remito_items")
public class ItemVentasRemito implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Digits(integer=12, fraction=2)
	private BigDecimal cantidad;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	private Producto producto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

 
	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	/*
	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}
*/
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;


}
