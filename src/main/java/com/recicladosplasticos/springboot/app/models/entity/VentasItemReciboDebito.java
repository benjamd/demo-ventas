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
@Table(name = "ventas_items_recibo_debitos")
public class VentasItemReciboDebito implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "importe_imputado")
	@Digits(integer=12, fraction=2)
	private BigDecimal importeimputado;
	
	@Column(name = "importe_pago")
	@Digits(integer=12, fraction=2)
	private BigDecimal importepago;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "debito_id")
	private VentasNotaDeDebito debito;
	
	
	
	private static final long serialVersionUID = 1L;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



 

	public VentasNotaDeDebito getDebito() {
		return debito;
	}



	public void setDebito(VentasNotaDeDebito debito) {
		this.debito = debito;
	}



 

	public BigDecimal getImporteimputado() {
		return importeimputado;
	}



	public void setImporteimputado(BigDecimal importeimputado) {
		this.importeimputado = importeimputado;
	}



	public BigDecimal getImportepago() {
		return importepago;
	}



	public void setImportepago(BigDecimal importepago) {
		this.importepago = importepago;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
