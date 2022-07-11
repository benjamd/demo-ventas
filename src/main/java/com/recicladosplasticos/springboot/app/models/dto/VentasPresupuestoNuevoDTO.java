package com.recicladosplasticos.springboot.app.models.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.springframework.format.annotation.DateTimeFormat;

import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasPresupuesto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;

public class VentasPresupuestoNuevoDTO {
 
	private Long id;

	//@NotNull
	private String prefijo;

	//@NotNull
	private Long numero;
 	 
	private Boolean anulada;
 	 
	private Double saldopendiente;
 
	private Double importe;
 
	private String observacion;
	
	private String letra;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private Date fecha;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	
	private Cliente cliente;

	private List<ItemVentasPresupuesto> items;
	
	private List<PuntoDeVenta> puntosDeVenta;
	
	private PuntoDeVenta pVenta;
	
	private String condventa;
	
  
	public PuntoDeVenta getpVenta() {
		return pVenta;
	}

	public void setpVenta(PuntoDeVenta pVenta) {
		this.pVenta = pVenta;
	}

	public VentasPresupuestoNuevoDTO() {

		this.items = new ArrayList<ItemVentasPresupuesto>();
		this.puntosDeVenta = new ArrayList<PuntoDeVenta>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getSaldopendiente() {
		return saldopendiente;
	}

	public void setSaldopendiente(Double saldopendiente) {
		this.saldopendiente = saldopendiente;
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemVentasPresupuesto> getItems() {
		return items;
	}

	public void setItems(List<ItemVentasPresupuesto> items) {
		this.items = items;
	}

	public List<PuntoDeVenta> getPuntosDeVenta() {
		return puntosDeVenta;
	}

	public void setPuntosDeVenta(List<PuntoDeVenta> puntosDeVenta) {
		this.puntosDeVenta = puntosDeVenta;
	}
	
	public void addPuntoDeVentaDisponible(PuntoDeVenta puntoDeVenta) {
		
		this.puntosDeVenta.add(puntoDeVenta);		
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getCondventa() {
		return condventa;
	}

	public void setCondventa(String condventa) {
		this.condventa = condventa;
	}

 
	
}
