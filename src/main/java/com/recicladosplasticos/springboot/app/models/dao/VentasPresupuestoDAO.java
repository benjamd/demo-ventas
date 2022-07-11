package com.recicladosplasticos.springboot.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.VentasPresupuesto;


public interface VentasPresupuestoDAO extends PagingAndSortingRepository<VentasPresupuesto, Long>{



	@Query("SELECT g FROM VentasPresupuesto g  WHERE  g.prefijo = ?1 AND g.numero = ?2")
	public VentasPresupuesto findOrdenDeEntregaByPrefijoAndNumero(String prefijo, Long numero );
	
	@Query("SELECT f FROM VentasPresupuesto f WHERE f.fecha BETWEEN ?1 AND ?2")
	public Page<VentasPresupuesto> findOrdenDeEntregaEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	@Query("SELECT f FROM VentasPresupuesto f WHERE f.fecha BETWEEN ?1 AND ?2")
	public List<VentasPresupuesto> findOrdenDeEntregaEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT f FROM VentasPresupuesto f WHERE (?1 IS NULL OR  f.numero >= ?1 ) AND (?2 IS NULL OR  f.numero <= ?2 )"
			+ "AND (?3 IS NULL OR  f.fecha >= ?3 ) AND (?2 IS NULL OR  f.fecha <= ?4)")
	public List<VentasPresupuesto> findOrdenDeEntregaList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
	
	@Query("SELECT f FROM VentasPresupuesto f WHERE    f.fecha >= ?1 ")
	public Page<VentasPresupuesto> findOrdenDeEntregaDesde( Date fechainicio, Pageable pageable);
	
	@Query("SELECT f FROM VentasPresupuesto f WHERE    f.fecha >= ?1 ")
	public List<VentasPresupuesto>  findOrdenDeEntregaDesde( Date fechainicio);
	
	@Query("SELECT f FROM VentasPresupuesto f WHERE    f.fecha <= ?1 ")
	public Page<VentasPresupuesto> findOrdenDeEntregaHasta( Date fechafin, Pageable pageable);
	
	@Query("SELECT f FROM VentasPresupuesto f WHERE    f.fecha <= ?1 ")
	public List<VentasPresupuesto>  findOrdenDeEntregaHasta( Date fechafin);
	
	@Query("select f from VentasPresupuesto f join fetch f.cliente c   where c.id=?1 AND f.saldopendiente > 0 ")
	public List<VentasPresupuesto> fetchOrdenDeEntregaPendienteByIdWithCliente(Long id);
	
	@Query("select f from VentasPresupuesto f join fetch f.cliente c   where c.id=?1")
	public List<VentasPresupuesto> fetchOrdenDeEntregaByIdWithCliente(Long id);
	
	@Query("SELECT f FROM VentasPresupuesto f WHERE f.detalle IS NULL")
	public List<VentasPresupuesto>  findOrdenesDeEntregaProductos();

	@Query("SELECT f FROM VentasPresupuesto f WHERE f.fecha BETWEEN ?1 AND ?2 AND f.detalle IS NULL")
	public List<VentasPresupuesto> findOrdenesDeEntregaProductosEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT f FROM VentasPresupuesto f WHERE    f.fecha >= ?1 AND f.detalle IS NULL")
	public List<VentasPresupuesto>  findOrdenesDeEntregaProductosDesde( Date fechainicio);

	@Query("SELECT f FROM VentasPresupuesto f WHERE    f.fecha <= ?1 AND f.detalle IS NULL")
	public List<VentasPresupuesto>  findOrdenesDeEntregaProductosHasta( Date fechafin);

}
