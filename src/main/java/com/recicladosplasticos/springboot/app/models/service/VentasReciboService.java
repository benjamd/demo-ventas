package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.dto.VentasReciboBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;

public interface VentasReciboService {
	
	
 	public List<VentasRecibo> findAll();

	public Page<VentasRecibo> findAll(Pageable pageable);

	public void save(VentasRecibo recibo);

	public VentasRecibo findOne(Long id);

	public void delete(Long id);
	
	public Page<VentasRecibo> buscarVentasRecibos(VentasReciboBusquedaDTO recibo, Pageable pageable);
	
	public List<VentasRecibo> buscarVentasRecibos(VentasReciboBusquedaDTO recibo);
	
	public VentasRecibo findReciboByPrefijoAndNumero(String prefijo, Long numero );
	
	public Page<VentasRecibo> findRecibosEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	public List<VentasRecibo> findRecibosEntreFechas( Date fechainicio, Date fechafin);
	
	public  List<VentasRecibo> findRecibosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
	public Page<VentasRecibo> findRecibosDesde( Date fechainicio, Pageable pageable);
	 
	public List<VentasRecibo>  findRecibosDesde( Date fechainicio);
	 
	public Page<VentasRecibo> findRecibosHasta( Date fechafin, Pageable pageable);
	 
	public List<VentasRecibo>  findRecibosHasta( Date fechafin);
	 
	public List<VentasRecibo> fetchReciboPendienteByIdWithCliente(Long id);
 
	public List<VentasRecibo> fetchReciboByIdWithCliente(Long id);
	
	public List<VentasRecibo> fetchReciboByIdWithClienteAndPrefijo(Long id, String prefijo);
	
	public List<VentasRecibo> fetchReciboByIdWithClienteAndPrefijoDistinto(Long id, String prefijo);
	
	public List<VentasRecibo> fetchReciboPendienteByIdWithClienteAndPrefijo(Long id, String prefijo);
	
	public List<VentasRecibo> fetchReciboPendienteByIdWithClienteAndPrefijoDistinto(Long id, String prefijo);
	
	

}
