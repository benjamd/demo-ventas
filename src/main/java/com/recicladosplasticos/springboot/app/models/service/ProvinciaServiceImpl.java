package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.ProvinciaDAO;
import com.recicladosplasticos.springboot.app.models.entity.Provincia;

@Service
public class ProvinciaServiceImpl implements ProvinciaService{

	ProvinciaDAO provinciaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Provincia> findAll() {
		 
		return (List<Provincia>) provinciaDao.findAll() ;
	}

	@Override
	@Transactional
	public void save(Provincia provincia) {
		
		provinciaDao.save(provincia);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Provincia findOne(Long id) {
		
		return provinciaDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		provinciaDao.deleteById(id);
		
	}

}
