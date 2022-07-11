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
@Table(name = "ventas_items_retenciones")
public class VentasItemReciboRetencion  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "retencion_id")
	private Retencion retencion;
	
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

 

	public Retencion getRetencion() {
		return retencion;
	}

	public void setRetencion(Retencion retencion) {
		this.retencion = retencion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
