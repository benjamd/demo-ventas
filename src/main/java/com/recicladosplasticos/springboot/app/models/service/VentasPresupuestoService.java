package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.dto.VentasPresupuestoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasPresupuesto;

public interface VentasPresupuestoService {

 	public List<VentasPresupuesto> findAll();

	public Page<VentasPresupuesto> findAll(Pageable pageable);

	public void save(VentasPresupuesto ordenDeEntrega);

	public VentasPresupuesto findOne(Long id);

	public void delete(Long id);
 
	public Page<VentasPresupuesto> buscarVentasPresupuestos(VentasPresupuestoBusquedaDTO presupuesto, Pageable pageable);
	
	public List<VentasPresupuesto> buscarVentasPresupuestos(VentasPresupuestoBusquedaDTO presupuesto);

	public VentasPresupuesto findOrdenDeEntregaByPrefijoAndNumero(String prefijo, Long numero );
	 
	public Page<VentasPresupuesto> findOrdenDeEntregaEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	 
	public List<VentasPresupuesto> findOrdenDeEntregaEntreFechas( Date fechainicio, Date fechafin);
	 
	public List<VentasPresupuesto> findOrdenDeEntregaList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
	public Page<VentasPresupuesto> findOrdenDeEntregaDesde( Date fechainicio, Pageable pageable);
	 
	public List<VentasPresupuesto>  findOrdenDeEntregaDesde( Date fechainicio);
	
	public Page<VentasPresupuesto> findOrdenDeEntregaHasta( Date fechafin, Pageable pageable);
		 
	public List<VentasPresupuesto>  findOrdenDeEntregaHasta( Date fechafin);
	
	public List<VentasPresupuesto> fetchOrdenDeEntregaPendienteByIdWithCliente(Long id);
	
	public List<VentasPresupuesto> fetchOrdenDeEntregaByIdWithCliente(Long id);
	
	public List<VentasPresupuesto>  findOrdenesDeEntregaProductos();

	public List<VentasPresupuesto> findOrdenesDeEntregaProductosEntreFechas( Date fechainicio, Date fechafin);

	public List<VentasPresupuesto>  findOrdenesDeEntregaProductosDesde( Date fechainicio);

	public List<VentasPresupuesto>  findOrdenesDeEntregaProductosHasta( Date fechafin);
}
