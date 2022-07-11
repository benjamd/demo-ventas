package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.ClienteCuentaCorrienteDAO;
import com.recicladosplasticos.springboot.app.models.entity.ClienteCuentaCorriente;

public class ClienteCuentaCorrienteServiceImpl implements ClienteCuentaCorrienteService {

	@Autowired
	private ClienteCuentaCorrienteDAO clienteCuentaCorrienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ClienteCuentaCorriente> findAll() {
		 
		return (List<ClienteCuentaCorriente>) clienteCuentaCorrienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClienteCuentaCorriente> findAll(Pageable pageable) {
		 
		return clienteCuentaCorrienteDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(ClienteCuentaCorriente clienteCuentaCorriente) {
		 
		clienteCuentaCorrienteDao.save(clienteCuentaCorriente);
		
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteCuentaCorriente findOne(Long id) {
		 
		return clienteCuentaCorrienteDao.findById(id).orElse(null);
	}
	

	@Override
	@Transactional
	public void delete(Long id) {
		
		clienteCuentaCorrienteDao.deleteById(id);
		
		
	}

}
