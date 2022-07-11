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
 

@Entity
@Table(name = "ventas_recibos_pendientes")
public class VentasReciboPendiente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "importe_pendiente")
	@Digits(integer=12, fraction=2)
	private BigDecimal importependiente;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recibo_imputado_id")
	private VentasRecibo recibo;
	
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 
	public VentasRecibo getRecibo() {
		return recibo;
	}

	public void setRecibo(VentasRecibo recibo) {
		this.recibo = recibo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public BigDecimal getImportependiente() {
		return importependiente;
	}

	public void setImportependiente(BigDecimal importependiente) {
		this.importependiente = importependiente;
	}


}
