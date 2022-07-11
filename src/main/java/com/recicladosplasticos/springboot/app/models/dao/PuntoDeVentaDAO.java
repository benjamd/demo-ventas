package com.recicladosplasticos.springboot.app.models.dao;

 
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;


public interface PuntoDeVentaDAO extends PagingAndSortingRepository<PuntoDeVenta, Long>{

	
	@Query("select p from PuntoDeVenta p where p.documento = ?1")
	public List<PuntoDeVenta> findByDocumento(String term);

	public List<PuntoDeVenta> findByDocumentoLikeIgnoreCase(String term);
	
	@Query("select p from PuntoDeVenta p where p.documento = ?1 AND p.tipo = ?2 AND p.prefijo = ?3")
	public PuntoDeVenta findPuntoDeVentaByDocumentoAndLetraAndPrefijo(String documento, String letra, String prefijo);
}
