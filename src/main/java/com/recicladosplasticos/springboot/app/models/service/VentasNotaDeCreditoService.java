package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeCreditoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;

public interface VentasNotaDeCreditoService {

 	public List<VentasNotaDeCredito> findAll();

	public Page<VentasNotaDeCredito> findAll(Pageable pageable);

	public void save(VentasNotaDeCredito ventasNotaDeCredito);

	public VentasNotaDeCredito findOne(Long id);

	public void delete(Long id);
	
	public  Page<VentasNotaDeCredito> buscarNotasDeCredito(VentasNotaDeCreditoBusquedaDTO credito, Pageable pageable);
	
	public  List<VentasNotaDeCredito> buscarNotasDeCredito(VentasNotaDeCreditoBusquedaDTO credito);
	
	public VentasNotaDeCredito findCreditoByPrefijoAndNumero(String prefijo, Long numero );
	
	public Page<VentasNotaDeCredito> findCreditosEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	public List<VentasNotaDeCredito> findCreditosEntreFechas( Date fechainicio, Date fechafin);
	
	public  List<VentasNotaDeCredito> findCreditosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
	public Page<VentasNotaDeCredito> findCreditosDesde( Date fechainicio, Pageable pageable);
	
	public List<VentasNotaDeCredito>  findCreditosDesde( Date fechainicio);
	
	public Page<VentasNotaDeCredito> findCreditosHasta( Date fechafin, Pageable pageable);
	
	public List<VentasNotaDeCredito>  findCreditosHasta( Date fechafin);
	
	public List<VentasNotaDeCredito> fetchCreditoPendienteByIdWithCliente(Long id);
 
	public List<VentasNotaDeCredito> fetchCreditoByIdWithCliente(Long id);
	
	public List<VentasNotaDeCredito> findCreditosProductos();
	
	public List<VentasNotaDeCredito> findCreditosProductosEntreFechas( Date fechainicio, Date fechafin);
	
	public List<VentasNotaDeCredito>  findCreditosProductosDesde( Date fechainicio);
	
	public List<VentasNotaDeCredito>  findCreditosProductosHasta( Date fechafin);
	
}
