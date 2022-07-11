package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.BuscarDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasNotaDeDebitoDAO;
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeDebitoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;

@Service
public class VentasNotaDeDebitoServiceImpl implements VentasNotaDeDebitoService {

	@Autowired
	private VentasNotaDeDebitoDAO debitoDao;
	
	@Autowired
	private BuscarDAO buscarDAO;

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findAll() {
		 
		return (List<VentasNotaDeDebito>) debitoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeDebito> findAll(Pageable pageable) {
		 
		return  debitoDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(VentasNotaDeDebito notaDeCredito) {
		 	
		debitoDao.save(notaDeCredito);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasNotaDeDebito findOne(Long id) {
		 
		return debitoDao.findById(id).orElse(null);
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		 
		debitoDao.deleteById(id);
		
	}

 	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeDebito> findDebitosEntreFechas(Date fechainicio, Date fechafin, Pageable pageable) {
	 
		return debitoDao.findDebitosEntreFechas(fechainicio, fechafin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findDebitosEntreFechas(Date fechainicio, Date fechafin) {
		 
		return  debitoDao.findDebitosEntreFechas(fechainicio, fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findDebitosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin,
			String codigo, String nombre, String razonsocial) {
	 
		return  debitoDao.findDebitosList(numeromin, numeromax, fechainicio, fechafin, codigo, nombre, razonsocial);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeDebito> findDebitosDesde(Date fechainicio, Pageable pageable) {
		 
		return  debitoDao.findDebitosDesde(fechainicio, pageable); 
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findDebitosDesde(Date fechainicio) {
		 
		return  debitoDao.findDebitosDesde(fechainicio);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeDebito> findDebitosHasta(Date fechafin, Pageable pageable) {
	 
		return debitoDao.findDebitosHasta(fechafin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findDebitosHasta(Date fechafin) {
		 
		return debitoDao.findDebitosHasta(fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> fetchDebitoPendienteByIdWithCliente(Long id) {
		 
		return debitoDao.fetchDebitoPendienteByIdWithCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> fetchDebitoByIdWithCliente(Long id) {
		 
		return debitoDao.fetchDebitoByIdWithCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public VentasNotaDeDebito findDebitoByPrefijoAndNumero(String prefijo, Long numero) {
		 
		return debitoDao.findDebitoByPrefijoAndNumero(prefijo, numero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findDebitosProductos() {
		 
		return debitoDao.findDebitosProductos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findDebitosProductosEntreFechas(Date fechainicio, Date fechafin) {
	 
		return debitoDao.findDebitosProductosEntreFechas(fechainicio, fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findDebitosProductosDesde(Date fechainicio) {
		 
		return debitoDao.findDebitosProductosDesde(fechainicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> findDebitosProductosHasta(Date fechafin) {
		 
		return debitoDao.findDebitosProductosHasta(fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeDebito> buscarVentasNotasDeDebito(VentasNotaDeDebitoBusquedaDTO debito, Pageable pageable) {
		 
		return buscarDAO.buscarVentasNotasDeDebito(debito, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeDebito> buscarVentasNotasDeDebito(VentasNotaDeDebitoBusquedaDTO debito) {
		return buscarDAO.buscarVentasNotasDeDebito(debito);
	}

	
	
}

 
