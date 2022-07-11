package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.BuscarDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasPresupuestoDAO;
import com.recicladosplasticos.springboot.app.models.dto.VentasPresupuestoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasPresupuesto;

@Service
public class VentasPresupuestoServiceImpl implements VentasPresupuestoService {

	@Autowired
	private VentasPresupuestoDAO ventasPresupuestoDAO;
	
	@Autowired
	private BuscarDAO buscarDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findAll() {
		 
		return (List<VentasPresupuesto>) ventasPresupuestoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasPresupuesto> findAll(Pageable pageable) {
		 
		return  ventasPresupuestoDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(VentasPresupuesto ordenDeEntrega) {
		ventasPresupuestoDAO.save(ordenDeEntrega);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasPresupuesto findOne(Long id) {
		 
		return ventasPresupuestoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
	 	ventasPresupuestoDAO.deleteById(id);
		
	}

 

	@Override
	@Transactional(readOnly = true)
	public Page<VentasPresupuesto> findOrdenDeEntregaEntreFechas(Date fechainicio, Date fechafin, Pageable pageable) {
		 
		return ventasPresupuestoDAO.findOrdenDeEntregaEntreFechas(fechainicio, fechafin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findOrdenDeEntregaEntreFechas(Date fechainicio, Date fechafin) {
		 
		return ventasPresupuestoDAO.findOrdenDeEntregaEntreFechas(fechainicio, fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findOrdenDeEntregaList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin,
			String codigo, String nombre, String razonsocial) {
		 
		return ventasPresupuestoDAO.findOrdenDeEntregaList(numeromin, numeromax, fechainicio, fechafin, codigo, nombre, razonsocial);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasPresupuesto> findOrdenDeEntregaDesde(Date fechainicio, Pageable pageable) {
		 
		return ventasPresupuestoDAO.findOrdenDeEntregaDesde(fechainicio, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findOrdenDeEntregaDesde(Date fechainicio) {
		 
		return ventasPresupuestoDAO.findOrdenDeEntregaDesde(fechainicio);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasPresupuesto> findOrdenDeEntregaHasta(Date fechafin, Pageable pageable) {
		 
		return ventasPresupuestoDAO.findOrdenDeEntregaHasta(fechafin, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findOrdenDeEntregaHasta(Date fechafin) {
		 
		return ventasPresupuestoDAO.findOrdenDeEntregaHasta(fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> fetchOrdenDeEntregaPendienteByIdWithCliente(Long id) {
		 
		return ventasPresupuestoDAO.fetchOrdenDeEntregaPendienteByIdWithCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> fetchOrdenDeEntregaByIdWithCliente(Long id) {
		 
		return ventasPresupuestoDAO.fetchOrdenDeEntregaByIdWithCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public VentasPresupuesto findOrdenDeEntregaByPrefijoAndNumero(String prefijo, Long numero) {
		 
		return ventasPresupuestoDAO.findOrdenDeEntregaByPrefijoAndNumero(prefijo, numero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findOrdenesDeEntregaProductos() {
		 
		return ventasPresupuestoDAO.findOrdenesDeEntregaProductos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findOrdenesDeEntregaProductosEntreFechas(Date fechainicio, Date fechafin) {
		 
		return ventasPresupuestoDAO.findOrdenesDeEntregaProductosEntreFechas(fechainicio, fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findOrdenesDeEntregaProductosDesde(Date fechainicio) {
		 
		return ventasPresupuestoDAO.findOrdenesDeEntregaProductosDesde(fechainicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> findOrdenesDeEntregaProductosHasta(Date fechafin) {
		
		return ventasPresupuestoDAO.findOrdenesDeEntregaProductosHasta(fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VentasPresupuesto> buscarVentasPresupuestos(VentasPresupuestoBusquedaDTO presupuesto, Pageable pageable) {
		 
		return buscarDAO.buscarVentasPresupuestos(presupuesto, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasPresupuesto> buscarVentasPresupuestos(VentasPresupuestoBusquedaDTO presupuesto) {
		 
		return buscarDAO.buscarVentasPresupuestos(presupuesto);
	}

}
