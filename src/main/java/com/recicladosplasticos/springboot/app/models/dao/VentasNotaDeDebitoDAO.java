package com.recicladosplasticos.springboot.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;

public interface VentasNotaDeDebitoDAO extends PagingAndSortingRepository<VentasNotaDeDebito, Long> {
 
 
	@Query("SELECT g FROM VentasNotaDeDebito g  WHERE  g.prefijo = ?1 AND g.numero = ?2")
	public VentasNotaDeDebito findDebitoByPrefijoAndNumero(String prefijo, Long numero );
	
	@Query("SELECT d FROM VentasNotaDeDebito d WHERE d.fecha BETWEEN ?1 AND ?2")
	public Page<VentasNotaDeDebito> findDebitosEntreFechas( Date fechainicio, Date fechafin, Pageable pageable);
	
	@Query("SELECT d FROM VentasNotaDeDebito d WHERE d.fecha BETWEEN ?1 AND ?2")
	public List<VentasNotaDeDebito> findDebitosEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT d FROM VentasNotaDeDebito d WHERE (?1 IS NULL OR  d.numero >= ?1 ) AND (?2 IS NULL OR  d.numero <= ?2 )"
			+ "AND (?3 IS NULL OR  d.fecha >= ?3 ) AND (?2 IS NULL OR  d.fecha <= ?4)")
	public List<VentasNotaDeDebito> findDebitosList(Long numeromin, Long numeromax, Date fechainicio, Date fechafin, String codigo,
			String nombre,String razonsocial);
	
	
	@Query("SELECT d FROM VentasNotaDeDebito d WHERE    d.fecha >= ?1 ")
	public Page<VentasNotaDeDebito> findDebitosDesde( Date fechainicio, Pageable pageable);
	
	@Query("SELECT d FROM VentasNotaDeDebito d WHERE    d.fecha >= ?1 ")
	public List<VentasNotaDeDebito>  findDebitosDesde( Date fechainicio);
	
	@Query("SELECT d FROM VentasNotaDeDebito d WHERE    d.fecha <= ?1 ")
	public Page<VentasNotaDeDebito> findDebitosHasta( Date fechafin, Pageable pageable);
	
	@Query("SELECT d FROM VentasNotaDeDebito d WHERE    d.fecha <= ?1 ")
	public List<VentasNotaDeDebito>  findDebitosHasta( Date fechafin);
	
	@Query("SELECT f FROM VentasNotaDeDebito f join fetch f.cliente c   where c.id=?1 AND f.saldopendiente > 0 ")
	public List<VentasNotaDeDebito> fetchDebitoPendienteByIdWithCliente(Long id);
	
	@Query("SELECT f FROM VentasNotaDeDebito f join fetch f.cliente c   where c.id=?1")
	public List<VentasNotaDeDebito> fetchDebitoByIdWithCliente(Long id);
	
	@Query("SELECT f FROM VentasNotaDeDebito f WHERE f.detalle IS NULL")
	public List<VentasNotaDeDebito>  findDebitosProductos();

	@Query("SELECT f FROM VentasNotaDeDebito f WHERE f.fecha BETWEEN ?1 AND ?2 AND f.detalle IS NULL")
	public List<VentasNotaDeDebito> findDebitosProductosEntreFechas( Date fechainicio, Date fechafin);

	@Query("SELECT f FROM VentasNotaDeDebito f WHERE    f.fecha >= ?1 AND f.detalle IS NULL")
	public List<VentasNotaDeDebito>  findDebitosProductosDesde( Date fechainicio);

	@Query("SELECT f FROM VentasNotaDeDebito f WHERE    f.fecha <= ?1 AND f.detalle IS NULL")
	public List<VentasNotaDeDebito>  findDebitosProductosHasta( Date fechafin);
	
}
