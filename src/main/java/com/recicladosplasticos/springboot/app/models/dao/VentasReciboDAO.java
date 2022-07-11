package com.recicladosplasticos.springboot.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;

public interface VentasReciboDAO extends PagingAndSortingRepository<VentasRecibo, Long> {
	
 
	
	@Query("SELECT g FROM VentasRecibo g  WHERE  g.prefijo = ?1 AND g.numero = ?2")
	public VentasRecibo findReciboByPrefijoAndNumero(String prefijo, Long numero );
	
	@Query("SELECT f FROM VentasRecibo f WHERE f.fecha BETWEEN ?1 AND ?2")
	public Page<VentasRecibo> findRecibosEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	@Query("SELECT f FROM VentasRecibo f WHERE f.fecha BETWEEN ?1 AND ?2")
	public List<VentasRecibo> findRecibosEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT f FROM VentasRecibo f WHERE (?1 IS NULL OR  f.numero >= ?1 ) AND (?2 IS NULL OR  f.numero <= ?2 )"
			+ "AND (?3 IS NULL OR  f.fecha >= ?3 ) AND (?2 IS NULL OR  f.fecha <= ?4)")
	public List<VentasRecibo> findRecibosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
	
	@Query("SELECT f FROM VentasRecibo f WHERE    f.fecha >= ?1 ")
	public Page<VentasRecibo> findRecibosDesde( Date fechainicio, Pageable pageable);
	
	@Query("SELECT f FROM VentasRecibo f WHERE    f.fecha >= ?1 ")
	public List<VentasRecibo>  findRecibosDesde( Date fechainicio);
	
	@Query("SELECT f FROM VentasRecibo f WHERE    f.fecha <= ?1 ")
	public Page<VentasRecibo> findRecibosHasta( Date fechafin, Pageable pageable);
	
	@Query("SELECT f FROM VentasRecibo f WHERE    f.fecha <= ?1 ")
	public List<VentasRecibo>  findRecibosHasta( Date fechafin);
	
	@Query("select f from VentasRecibo f join fetch f.cliente c   where c.id=?1 AND f.saldopendiente > 0 ")
	public List<VentasRecibo> fetchReciboPendienteByIdWithCliente(Long id);
	
	@Query("select f from VentasRecibo f join fetch f.cliente c   where c.id=?1")
	public List<VentasRecibo> fetchReciboByIdWithCliente(Long id);
	
	@Query("select f from VentasRecibo f join fetch f.cliente c   where c.id=?1 AND f.prefijo =?2")
	public List<VentasRecibo> fetchReciboByIdWithClienteAndPrefijo(Long id, String prefijo);
	
	@Query("select f from VentasRecibo f join fetch f.cliente c   where c.id=?1 AND f.prefijo !=?2")
	public List<VentasRecibo> fetchReciboByIdWithClienteAndPrefijoDistinto(Long id, String prefijo);
	
	@Query("select f from VentasRecibo f join fetch f.cliente c   where c.id=?1 AND f.prefijo =?2 AND f.saldopendiente > 0 ")
	public List<VentasRecibo> fetchReciboPendienteByIdWithClienteAndPrefijo(Long id, String prefijo);
	
	@Query("select f from VentasRecibo f join fetch f.cliente c   where c.id=?1 AND f.prefijo !=?2 AND f.saldopendiente > 0 ")
	public List<VentasRecibo> fetchReciboPendienteByIdWithClienteAndPrefijoDistinto(Long id, String prefijo);
	
	

}
