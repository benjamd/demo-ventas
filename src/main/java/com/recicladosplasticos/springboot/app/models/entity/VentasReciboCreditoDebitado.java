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
@Table(name = "ventas_recibos_creditos_debitados")
public class VentasReciboCreditoDebitado implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "importe_debito")
	@Digits(integer=12, fraction=2)
	private BigDecimal importedebito;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "debito_id")
	private VentasNotaDeDebito debito;
	
	
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
 

	public BigDecimal getImportedebito() {
		return importedebito;
	}

	public void setImportedebito(BigDecimal importedebito) {
		this.importedebito = importedebito;
	}

	public VentasNotaDeDebito getDebito() {
		return debito;
	}

	public void setDebito(VentasNotaDeDebito debito) {
		this.debito = debito;
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
