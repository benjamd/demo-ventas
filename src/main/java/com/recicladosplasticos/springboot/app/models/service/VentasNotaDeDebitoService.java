package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeDebitoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
 

public interface VentasNotaDeDebitoService {

 	public List<VentasNotaDeDebito> findAll();

	public Page<VentasNotaDeDebito> findAll(Pageable pageable);

	public void save(VentasNotaDeDebito ventasNotaDeDebito);

	public VentasNotaDeDebito findOne(Long id);

	public void delete(Long id);
	
	public Page<VentasNotaDeDebito> buscarVentasNotasDeDebito(VentasNotaDeDebitoBusquedaDTO debito, Pageable pageable);
	
	public List<VentasNotaDeDebito> buscarVentasNotasDeDebito(VentasNotaDeDebitoBusquedaDTO debito);
	
 	public VentasNotaDeDebito findDebitoByPrefijoAndNumero(String prefijo, Long numero );
	
	public Page<VentasNotaDeDebito> findDebitosEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	public List<VentasNotaDeDebito> findDebitosEntreFechas( Date fechainicio, Date fechafin);
	
	public  List<VentasNotaDeDebito> findDebitosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
 
	public Page<VentasNotaDeDebito> findDebitosDesde( Date fechainicio, Pageable pageable);
	
	 
	public List<VentasNotaDeDebito>  findDebitosDesde( Date fechainicio);
	
	 
	public Page<VentasNotaDeDebito> findDebitosHasta( Date fechafin, Pageable pageable);
	
	 
	public List<VentasNotaDeDebito>  findDebitosHasta( Date fechafin);
	
	 
	public List<VentasNotaDeDebito> fetchDebitoPendienteByIdWithCliente(Long id);
	
 
	public List<VentasNotaDeDebito> fetchDebitoByIdWithCliente(Long id);
	
	
	public List<VentasNotaDeDebito> findDebitosProductos();
	
	public List<VentasNotaDeDebito> findDebitosProductosEntreFechas( Date fechainicio, Date fechafin);
	
	public List<VentasNotaDeDebito>  findDebitosProductosDesde( Date fechainicio);
	
	public List<VentasNotaDeDebito>  findDebitosProductosHasta( Date fechafin);
	
	
	
}
	

 
