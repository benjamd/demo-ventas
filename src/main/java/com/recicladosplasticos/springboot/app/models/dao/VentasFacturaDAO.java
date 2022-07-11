package com.recicladosplasticos.springboot.app.models.dao;
 
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;
 

public interface VentasFacturaDAO extends PagingAndSortingRepository<VentasFactura, Long> {
	
	@Query("select f from VentasFactura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public VentasFactura fetchByIdWithClienteWhithItemFacturaWithProducto(Long id);
	 
	@Query("SELECT f FROM VentasFactura f WHERE (?1 IS NULL OR  f.numero >= ?1 ) AND (?2 IS NULL OR  f.numero <= ?2 )"
			+ "AND (?3 IS  NULL AND  ?4 IS NULL AND f.fecha BETWEEN ?3 AND ?4) OR (?3 IS  NULL AND  f.fecha >= ?3 ) OR (?4 IS NULL OR  f.fecha <= ?4)")
	public Page<VentasFactura> findFacturas(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial, Pageable pageable);
	
	@Query("SELECT g FROM VentasFactura g  WHERE  g.prefijo = ?1 AND g.numero = ?2")
	public VentasFactura findFacturaByPrefijoAndNumero(String prefijo, Long numero );
	
	@Query("SELECT f FROM VentasFactura  f WHERE f.fecha BETWEEN ?1 AND ?2")
	public Page<VentasFactura> findFacturasEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	@Query("SELECT f FROM VentasFactura f WHERE f.fecha BETWEEN ?1 AND ?2")
	public List<VentasFactura> findFacturasEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT f FROM VentasFactura f WHERE (?1 IS NULL OR  f.numero >= ?1 ) AND (?2 IS NULL OR  f.numero <= ?2 )"
			+ "AND (?3 IS NULL OR  f.fecha >= ?3 ) AND (?2 IS NULL OR  f.fecha <= ?4)")
	public List<VentasFactura> findFacturasList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
	@Query("SELECT f FROM VentasFactura f WHERE    f.fecha >= ?1 ")
	public Page<VentasFactura> findFacturasDesde( Date fechainicio, Pageable pageable);
	
	@Query("SELECT f FROM VentasFactura f WHERE    f.fecha >= ?1 ")
	public List<VentasFactura>  findFacturasDesde( Date fechainicio);
	
	@Query("SELECT f FROM VentasFactura f WHERE    f.fecha <= ?1 ")
	public Page<VentasFactura> findFacturasHasta( Date fechafin, Pageable pageable);
	
	@Query("SELECT f FROM VentasFactura f WHERE    f.fecha <= ?1 ")
	public List<VentasFactura>  findFacturasHasta( Date fechafin);
	
	@Query("select f from VentasFactura f join fetch f.cliente c   where c.id=?1 AND f.saldopendiente > 0 ")
	public List<VentasFactura> fetchFacturaPendienteByIdWithCliente(Long id);
	
	@Query("select f from VentasFactura f join fetch f.cliente c   where c.id=?1")
	public List<VentasFactura> fetchFacturaByIdWithCliente(Long id);
	
	@Query("SELECT f FROM VentasFactura f WHERE f.detalle IS NULL")
	public List<VentasFactura>  findFacturasProductos();

	@Query("SELECT f FROM VentasFactura f WHERE f.fecha BETWEEN ?1 AND ?2 AND f.detalle IS NULL")
	public List<VentasFactura> findFacturasProductosEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT f FROM VentasFactura f WHERE    f.fecha >= ?1 AND f.detalle IS NULL")
	public List<VentasFactura>  findFacturasProductosDesde( Date fechainicio);

	@Query("SELECT f FROM VentasFactura f WHERE    f.fecha <= ?1 AND f.detalle IS NULL")
	public List<VentasFactura>  findFacturasProductosHasta( Date fechafin);
	
}
