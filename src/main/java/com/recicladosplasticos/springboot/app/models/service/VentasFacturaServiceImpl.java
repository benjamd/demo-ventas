package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.BuscarDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasFacturaDAO;
import com.recicladosplasticos.springboot.app.models.dto.VentasFacturaBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;

@Service
public class VentasFacturaServiceImpl implements VentasFacturaService {

	@Autowired
	private VentasFacturaDAO ventasFacturaDAO;
	
	@Autowired
	private BuscarDAO buscarDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findAll() {
		
		return (List<VentasFactura>) ventasFacturaDAO.findAll(); 
	}
	
 
	

	@Override
	@Transactional(readOnly = true)
	public Page<VentasFactura> findAll(Pageable pageable) {
		 
		return ventasFacturaDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(VentasFactura ventasFactura) {
		ventasFacturaDAO.save(ventasFactura);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasFactura findOne(Long id) {
		 
		return ventasFacturaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		ventasFacturaDAO.deleteById(id);
		
	}




	@Override
	@Transactional(readOnly = true)
	public Page<VentasFactura> buscarVentasFacturas(VentasFacturaBusquedaDTO factura, Pageable pageable) {
		 
		return buscarDao.buscarVentasFacturas(factura, pageable);
	}




	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findFacturasList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin,
			String codigo, String nombre, String razonsocial) {
		 
		return ventasFacturaDAO.findFacturasList(numeromin, numeromax, fechainicio, fechafin, codigo, nombre, razonsocial);
	}




	@Override
	@Transactional(readOnly = true)
	public Page<VentasFactura> findFacturasEntreFechas(Date fechainicio, Date fechafin, Pageable pageable) {
		 
		return  ventasFacturaDAO.findFacturasEntreFechas(fechainicio, fechafin, pageable);
	}




	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findFacturasEntreFechas(Date fechainicio, Date fechafin) {
		 
		return ventasFacturaDAO.findFacturasEntreFechas(fechainicio, fechafin);
	}




	@Override
	@Transactional(readOnly = true)
	public Page<VentasFactura> findFacturasDesde(Date fechainicio, Pageable pageable) {
		 
		return ventasFacturaDAO.findFacturasDesde(fechainicio, pageable);
	}




	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findFacturasDesde(Date fechainicio) {
		 
		return  ventasFacturaDAO.findFacturasDesde(fechainicio);
	}




	@Override
	@Transactional(readOnly = true)
	public Page<VentasFactura> findFacturasHasta(Date fechafin, Pageable pageable) {
		 
		return ventasFacturaDAO.findFacturasHasta(fechafin, pageable);
	}




	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findFacturasHasta(Date fechafin) {
		 
		return ventasFacturaDAO.findFacturasHasta(fechafin);
	}




	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> fetchFacturaByIdWithCliente(Long id) {
		 
		return ventasFacturaDAO.fetchFacturaByIdWithCliente(id);
	}




	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> fetchFacturaPendienteByIdWithCliente(Long id) {
		 
		return  ventasFacturaDAO.fetchFacturaPendienteByIdWithCliente(id);
	}




	@Override
	@Transactional(readOnly = true)
	public VentasFactura findFacturaByPrefijoAndNumero(String prefijo, Long numero) {
		return ventasFacturaDAO.findFacturaByPrefijoAndNumero(prefijo, numero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findFacturasProductosEntreFechas(Date fechainicio, Date fechafin) {
		return ventasFacturaDAO.findFacturasProductosEntreFechas(fechainicio, fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findFacturasProductosDesde(Date fechainicio) {
		return ventasFacturaDAO.findFacturasProductosDesde(fechainicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findFacturasProductosHasta(Date fechafin) {
		return ventasFacturaDAO.findFacturasProductosHasta(fechafin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> findFacturasProductos() {
		return ventasFacturaDAO.findFacturasProductos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VentasFactura> buscarVentasFacturas(VentasFacturaBusquedaDTO factura) {
		return buscarDao.buscarVentasFacturas(factura);
	}



 
 

 
}
