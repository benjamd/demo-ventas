package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.BuscarDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasRemitoDAO;
import com.recicladosplasticos.springboot.app.models.dto.VentasRemitoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasRemito;

@Service
public class VentasRemitoServiceImpl implements VentasRemitoService {

	@Autowired
	private VentasRemitoDAO remitoVentasDao;
	
	@Autowired
	private BuscarDAO buscarDAO;

	@Override
	@Transactional(readOnly = true)
	public List<VentasRemito> findAll() {
		 
		return (List<VentasRemito>) remitoVentasDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasRemito> findAll(Pageable pageable) {
		 
		return remitoVentasDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(VentasRemito remito) {
		 
		remitoVentasDao.save(remito);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasRemito findOne(Long id) {
		 
		return remitoVentasDao.findById(id).orElse(null);
	}

	@Override
	@Transactional 
	public void delete(Long id) {
		 
		remitoVentasDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasRemito findRemitoByPrefijoAndNumero(String prefijo, Long numero) {
	 
		return remitoVentasDao.findRemitoByPrefijoAndNumero(prefijo, numero);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasRemito> buscarVentasRemitos(VentasRemitoBusquedaDTO documento, Pageable pageable) {
		 
		return buscarDAO.buscarVentasRemitos(documento, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRemito> buscarVentasRemitos(VentasRemitoBusquedaDTO documento) {
		 
		return buscarDAO.buscarVentasRemitos(documento);
	}

}
