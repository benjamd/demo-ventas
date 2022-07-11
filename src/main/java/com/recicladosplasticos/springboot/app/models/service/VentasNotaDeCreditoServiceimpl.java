package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.BuscarDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasNotaDeCreditoDAO;
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeCreditoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;

@Service
public class VentasNotaDeCreditoServiceimpl implements VentasNotaDeCreditoService {

	@Autowired
	private VentasNotaDeCreditoDAO  ventasNotaDeCreditoDAO;
	
	@Autowired
	private BuscarDAO buscarDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findAll() {
		 
		return  (List<VentasNotaDeCredito>) ventasNotaDeCreditoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeCredito> findAll(Pageable pageable) {
		 
		return ventasNotaDeCreditoDAO.findAll(pageable);
	}

	@Override
	@Transactional 
	public void save(VentasNotaDeCredito ventasNotaDeCredito) {
		 
		ventasNotaDeCreditoDAO.save(ventasNotaDeCredito);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasNotaDeCredito findOne(Long id) {
		 
		return ventasNotaDeCreditoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		ventasNotaDeCreditoDAO.deleteById(id);
		
	}

 
	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeCredito> findCreditosEntreFechas(Date fechainicio, Date fechafin, Pageable pageable) {
		 
		return ventasNotaDeCreditoDAO.findCreditosEntreFechas(fechainicio, fechafin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findCreditosEntreFechas(Date fechainicio, Date fechafin) {
		 
		return ventasNotaDeCreditoDAO.findCreditosEntreFechas(fechainicio, fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findCreditosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin,
			String codigo, String nombre, String razonsocial) {
		 
		return ventasNotaDeCreditoDAO.findCreditosList(numeromin, numeromax, fechainicio, fechafin, codigo, nombre, razonsocial);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeCredito> findCreditosDesde(Date fechainicio, Pageable pageable) {
		 
		return ventasNotaDeCreditoDAO.findCreditosDesde(fechainicio, pageable); 
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findCreditosDesde(Date fechainicio) {
		 
		return ventasNotaDeCreditoDAO.findCreditosDesde(fechainicio);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeCredito> findCreditosHasta(Date fechafin, Pageable pageable) {
		 
		return ventasNotaDeCreditoDAO.findCreditosHasta(fechafin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findCreditosHasta(Date fechafin) {
		 
		return ventasNotaDeCreditoDAO.findCreditosHasta(fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> fetchCreditoPendienteByIdWithCliente(Long id) {
		
		return ventasNotaDeCreditoDAO.fetchCreditoPendienteByIdWithCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> fetchCreditoByIdWithCliente(Long id) {
		 
		return ventasNotaDeCreditoDAO.fetchCreditoByIdWithCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public VentasNotaDeCredito findCreditoByPrefijoAndNumero(String prefijo, Long numero) {
		 
		return ventasNotaDeCreditoDAO.findCreditoByPrefijoAndNumero(prefijo, numero) ;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findCreditosProductos() {
		
		return ventasNotaDeCreditoDAO.findCreditosProductos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findCreditosProductosEntreFechas(Date fechainicio, Date fechafin) {
		 
		return ventasNotaDeCreditoDAO.findCreditosProductosEntreFechas(fechainicio, fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findCreditosProductosDesde(Date fechainicio) {
		 
		return ventasNotaDeCreditoDAO.findCreditosProductosDesde(fechainicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> findCreditosProductosHasta(Date fechafin) {
		 
		return ventasNotaDeCreditoDAO.findCreditosProductosHasta(fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasNotaDeCredito> buscarNotasDeCredito(VentasNotaDeCreditoBusquedaDTO credito, Pageable pageable) {
		 
		return buscarDAO.buscarVentasNotasDeCredito(credito, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasNotaDeCredito> buscarNotasDeCredito(VentasNotaDeCreditoBusquedaDTO credito) {
		 
		return buscarDAO.buscarVentasNotasDeCredito(credito);
	}

 
}
