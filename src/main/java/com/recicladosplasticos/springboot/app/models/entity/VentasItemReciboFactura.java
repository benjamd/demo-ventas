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
@Table(name = "ventas_items_recibo_facturas")
public class VentasItemReciboFactura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "importe_pago")
	@Digits(integer=12, fraction=2)
	private BigDecimal importepago;
	
	@Column(name = "importe_imputado")
	@Digits(integer=12, fraction=2)
	private BigDecimal importeimputado;
 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "factura_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private VentasFactura ventasFactura;
 
 
	private static final long serialVersionUID = 1L;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

 


	public BigDecimal getImportepago() {
		return importepago;
	}


	public void setImportepago(BigDecimal importepago) {
		this.importepago = importepago;
	}


	public BigDecimal getImporteimputado() {
		return importeimputado;
	}


	public void setImporteimputado(BigDecimal importeimputado) {
		this.importeimputado = importeimputado;
	}


	public VentasFactura getFactura() {
		return ventasFactura;
	}


	public void setFactura(VentasFactura ventasFactura) {
		this.ventasFactura = ventasFactura;
	}

 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
