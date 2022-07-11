package com.recicladosplasticos.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.VentasDevolucion;
 

public interface VentasDevolucionDAO extends PagingAndSortingRepository<VentasDevolucion, Long>{

	@Query("SELECT g FROM VentasDevolucion g  WHERE  g.prefijo = ?1 AND g.numero = ?2")
	public VentasDevolucion findDevolucionByPrefijoAndNumero(String prefijo, Long numero );
}
