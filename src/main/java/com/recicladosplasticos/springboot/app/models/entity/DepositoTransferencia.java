package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

 
@Entity
@Table(name = "depositos_tranferencias")
public class DepositoTransferencia implements Serializable {
	
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tipo;
	
	@Positive
	@Digits(integer=12, fraction=2)
	private BigDecimal pago;
	
	@NotEmpty
	private String comprobante;
	
	@NotEmpty
	private String banco;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;
	
	
	private static final long serialVersionUID = 1L;
	
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public BigDecimal getPago() {
		return pago;
	}


	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}


	public String getComprobante() {
		return comprobante;
	}


	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}


	public String getBanco() {
		return banco;
	}


	public void setBanco(String banco) {
		this.banco = banco;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Date getCreateAt() {
		return createAt;
	}


	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
