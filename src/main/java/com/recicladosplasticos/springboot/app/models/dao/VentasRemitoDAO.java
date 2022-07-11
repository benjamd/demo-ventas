package com.recicladosplasticos.springboot.app.models.dao;

 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.VentasRemito;
 

public interface VentasRemitoDAO extends PagingAndSortingRepository<VentasRemito, Long> {
	
	@Query("SELECT g FROM VentasRemito g  WHERE  g.prefijo = ?1 AND g.numero = ?2")
	public VentasRemito findRemitoByPrefijoAndNumero(String prefijo, Long numero );
 
}
