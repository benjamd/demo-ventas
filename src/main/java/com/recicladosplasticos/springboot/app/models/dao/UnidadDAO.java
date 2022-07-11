package com.recicladosplasticos.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.Unidad;

public interface UnidadDAO extends PagingAndSortingRepository<Unidad, Long>{
	

}
