package com.recicladosplasticos.springboot.app.models.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;

public class Item {
	
	
	@Digits(integer=12, fraction=2)
	private BigDecimal cantidad;
	
	@Digits(integer=12, fraction=2)
	private BigDecimal precio;
	
	@Digits(integer=12, fraction=2)
	private BigDecimal descuento;
	
	@Digits(integer=12, fraction=2)
	private BigDecimal importeNeto;
	
	private Producto producto;

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	
	public BigDecimal calcularImporteNeto() {
		
		BigDecimal dto = BigDecimal.valueOf(1).subtract(this.getDescuento().divide(BigDecimal.valueOf(100)));
		BigDecimal precioneto = this.getPrecio().multiply(dto);
		
		BigDecimal iNeto =  this.getCantidad().multiply(precioneto) ;
		
		return iNeto;
	}

	
	
	
	

}
