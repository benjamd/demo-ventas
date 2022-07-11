package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;

public interface PuntoDeVentaService {

	public List<PuntoDeVenta> findAll();

	public Page<PuntoDeVenta> findAll(Pageable pageable);

	public void save(PuntoDeVenta puntodeventa);

	public PuntoDeVenta findOne(Long id);

	public void delete(Long id);

	public List<PuntoDeVenta> findByDocumento(String term);
	
	public PuntoDeVenta findPuntoDeVentaByDocumentoAndLetraAndPrefijo(String documento, String letra, String prefijo);

}
