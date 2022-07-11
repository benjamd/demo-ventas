package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ventas_recibos_facturas_acreditadas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VentasReciboFacturaAcreditada implements Serializable {

 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "importe_credito")
	@Digits(integer=12, fraction=2)
	private BigDecimal importecredito;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "factura_id")
	private VentasFactura ventasFactura;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "credito_id")
	private VentasNotaDeCredito credito;
	
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getImportecredito() {
		return importecredito;
	}

	public void setImportecredito(BigDecimal importecredito) {
		this.importecredito = importecredito;
	}

	public VentasFactura getFactura() {
		return ventasFactura;
	}

	public void setFactura(VentasFactura ventasFactura) {
		this.ventasFactura = ventasFactura;
	}

	public VentasNotaDeCredito getCredito() {
		return credito;
	}

	public void setCredito(VentasNotaDeCredito credito) {
		this.credito = credito;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
