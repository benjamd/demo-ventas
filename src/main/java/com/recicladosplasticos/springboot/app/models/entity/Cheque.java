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
@Table(name = "cheques")
public class Cheque implements Serializable {

  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String banco;
	
	@Positive
	private Long numero;
	
	private String tipo;
	
	@Column(name = "pago_cheque")
	@Positive
	@Digits(integer=12, fraction=2)
	private BigDecimal  pagocheque;
	
	@NotEmpty
	private String   emisor;
	
	@Column(name = "cuit_emisor")
	@NotEmpty
	private String   cuitemisor;
	
	@Column(name = "paguese_a")
	@NotEmpty
	private String   paguesea;
	
	private String   estado;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaemision;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechapago;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;
	
	private static final long serialVersionUID = 1L;
	 
	
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
		estado = "En Cartera";
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getBanco() {
		return banco;
	}



	public void setBanco(String banco) {
		this.banco = banco;
	}



	public Long getNumero() {
		return numero;
	}



	public void setNumero(Long numero) {
		this.numero = numero;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public BigDecimal getPagocheque() {
		return pagocheque;
	}



	public void setPagocheque( BigDecimal pagocheque) {
		this.pagocheque = pagocheque;
	}



	public String getEmisor() {
		return emisor;
	}



	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}



	public String getCuitemisor() {
		return cuitemisor;
	}



	public void setCuitemisor(String cuitemisor) {
		this.cuitemisor = cuitemisor;
	}



	public String getPaguesea() {
		return paguesea;
	}



	public void setPaguesea(String paguesea) {
		this.paguesea = paguesea;
	}



	public Date getFechaemision() {
		return fechaemision;
	}



	public void setFechaemision(Date fechaemision) {
		this.fechaemision = fechaemision;
	}



	public Date getFechapago() {
		return fechapago;
	}



	public void setFechapago(Date fechapago) {
		this.fechapago = fechapago;
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



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}
	

	

}
