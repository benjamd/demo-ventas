package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.TipoDocumentoDAO;
import com.recicladosplasticos.springboot.app.models.entity.TipoDocumento;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	private TipoDocumentoDAO tipodocumentoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoDocumento> findAll() {
		 
		return (List<TipoDocumento>) tipodocumentoDao.findAll() ;
	}

	@Override
	@Transactional
	public void save(TipoDocumento tipodocumento) {
		 
		tipodocumentoDao.save(tipodocumento);
		
	}

	@Override
	@Transactional(readOnly = true)
	public TipoDocumento findOne(Long id) {
		
		return tipodocumentoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
			
		tipodocumentoDao.deleteById(id);
		
	}

}
