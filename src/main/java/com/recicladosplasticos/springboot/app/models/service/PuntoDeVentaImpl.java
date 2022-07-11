package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.recicladosplasticos.springboot.app.models.dao.PuntoDeVentaDAO;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;

@Service
public class PuntoDeVentaImpl implements PuntoDeVentaService {

	@Autowired
	private PuntoDeVentaDAO puntodeventaDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<PuntoDeVenta> findAll() {
		
		return   (List<PuntoDeVenta>) puntodeventaDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PuntoDeVenta> findAll(Pageable pageable) {
		
		return puntodeventaDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(PuntoDeVenta puntodeventa) {
		 	puntodeventaDAO.save(puntodeventa);
		
	}

	@Override
	@Transactional(readOnly = true)
	public PuntoDeVenta findOne(Long id) {
		
		return puntodeventaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		 puntodeventaDAO.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<PuntoDeVenta> findByDocumento(String term) {
		 
		return puntodeventaDAO.findByDocumentoLikeIgnoreCase("%" + term + "%"); 
	}

	@Override
	@Transactional(readOnly = true)
	public PuntoDeVenta findPuntoDeVentaByDocumentoAndLetraAndPrefijo(String documento, String letra,
			String prefijo) {
	 
		return puntodeventaDAO.findPuntoDeVentaByDocumentoAndLetraAndPrefijo(documento, letra, prefijo);
	}

	
	
}
