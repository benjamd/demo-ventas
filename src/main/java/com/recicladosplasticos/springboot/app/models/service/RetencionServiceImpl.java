package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.RetencionDAO;
import com.recicladosplasticos.springboot.app.models.entity.Retencion;

@Service
public class RetencionServiceImpl implements RetencionService {

	@Autowired
	private RetencionDAO retencionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Retencion> findAll() {
		 
		return  (List<Retencion>) retencionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Retencion> findAll(Pageable pageable) {
		 
		return retencionDao.findAll(pageable);
	}

	@Override
	@Transactional 
	public void save(Retencion retencion) {
		 
		retencionDao.save(retencion);
	}

	@Override
	@Transactional(readOnly = true)
	public Retencion findOne(Long id) {
		 
		return retencionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		 
		retencionDao.deleteById(id);
		
	}

}
