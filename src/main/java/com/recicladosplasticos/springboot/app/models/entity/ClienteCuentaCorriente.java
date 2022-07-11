package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

 

@Entity
@Table(name = "ventas_cuentas_corrientes")
public class ClienteCuentaCorriente implements Serializable {

 
	private static final long serialVersionUID = 1L;


	@Id
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	private Cliente cliente;
	
 	private Double	debe;
	
	private Double	haber;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

 

	public Double getDebe() {
		return debe;
	}

	public void setDebe(Double debe) {
		this.debe = debe;
	}

	public Double getHaber() {
		return haber;
	}

	public void setHaber(Double haber) {
		this.haber = haber;
	}

	
	
}
