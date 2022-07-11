package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.PaisDAO;
import com.recicladosplasticos.springboot.app.models.entity.Pais;

@Service
public class PaisServiceImpl implements PaisService {

	PaisDAO paisDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Pais> findAll() {
		 
		return (List<Pais>) paisDao.findAll();
	}

	@Override
	@Transactional
	public void save(Pais pais) {
		 
		paisDao.save(pais);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Pais findOne(Long id) {
		 
		return paisDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		 
		paisDao.deleteById(id);
		
	}

}
