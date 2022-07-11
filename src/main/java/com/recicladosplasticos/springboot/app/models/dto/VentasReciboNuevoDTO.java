package com.recicladosplasticos.springboot.app.models.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.recicladosplasticos.springboot.app.models.entity.Cheque;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.DepositoTransferencia;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboFactura;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;
import com.recicladosplasticos.springboot.app.models.entity.VentasReciboPendiente;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.entity.Retencion;

public class VentasReciboNuevoDTO {
	
	
	private Long id;

	 
	private String prefijo;

 
	private Long numero;
	
		 
	private Boolean anulada;

	 
	private Double importe;

	 
	private Double pago;

 

	private String observacion;
	


	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private Date fecha;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

 
	
	private Cliente cliente;
	
	private List<VentasFactura> facturaspendientes;
	
	private List<VentasNotaDeCredito> creditospendientes;
	
	private List<VentasNotaDeDebito> debitospendientes; 
	 
	private List<VentasRecibo> recibospendientes;
	 
	private List<VentasItemReciboFactura> facturas;
		
	private List<VentasItemReciboCredito> creditos;
		 
	private List<VentasItemReciboDebito> debitos;
	
	private List<VentasReciboPendiente> recibos;
	
	private List<PuntoDeVenta> puntosDeVenta;
	
	private PuntoDeVenta pVenta;

	private Double efectivo;
	
 
	
	private List<Cheque> cheques;
	
	private List<DepositoTransferencia> depotransfer;
 
	private List<Retencion> retenciones;
	
	
	
	public VentasReciboNuevoDTO() {
		this.facturaspendientes = new ArrayList<VentasFactura>();
		this.creditospendientes = new ArrayList<VentasNotaDeCredito>();
		this.debitospendientes = new ArrayList<VentasNotaDeDebito>(); 
		this.recibospendientes = new ArrayList<VentasRecibo>();
		
		this.cheques = new ArrayList<Cheque>();
		this.depotransfer = new ArrayList<DepositoTransferencia>();
		this.retenciones = new ArrayList<Retencion>();
		
	 	this.facturas = new ArrayList<VentasItemReciboFactura>();
		this.creditos = new ArrayList<VentasItemReciboCredito>();
		this.debitos = new ArrayList<VentasItemReciboDebito>();
		this.recibos = new ArrayList<VentasReciboPendiente>();
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

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getPago() {
		return pago;
	}

	public void setPago(Double pago) {
		this.pago = pago;
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

	public List<VentasItemReciboFactura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<VentasItemReciboFactura> facturas) {
		this.facturas = facturas;
	}

	public List<VentasItemReciboCredito> getCreditos() {
		return creditos;
	}

	public void setCreditos(List<VentasItemReciboCredito> creditos) {
		this.creditos = creditos;
	}

	public List<VentasItemReciboDebito> getDebitos() {
		return debitos;
	}

	public void setDebitos(List<VentasItemReciboDebito> debitos) {
		this.debitos = debitos;
	}

	public List<PuntoDeVenta> getPuntosDeVenta() {
		return puntosDeVenta;
	}

	public void setPuntosDeVenta(List<PuntoDeVenta> puntosDeVenta) {
		this.puntosDeVenta = puntosDeVenta;
	}

	public PuntoDeVenta getpVenta() {
		return pVenta;
	}

	public void setpVenta(PuntoDeVenta pVenta) {
		this.pVenta = pVenta;
	}

	public List<VentasFactura> getFacturaspendientes() {
		return facturaspendientes;
	}

	public void setFacturaspendientes(List<VentasFactura> facturaspendientes) {
		this.facturaspendientes = facturaspendientes;
	}

	public List<VentasNotaDeCredito> getCreditospendientes() {
		return creditospendientes;
	}

	public void setCreditospendientes(List<VentasNotaDeCredito> creditospendientes) {
		this.creditospendientes = creditospendientes;
	}

	public List<VentasNotaDeDebito> getDebitospendientes() {
		return debitospendientes;
	}

	public void setDebitospendientes(List<VentasNotaDeDebito> debitospendientes) {
		this.debitospendientes = debitospendientes;
	}

	public Double getEfectivo() {
		return efectivo;
	}

	public void setEfectivo(Double efectivo) {
		this.efectivo = efectivo;
	}


	public List<Cheque> getCheques() {
		return cheques;
	}

	public void setCheques(List<Cheque> cheques) {
		this.cheques = cheques;
	}

	public List<DepositoTransferencia> getDepotransfer() {
		return depotransfer;
	}

	public void setDepotransfer(List<DepositoTransferencia> depotransfer) {
		this.depotransfer = depotransfer;
	}

	public List<VentasRecibo> getRecibospendientes() {
		return recibospendientes;
	}

	public void setRecibospendientes(List<VentasRecibo> recibospendientes) {
		this.recibospendientes = recibospendientes;
	}

	public List<VentasReciboPendiente> getRecibos() {
		return recibos;
	}

	public void setRecibos(List<VentasReciboPendiente> recibos) {
		this.recibos = recibos;
	}


	public List<Retencion> getRetenciones() {
		return retenciones;
	}


	public void setRetenciones(List<Retencion> retenciones) {
		this.retenciones = retenciones;
	}

	 
	
 

}
