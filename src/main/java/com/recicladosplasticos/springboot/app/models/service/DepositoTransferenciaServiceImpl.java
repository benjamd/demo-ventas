package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.DepositoTransferenciaDAO;
import com.recicladosplasticos.springboot.app.models.entity.DepositoTransferencia;

@Service
public class DepositoTransferenciaServiceImpl implements DepositoTransferenciaService {

	@Autowired
	private DepositoTransferenciaDAO depotransferDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<DepositoTransferencia> findAll() {
		 
		return (List<DepositoTransferencia>) depotransferDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DepositoTransferencia> findAll(Pageable pageable) {
		 
		return depotransferDao.findAll(pageable);
	}

	@Override
	@Transactional 
	public void save(DepositoTransferencia depotransfer) {
		 
		depotransferDao.save(depotransfer);
		
	}

	@Override
	@Transactional(readOnly = true)
	public DepositoTransferencia findOne(Long id) {
		 
		return depotransferDao.findById(id).orElse(null);
	}

	@Override
	@Transactional 
	public void delete(Long id) {
		
		depotransferDao.deleteById(id);
		
	}

}
