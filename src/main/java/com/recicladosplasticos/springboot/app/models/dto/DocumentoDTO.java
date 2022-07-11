

package com.recicladosplasticos.springboot.app.models.dto;

import java.util.Date;

import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;

public interface DocumentoDTO {
	
	public Long getNumeromin();
	public Long getNumeromax();
	public Date getFechainicio();
	public Date getFechafin();
	public String getCodigo();
	public String getNombre();
	public String getRazonsocial();
	public Double getImportemin();
	public Double getImportemax();
	public String getPrefijo();
	public PuntoDeVenta getpVenta();
}
