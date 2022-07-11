package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.Unidad;

public interface UnidadService {
	
	public List<Unidad> findAll();

	public Page<Unidad> findAll(Pageable pageable);

	public void save(Unidad unidad);

	public Unidad findOne(Long id);

	public void delete(Long id);

}
