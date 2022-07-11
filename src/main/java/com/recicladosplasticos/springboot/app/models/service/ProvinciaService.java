package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import com.recicladosplasticos.springboot.app.models.entity.Provincia;

public interface ProvinciaService {

	public List<Provincia> findAll();

	public void save(Provincia provincia);

	public Provincia findOne(Long id);

	public void delete(Long id);
}
