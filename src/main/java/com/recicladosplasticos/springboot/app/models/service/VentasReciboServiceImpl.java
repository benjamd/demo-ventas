package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.BuscarDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasReciboDAO;
import com.recicladosplasticos.springboot.app.models.dto.VentasReciboBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;

@Service
public class VentasReciboServiceImpl implements VentasReciboService {

	@Autowired
	private VentasReciboDAO reciboDao;
	
	@Autowired
	private BuscarDAO buscarDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> findAll() {
		 
		return (List<VentasRecibo>) reciboDao.findAll() ;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasRecibo> findAll(Pageable pageable) {
		 
		return reciboDao.findAll(pageable); 
		 
	}

	@Override
	@Transactional 
	public void save(VentasRecibo recibo) {
		 
		reciboDao.save(recibo);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasRecibo findOne(Long id) {
		 
		return reciboDao.findById(id).orElse(null);
	}

	@Override
	@Transactional 
	public void delete(Long id) {
		 
		reciboDao.deleteById(id);
		
	}

 

	@Override
	@Transactional(readOnly = true)
	public Page<VentasRecibo> findRecibosEntreFechas(Date fechainicio, Date fechafin, Pageable pageable) {
	 
		return reciboDao.findRecibosEntreFechas(fechainicio, fechafin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> findRecibosEntreFechas(Date fechainicio, Date fechafin) {
		 
		return reciboDao.findRecibosEntreFechas(fechainicio, fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> findRecibosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin,
			String codigo, String nombre, String razonsocial) {
		 
		return reciboDao.findRecibosList(numeromin, numeromax, fechainicio, fechafin, codigo, nombre, razonsocial);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasRecibo> findRecibosDesde(Date fechainicio, Pageable pageable) {
		 
		return reciboDao.findRecibosDesde(fechainicio, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> findRecibosDesde(Date fechainicio) {
		 
		return reciboDao.findRecibosDesde(fechainicio);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasRecibo> findRecibosHasta(Date fechafin, Pageable pageable) {
		 
		return reciboDao.findRecibosHasta(fechafin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> findRecibosHasta(Date fechafin) {
		 
		return reciboDao.findRecibosHasta(fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> fetchReciboPendienteByIdWithCliente(Long id) {
		 
		return reciboDao.fetchReciboPendienteByIdWithCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> fetchReciboByIdWithCliente(Long id) {
		 
		return reciboDao.fetchReciboByIdWithCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public VentasRecibo findReciboByPrefijoAndNumero(String prefijo, Long numero) {
		 
		return reciboDao.findReciboByPrefijoAndNumero(prefijo, numero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> fetchReciboByIdWithClienteAndPrefijo(Long id, String prefijo) {
		 
		return reciboDao.fetchReciboByIdWithClienteAndPrefijo(id, prefijo);
	 
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> fetchReciboByIdWithClienteAndPrefijoDistinto(Long id, String prefijo) {
		 
		return reciboDao.fetchReciboByIdWithClienteAndPrefijoDistinto(id, prefijo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> fetchReciboPendienteByIdWithClienteAndPrefijo(Long id, String prefijo) {
		 
		return  reciboDao.fetchReciboPendienteByIdWithClienteAndPrefijo(id, prefijo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> fetchReciboPendienteByIdWithClienteAndPrefijoDistinto(Long id, String prefijo) {
		 
		return  reciboDao.fetchReciboPendienteByIdWithClienteAndPrefijoDistinto(id, prefijo);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasRecibo> buscarVentasRecibos(VentasReciboBusquedaDTO recibo, Pageable pageable) {
		return buscarDAO.buscarVentasRecibos(recibo, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasRecibo> buscarVentasRecibos(VentasReciboBusquedaDTO recibo) {
		 
		return buscarDAO.buscarVentasRecibos(recibo);
	}

}
