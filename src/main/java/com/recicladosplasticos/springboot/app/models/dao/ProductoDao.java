package com.recicladosplasticos.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.Producto;

public interface ProductoDao extends PagingAndSortingRepository<Producto,Long> {

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	@Query("select p from Producto p where p.nombre like %?1%")
	public Page<Producto> findByNombre(String term, Pageable pageable);

	public List<Producto> findByNombreLikeIgnoreCase(String nombre);
	
	@Query("select p from Producto p where p.nombre like %?1% AND p.descripcion like %?2% order by p.nombre")
	public Page<Producto> findByNombreLikeIgnoreCase(String nombre, String descripcion, Pageable pageable);
}
 
