package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.dto.VentasDevolucionBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasDevolucion;
 

public interface VentasDevolucionService {
	
 	public List<VentasDevolucion> findAll();

	public Page<VentasDevolucion> findAll(Pageable pageable);

	public void save(VentasDevolucion remito);

	public VentasDevolucion findOne(Long id);

	public void delete(Long id);
	
	public VentasDevolucion findDevolucionByPrefijoAndNumero(String prefijo, Long numero );
	
	public Page<VentasDevolucion> buscarVentasDevoluciones(VentasDevolucionBusquedaDTO devolucion, Pageable pageable);
	
	public List<VentasDevolucion> buscarVentasDevoluciones(VentasDevolucionBusquedaDTO devolucion);

}
