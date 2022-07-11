package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.UnidadDAO;
import com.recicladosplasticos.springboot.app.models.entity.Unidad;

@Service
public class UnidadServiceImpl implements UnidadService {
	
	@Autowired
	private UnidadDAO unidadDao;

	@Override
	@Transactional(readOnly = true)
	public List<Unidad> findAll() {
		 
		return (List<Unidad>) unidadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Unidad> findAll(Pageable pageable) {
		 
		return unidadDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Unidad unidad) {
		
		unidadDao.save(unidad);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Unidad findOne(Long id) {
	
		return unidadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		unidadDao.deleteById(id);
		
	}

}
