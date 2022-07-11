package com.recicladosplasticos.springboot.app.models.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class VentasReporteDTO {
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private  Date fechainicio; 
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private Date fechafin;
 
	private String documento;

	public VentasReporteDTO() {
	 
	}


	public Date getFechainicio() {
		return fechainicio;
	}


	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}


	public Date getFechafin() {
		return fechafin;
	}


	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}


	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	

}
