package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ventas_items_Depositos_transferncias")
public class VentasItemDepositoTransferencia  implements Serializable {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "deposito_tranferencia_id")
	private DepositoTransferencia depositotranferencia;
	
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DepositoTransferencia getDepositotranferencia() {
		return depositotranferencia;
	}

	public void setDepositotranferencia(DepositoTransferencia depositotranferencia) {
		this.depositotranferencia = depositotranferencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
