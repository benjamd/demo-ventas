package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.dto.VentasRemitoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasRemito;

public interface VentasRemitoService {

 	public List<VentasRemito> findAll();

	public Page<VentasRemito> findAll(Pageable pageable);

	public void save(VentasRemito remito);

	public VentasRemito findOne(Long id);

	public void delete(Long id);
	
	public VentasRemito findRemitoByPrefijoAndNumero(String prefijo, Long numero );
	
	public  Page<VentasRemito> buscarVentasRemitos(VentasRemitoBusquedaDTO documento, Pageable pageable);
	
	public  List<VentasRemito> buscarVentasRemitos(VentasRemitoBusquedaDTO documento);
	
	
}
