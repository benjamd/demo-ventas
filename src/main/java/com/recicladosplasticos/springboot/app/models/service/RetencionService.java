package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.Retencion;

public interface RetencionService {
	
	public List<Retencion> findAll();

	public Page<Retencion> findAll(Pageable pageable);

	public void save(Retencion retencion);

	public Retencion findOne(Long id);

	public void delete(Long id);	
	

}
