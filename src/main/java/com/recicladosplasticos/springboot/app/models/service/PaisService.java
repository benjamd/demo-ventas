package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import com.recicladosplasticos.springboot.app.models.entity.Pais;

 
 

public interface PaisService {

	public List<Pais> findAll();

	public void save(Pais pais);

	public Pais findOne(Long id);

	public void delete(Long id);
	 
}
