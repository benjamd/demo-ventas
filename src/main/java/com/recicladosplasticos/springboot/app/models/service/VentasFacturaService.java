package com.recicladosplasticos.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.dto.VentasFacturaBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura; 
public interface VentasFacturaService {

 	public List<VentasFactura> findAll();

	public Page<VentasFactura> findAll(Pageable pageable);

	public void save(VentasFactura ventasFactura);

	public VentasFactura findOne(Long id);

	public void delete(Long id);
	
	public  Page<VentasFactura> buscarVentasFacturas(VentasFacturaBusquedaDTO factura, Pageable pageable);
	
	public List<VentasFactura>  buscarVentasFacturas(VentasFacturaBusquedaDTO factura);
	
	public VentasFactura findFacturaByPrefijoAndNumero(String prefijo, Long numero );
	
	public Page<VentasFactura> findFacturasEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	public List<VentasFactura> findFacturasEntreFechas( Date fechainicio, Date fechafin);
	
	public  List<VentasFactura> findFacturasList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
 
	public Page<VentasFactura> findFacturasDesde( Date fechainicio, Pageable pageable);
	 
	public List<VentasFactura>  findFacturasDesde( Date fechainicio);
	 
	public Page<VentasFactura> findFacturasHasta( Date fechafin, Pageable pageable);
	 
	public List<VentasFactura>  findFacturasHasta( Date fechafin);
	
	public  List<VentasFactura> fetchFacturaByIdWithCliente(Long id);
	
	public  List<VentasFactura> fetchFacturaPendienteByIdWithCliente(Long id);

	public List<VentasFactura> findFacturasProductos();
	
	public List<VentasFactura> findFacturasProductosEntreFechas( Date fechainicio, Date fechafin);
	
	public List<VentasFactura>  findFacturasProductosDesde( Date fechainicio);
	
	public List<VentasFactura>  findFacturasProductosHasta( Date fechafin);
	
 
	
}
