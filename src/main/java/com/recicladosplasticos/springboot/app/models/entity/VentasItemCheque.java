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
@Table(name = "ventas_items_cheques")
public class VentasItemCheque implements Serializable {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cheque_id")
	private Cheque cheque;
	
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
