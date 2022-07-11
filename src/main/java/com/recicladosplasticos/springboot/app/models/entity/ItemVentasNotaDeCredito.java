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
@Table(name = "ventas_credito_items")
public class ItemVentasNotaDeCredito  implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Digits(integer=12, fraction=2)
	private BigDecimal cantidad;
	
	@Digits(integer=12, fraction=2)
	private BigDecimal precio;
	
	@Digits(integer=12, fraction=2)
	private BigDecimal descuento;
	
	@Digits(integer=12, fraction=2)
	private BigDecimal importeNeto;

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

	public BigDecimal calcularImporte() {
		return this.cantidad.multiply(this.producto.getPrecio());
	}
 
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
 
	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getImporteNeto() {
		return importeNeto;
	}

	public void setImporteNeto(BigDecimal importeNeto) {
		this.importeNeto = importeNeto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	
 
 
	public BigDecimal precioNeto() {
		
		BigDecimal dto = BigDecimal.valueOf(1).subtract(this.getDescuento().divide(BigDecimal.valueOf(100)));
		BigDecimal precioneto = this.getPrecio().multiply(dto);
		
		BigDecimal iNeto =  this.getCantidad().multiply(precioneto) ;
		
		return iNeto;
	}
 

}
