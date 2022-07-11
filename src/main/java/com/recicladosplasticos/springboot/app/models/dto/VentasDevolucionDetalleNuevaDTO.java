package com.recicladosplasticos.springboot.app.models.dto;

 
import java.util.Date;
 

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;

public class VentasDevolucionDetalleNuevaDTO {
	
	//@NotNull
	private String prefijo;

	//@NotNull
	private Long numero;
	 
	private Boolean anulada;

	private Double cantidadtotal;

	private String observacion;
	
	@NotEmpty(message = "Debe escribir el detalle de la devolucion")
	private String detalle;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private Date fecha;

	private Cliente cliente;

 
	private PuntoDeVenta pVenta;

	public VentasDevolucionDetalleNuevaDTO() {
	 
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

	public Boolean getAnulada() {
		return anulada;
	}

	public void setAnulada(Boolean anulada) {
		this.anulada = anulada;
	}

	public Double getCantidadtotal() {
		return cantidadtotal;
	}

	public void setCantidadtotal(Double cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

 

	public PuntoDeVenta getpVenta() {
		return pVenta;
	}

	public void setpVenta(PuntoDeVenta pVenta) {
		this.pVenta = pVenta;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	

}
