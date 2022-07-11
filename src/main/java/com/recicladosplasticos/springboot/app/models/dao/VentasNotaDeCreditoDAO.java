package com.recicladosplasticos.springboot.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito; 


public interface VentasNotaDeCreditoDAO extends PagingAndSortingRepository<VentasNotaDeCredito, Long> {
	


	@Query("SELECT g FROM VentasNotaDeCredito g  WHERE  g.prefijo = ?1 AND g.numero = ?2")
	public VentasNotaDeCredito findCreditoByPrefijoAndNumero(String prefijo, Long numero );
	
	
	@Query("SELECT c FROM VentasNotaDeCredito c WHERE c.fecha BETWEEN ?1 AND ?2")
	public Page<VentasNotaDeCredito> findCreditosEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	@Query("SELECT c FROM VentasNotaDeCredito c WHERE c.fecha BETWEEN ?1 AND ?2")
	public List<VentasNotaDeCredito> findCreditosEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT c FROM VentasNotaDeCredito c WHERE (?1 IS NULL OR  c.numero >= ?1 ) AND (?2 IS NULL OR  c.numero <= ?2 )"
			+ "AND (?3 IS NULL OR  c.fecha >= ?3 ) AND (?2 IS NULL OR  c.fecha <= ?4)")
	public List<VentasNotaDeCredito> findCreditosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
	
	@Query("SELECT c FROM VentasNotaDeCredito c WHERE    c.fecha >= ?1 ")
	public Page<VentasNotaDeCredito> findCreditosDesde( Date fechainicio, Pageable pageable);
	
	@Query("SELECT c FROM VentasNotaDeCredito c WHERE    c.fecha >= ?1 ")
	public List<VentasNotaDeCredito>  findCreditosDesde( Date fechainicio);
	
	@Query("SELECT c FROM VentasNotaDeCredito c WHERE    c.fecha <= ?1 ")
	public Page<VentasNotaDeCredito> findCreditosHasta( Date fechafin, Pageable pageable);
	
	@Query("SELECT c FROM VentasNotaDeCredito c WHERE    c.fecha <= ?1 ")
	public List<VentasNotaDeCredito>  findCreditosHasta( Date fechafin);
	
	@Query("select f FROM VentasNotaDeCredito f join fetch f.cliente c   where c.id=?1 AND f.saldopendiente > 0 ")
	public List<VentasNotaDeCredito> fetchCreditoPendienteByIdWithCliente(Long id);
	
	@Query("select f FROM VentasNotaDeCredito f join fetch f.cliente c   where c.id=?1")
	public List<VentasNotaDeCredito> fetchCreditoByIdWithCliente(Long id);
	
	@Query("SELECT f FROM VentasNotaDeCredito f WHERE f.detalle IS NULL")
	public List<VentasNotaDeCredito>  findCreditosProductos();

	@Query("SELECT f FROM VentasNotaDeCredito f WHERE f.fecha BETWEEN ?1 AND ?2 AND f.detalle IS NULL")
	public List<VentasNotaDeCredito> findCreditosProductosEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT f FROM VentasNotaDeCredito f WHERE    f.fecha >= ?1 AND f.detalle IS NULL")
	public List<VentasNotaDeCredito>  findCreditosProductosDesde( Date fechainicio);

	@Query("SELECT f FROM VentasNotaDeCredito f WHERE    f.fecha <= ?1 AND f.detalle IS NULL")
	public List<VentasNotaDeCredito>  findCreditosProductosHasta( Date fechafin);
	
}
