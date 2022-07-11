package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.BancoDAO;
import com.recicladosplasticos.springboot.app.models.entity.Banco;

@Service
public class BancoServiceImpl implements BancoService {

	@Autowired
	private BancoDAO bancoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Banco> findAll() {
		 
		return (List<Banco>) bancoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Banco> findAll(Pageable pageable) {
		 
		return bancoDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Banco banco) {
		 
		bancoDao.save(banco);
	}

	@Override
	@Transactional(readOnly = true)
	public Banco findOne(Long id) {
		 
		return bancoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		 
		bancoDao.deleteById(id);
		
	}

}
