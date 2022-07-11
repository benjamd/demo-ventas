package com.recicladosplasticos.springboot.app.models.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;

public class VentasRemitoBusquedaDTO implements DocumentoDTO{
	
	@Nullable
	private  Long numeromin;
	
	 
	@Nullable
	private  Long numeromax;
	
	 
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private  Date fechainicio; 
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private Date fechafin;
	
	private String codigo;
	
	private String nombre;
	
	private String razonsocial;
	
	private Double importemin;
	
	private Double importemax;
 
	private String prefijo;

	private PuntoDeVenta pVenta;

	public VentasRemitoBusquedaDTO() {
		 
	}

	public Long getNumeromin() {
		return numeromin;
	}

	public void setNumeromin(Long numeromin) {
		this.numeromin = numeromin;
	}

	public Long getNumeromax() {
		return numeromax;
	}

	public void setNumeromax(Long numeromax) {
		this.numeromax = numeromax;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonsocial() {
		return razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}

	public Double getImportemin() {
		return importemin;
	}

	public void setImportemin(Double importemin) {
		this.importemin = importemin;
	}

	public Double getImportemax() {
		return importemax;
	}

	public void setImportemax(Double importemax) {
		this.importemax = importemax;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public PuntoDeVenta getpVenta() {
		return pVenta;
	}

	public void setpVenta(PuntoDeVenta pVenta) {
		this.pVenta = pVenta;
	}
	
	
}
