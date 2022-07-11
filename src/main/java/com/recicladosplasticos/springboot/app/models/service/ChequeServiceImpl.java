package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.ChequeDAO;
import com.recicladosplasticos.springboot.app.models.entity.Cheque;

@Service
public class ChequeServiceImpl implements ChequeService{

	@Autowired
	private ChequeDAO chequeDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cheque> findAll() {
		 
		return (List<Cheque>) chequeDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cheque> findAll(Pageable pageable) {
		
		return chequeDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Cheque cheque) {
		 
		chequeDao.save(cheque);
	}

	@Override
	@Transactional(readOnly = true)
	public Cheque findOne(Long id) {
		 
		return chequeDao.findById(id).orElse(null);
	}

	@Override
	@Transactional 
	public void delete(Long id) {
		 
		chequeDao.deleteById(id);
		
	}

}
