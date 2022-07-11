package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.BuscarDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasDevolucionDAO;
import com.recicladosplasticos.springboot.app.models.dto.VentasDevolucionBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasDevolucion;

@Service
public class VentasDevolucionServiceImpl implements VentasDevolucionService {

	@Autowired
	private VentasDevolucionDAO devolucionDao;
	
	@Autowired
	private BuscarDAO buscarDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<VentasDevolucion> findAll() {
		 
		return (List<VentasDevolucion>) devolucionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasDevolucion> findAll(Pageable pageable) {
		 
		return devolucionDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(VentasDevolucion devolucion) {
		
		devolucionDao.save(devolucion);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasDevolucion findOne(Long id) {
		 
		return devolucionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		devolucionDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasDevolucion findDevolucionByPrefijoAndNumero(String prefijo, Long numero) {
		 
		return devolucionDao.findDevolucionByPrefijoAndNumero(prefijo, numero);
	}

	@Override
	public Page<VentasDevolucion> buscarVentasDevoluciones(VentasDevolucionBusquedaDTO devolucion, Pageable pageable) {
		 
		return buscarDAO.buscarVentasDevoluciones(devolucion, pageable);
	}

	@Override
	public List<VentasDevolucion> buscarVentasDevoluciones(VentasDevolucionBusquedaDTO devolucion) {
		 
		return buscarDAO.buscarVentasDevoluciones(devolucion);
	}

}
