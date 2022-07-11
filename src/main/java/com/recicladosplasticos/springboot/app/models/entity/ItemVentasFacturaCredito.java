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
@Table(name = "facturas_items_creditos")
public class ItemVentasFacturaCredito implements Serializable {
	
 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Digits(integer=12, fraction=2)
	private BigDecimal importe;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "credito_id")
	private VentasNotaDeCredito credito;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

 

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
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
