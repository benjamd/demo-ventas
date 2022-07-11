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
@Table(name = "ventas_items_recibo_creditos")
public class VentasItemReciboCredito implements Serializable {
 

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Digits(integer=12, fraction=2)
	private BigDecimal importepago;
	
	
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

 

	public BigDecimal getImportepago() {
		return importepago;
	}

	public void setImportepago(BigDecimal importepago) {
		this.importepago = importepago;
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
