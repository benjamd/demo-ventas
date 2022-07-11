package com.recicladosplasticos.springboot.app.models.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.recicladosplasticos.springboot.app.models.dto.DocumentoDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasDevolucionBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasFacturaBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeCreditoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasNotaDeDebitoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasPresupuestoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasReciboBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasRemitoBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.VentasDevolucion;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasPresupuesto;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;
import com.recicladosplasticos.springboot.app.models.entity.VentasRemito;

@Repository
public class BuscarRepository implements BuscarDAO {
	
	@Autowired
	private EntityManager em;
	
	public Page<Cliente> buscarClientes(String codigo, String nombre, String razonsocial, String cuit, String actividad,
			String direccion, String codigopostal, String localidad, String partido, String provincia, String pais,
			String contacto, String email, String telefono,  Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		
		Root<Cliente> fromCliente = criteriaQuery.from(Cliente.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		
		List<Predicate> criterios = new ArrayList<Predicate>();
 		
		if(nombre != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("nombre"),
                            "%" + nombre + "%")
            );
		}
		
		if(razonsocial != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("razonsocial"),
                            "%" + razonsocial + "%")
            );
		}
		if(cuit != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("cuit"),
                            "%" + cuit + "%")
            );
		}
		if(actividad != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("actividad"),
                            "%" + actividad + "%")
            );
		}
		
		if(direccion != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("direccion"),
                            "%" + direccion + "%")
            );
		}
		if(codigopostal != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("codigopostal"),
                            "%" + codigopostal + "%")
            );
		}
		if(localidad != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("localidad"),
                            "%" + localidad + "%")
            );
		}
		if(partido != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("partido"),
                            "%" + partido + "%")
            );
		}
		if(provincia != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("provincia"),
                            "%" + provincia + "%")
            );
		}
		if(pais != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("pais"),
                            "%" + pais + "%")
            );
		}
		if(contacto != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("contacto"),
                            "%" + contacto + "%")
            );
		}
		if(email != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("email"),
                            "%" + email + "%")
            );
		}
		if(telefono != null) {
			criterios.add(
                    criteriaBuilder.like(fromCliente.get("telefono"),
                            "%" + telefono + "%")
            );
		}
		
		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[0]));
		
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromCliente.get("nombre")));
		 
		//Hacemos la Query para los construir la Pagina
		TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Cliente> countRoot = countQuery.from(Cliente.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(criterio);
        long  clientCount = em.createQuery(countQuery).getSingleResult();
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, clientCount);

		 
	}

	@Override
	public Page<VentasFactura> buscarVentasFacturas(VentasFacturaBusquedaDTO factura, Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasFactura> criteriaQuery = criteriaBuilder.createQuery(VentasFactura.class);
		Root<VentasFactura> fromFactura = criteriaQuery.from(VentasFactura.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasFactura, Cliente> joinCliente = fromFactura.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(factura, criteriaBuilder, fromFactura, joinCliente);

		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromFactura.get("fecha")),
				criteriaBuilder.asc(fromFactura.get("prefijo")), criteriaBuilder.asc(fromFactura.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasFactura> typedQuery = em.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<VentasFactura> countRoot = countQuery.from(VentasFactura.class);
    	countRoot.join("cliente",JoinType.INNER );
        countQuery.select(criteriaBuilder.count(countRoot)).where(criterio);
        long  facturaCount = em.createQuery(countQuery).getSingleResult();
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, facturaCount);

	}

	@Override
	public Page<VentasNotaDeCredito> buscarVentasNotasDeCredito(VentasNotaDeCreditoBusquedaDTO credito, Pageable pageable) {
		 
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasNotaDeCredito> criteriaQuery = criteriaBuilder.createQuery(VentasNotaDeCredito.class);
		Root<VentasNotaDeCredito> fromNotaDeCredito = criteriaQuery.from(VentasNotaDeCredito.class);
		
		//Definimos predicados (filtros de busqueda)
 		Join<VentasNotaDeCredito, Cliente> joinCliente = fromNotaDeCredito.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(credito, criteriaBuilder, fromNotaDeCredito, joinCliente);
			
		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromNotaDeCredito.get("fecha")),
				criteriaBuilder.asc(fromNotaDeCredito.get("prefijo")), criteriaBuilder.asc(fromNotaDeCredito.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasNotaDeCredito> typedQuery = em.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<VentasNotaDeCredito> countRoot = countQuery.from(VentasNotaDeCredito.class);
    	countRoot.join("cliente",JoinType.INNER );
        countQuery.select(criteriaBuilder.count(countRoot)).where(criterio);
        long  creditoCount = em.createQuery(countQuery).getSingleResult();
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, creditoCount);

	}

	@Override
	public Page<VentasNotaDeDebito> buscarVentasNotasDeDebito(VentasNotaDeDebitoBusquedaDTO debito, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasNotaDeDebito> criteriaQuery = criteriaBuilder.createQuery(VentasNotaDeDebito.class);
		Root<VentasNotaDeDebito> fromNotaDeDebito = criteriaQuery.from(VentasNotaDeDebito.class);
		
		//Definimos predicados (filtros de busqueda)
		
		Join<VentasNotaDeDebito, Cliente> joinCliente = fromNotaDeDebito.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(debito, criteriaBuilder, fromNotaDeDebito, joinCliente);
		
		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromNotaDeDebito.get("fecha")),
				criteriaBuilder.asc(fromNotaDeDebito.get("prefijo")), criteriaBuilder.asc(fromNotaDeDebito.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasNotaDeDebito> typedQuery = em.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<VentasNotaDeDebito> countRoot = countQuery.from(VentasNotaDeDebito.class);
    	countRoot.join("cliente",JoinType.INNER );
        countQuery.select(criteriaBuilder.count(countRoot)).where(criterio);
        long  debitoCount = em.createQuery(countQuery).getSingleResult();
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, debitoCount);
		 
	}

	@Override
	public List<VentasFactura> buscarVentasFacturas(VentasFacturaBusquedaDTO factura) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasFactura> criteriaQuery = criteriaBuilder.createQuery(VentasFactura.class);
		Root<VentasFactura> fromFactura = criteriaQuery.from(VentasFactura.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasFactura, Cliente> joinCliente = fromFactura.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(factura, criteriaBuilder, fromFactura, joinCliente);
		

		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromFactura.get("fecha")),
				criteriaBuilder.asc(fromFactura.get("prefijo")), criteriaBuilder.asc(fromFactura.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasFactura> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<VentasNotaDeCredito> buscarVentasNotasDeCredito(VentasNotaDeCreditoBusquedaDTO credito) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasNotaDeCredito> criteriaQuery = criteriaBuilder.createQuery(VentasNotaDeCredito.class);
		Root<VentasNotaDeCredito> fromNotaDeCredito = criteriaQuery.from(VentasNotaDeCredito.class);
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasNotaDeCredito, Cliente> joinCliente = fromNotaDeCredito.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(credito, criteriaBuilder, fromNotaDeCredito, joinCliente);

		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromNotaDeCredito.get("fecha")),
				criteriaBuilder.asc(fromNotaDeCredito.get("prefijo")), criteriaBuilder.asc(fromNotaDeCredito.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasNotaDeCredito> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<VentasNotaDeDebito> buscarVentasNotasDeDebito(VentasNotaDeDebitoBusquedaDTO debito) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasNotaDeDebito> criteriaQuery = criteriaBuilder.createQuery(VentasNotaDeDebito.class);
		Root<VentasNotaDeDebito> fromNotaDeDebito = criteriaQuery.from(VentasNotaDeDebito.class);
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasNotaDeDebito, Cliente> joinCliente = fromNotaDeDebito.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(debito, criteriaBuilder, fromNotaDeDebito, joinCliente);

		
		if(!debito.getNombre().equals("")) {
			criterios.add( criteriaBuilder.like(joinCliente.get("nombre"), "%" + debito.getNombre() + "%"));
		}
		 
		
		if(!debito.getRazonsocial().equals("")) {
			criterios.add( criteriaBuilder.like(joinCliente.get("razonsocial"), "%" + debito.getRazonsocial() + "%"));
		}
		
 
		
		if(debito.getPrefijo() != null && !debito.getPrefijo().equals("")) {
			criterios.add(
                    criteriaBuilder.like(fromNotaDeDebito.get("prefijo"),   debito.getPrefijo())
            );
		}
		
		if(debito.getNumeromin() != null) {
			criterios.add(
                    criteriaBuilder.greaterThanOrEqualTo(fromNotaDeDebito.get("numero"), debito.getNumeromin())
            );
		}
		
		if(debito.getNumeromax() != null) {
			criterios.add(
                    criteriaBuilder.lessThanOrEqualTo(fromNotaDeDebito.get("numero"), debito.getNumeromax() )
            );
		}
		
		if(debito.getFechainicio() != null && debito.getFechafin() != null) {
				criterios.add(criteriaBuilder.between(fromNotaDeDebito.get("fecha"), debito.getFechainicio(), debito.getFechafin()));
		}
		
		if(debito.getFechainicio() != null && debito.getFechafin() == null) {
			criterios.add(criteriaBuilder.greaterThanOrEqualTo(fromNotaDeDebito.<Date>get("fecha"), debito.getFechainicio()));
		}
		if(debito.getFechainicio() == null && debito.getFechafin() != null) {
			criterios.add(criteriaBuilder.lessThanOrEqualTo(fromNotaDeDebito.<Date>get("fecha"), debito.getFechafin()));
		}
		
		
		if(debito.getImportemin() == null && debito.getImportemax() != null) {
			criterios.add(
                    criteriaBuilder.lessThanOrEqualTo(fromNotaDeDebito.get("importetotal"), debito.getImportemax())
            );
		}
		
		if(debito.getImportemin() != null && debito.getImportemax() == null) {
			criterios.add(
                    criteriaBuilder.greaterThanOrEqualTo(fromNotaDeDebito.get("importetotal"), debito.getImportemin())
            );
		}
		
		
		if(debito.getImportemin() != null && debito.getImportemax() != null) {
			Predicate menorOIgualque = criteriaBuilder.lessThanOrEqualTo(fromNotaDeDebito.get("importetotal"), debito.getImportemax());
			Predicate mayorOIgualque = criteriaBuilder.greaterThanOrEqualTo(fromNotaDeDebito.get("importetotal"), debito.getImportemin());
			criterios.add( criteriaBuilder.and(menorOIgualque, mayorOIgualque));
		}
		
		
        if (criterios.size() > 0) {
        	criterios.forEach((p) -> {
                System.out.println("Predicado : " + p.getExpressions().toString());
                //criteriaQuery.where(criterios.toArray(new Predicate[criterios.size()]));
            });
        }

		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromNotaDeDebito.get("fecha")),
				criteriaBuilder.asc(fromNotaDeDebito.get("prefijo")), criteriaBuilder.asc(fromNotaDeDebito.get("numero")) );
 
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasNotaDeDebito> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public Page<VentasPresupuesto> buscarVentasPresupuestos(VentasPresupuestoBusquedaDTO presupuesto, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasPresupuesto> criteriaQuery = criteriaBuilder.createQuery(VentasPresupuesto.class);
		Root<VentasPresupuesto> fromPresupuesto = criteriaQuery.from(VentasPresupuesto.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasPresupuesto, Cliente> joinCliente = fromPresupuesto.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(presupuesto, criteriaBuilder, fromPresupuesto, joinCliente);
	

		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromPresupuesto.get("fecha")),
				criteriaBuilder.asc(fromPresupuesto.get("prefijo")), criteriaBuilder.asc(fromPresupuesto.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasPresupuesto> typedQuery = em.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<VentasPresupuesto> countRoot = countQuery.from(VentasPresupuesto.class);
    	countRoot.join("cliente",JoinType.INNER );
        countQuery.select(criteriaBuilder.count(countRoot)).where(criterio);
        long  presupuestoCount = em.createQuery(countQuery).getSingleResult();
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, presupuestoCount);

	}

	@Override
	public List<VentasPresupuesto> buscarVentasPresupuestos(VentasPresupuestoBusquedaDTO presupuesto) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasPresupuesto> criteriaQuery = criteriaBuilder.createQuery(VentasPresupuesto.class);
		Root<VentasPresupuesto> fromPresupuesto = criteriaQuery.from(VentasPresupuesto.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasPresupuesto, Cliente> joinCliente = fromPresupuesto.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(presupuesto, criteriaBuilder, fromPresupuesto, joinCliente);

		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(fromPresupuesto.get("fecha")),
				criteriaBuilder.asc(fromPresupuesto.get("prefijo")), criteriaBuilder.asc(fromPresupuesto.get("numero")) );
 
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasPresupuesto> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public Page<VentasRemito> buscarVentasRemitos(VentasRemitoBusquedaDTO documento, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasRemito> criteriaQuery = criteriaBuilder.createQuery(VentasRemito.class);
		Root<VentasRemito> from = criteriaQuery.from(VentasRemito.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasRemito, Cliente> joinCliente = from.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(documento, criteriaBuilder, from, joinCliente);
		
	
		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(from.get("fecha")),
				criteriaBuilder.asc(from.get("prefijo")), criteriaBuilder.asc(from.get("numero")) );
 
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasRemito> typedQuery = em.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<VentasRemito> countRoot = countQuery.from(VentasRemito.class);
    	countRoot.join("cliente",JoinType.INNER );
        countQuery.select(criteriaBuilder.count(countRoot)).where(criterio);
        long  documentoCount = em.createQuery(countQuery).getSingleResult();
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, documentoCount);

		
	}

	@Override
	public List<VentasRemito> buscarVentasRemitos(VentasRemitoBusquedaDTO documento) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasRemito> criteriaQuery = criteriaBuilder.createQuery(VentasRemito.class);
		Root<VentasRemito> from = criteriaQuery.from(VentasRemito.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasRemito, Cliente> joinCliente = from.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(documento, criteriaBuilder, from, joinCliente);
		
		
		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(from.get("fecha")),
				criteriaBuilder.asc(from.get("prefijo")), criteriaBuilder.asc(from.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasRemito> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public Page<VentasDevolucion> buscarVentasDevoluciones(VentasDevolucionBusquedaDTO documento, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasDevolucion> criteriaQuery = criteriaBuilder.createQuery(VentasDevolucion.class);
		Root<VentasDevolucion> from = criteriaQuery.from(VentasDevolucion.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasDevolucion, Cliente> joinCliente = from.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(documento, criteriaBuilder, from, joinCliente);
		
		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(from.get("fecha")),
				criteriaBuilder.asc(from.get("prefijo")), criteriaBuilder.asc(from.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasDevolucion> typedQuery = em.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<VentasDevolucion> countRoot = countQuery.from(VentasDevolucion.class);
    	countRoot.join("cliente",JoinType.INNER );
        countQuery.select(criteriaBuilder.count(countRoot)).where(criterio);
        long  documentoCount = em.createQuery(countQuery).getSingleResult();
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, documentoCount);
	}

	@Override
	public List<VentasDevolucion> buscarVentasDevoluciones(VentasDevolucionBusquedaDTO documento) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasDevolucion> criteriaQuery = criteriaBuilder.createQuery(VentasDevolucion.class);
		Root<VentasDevolucion> from = criteriaQuery.from(VentasDevolucion.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasDevolucion, Cliente> joinCliente = from.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(documento, criteriaBuilder, from, joinCliente);
	
		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(from.get("fecha")),
				criteriaBuilder.asc(from.get("prefijo")), criteriaBuilder.asc(from.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasDevolucion> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public Page<VentasRecibo> buscarVentasRecibos(VentasReciboBusquedaDTO documento, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasRecibo> criteriaQuery = criteriaBuilder.createQuery(VentasRecibo.class);
		Root<VentasRecibo> from = criteriaQuery.from(VentasRecibo.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasRecibo, Cliente> joinCliente = from.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(documento, criteriaBuilder, from, joinCliente);

		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(from.get("fecha")),
				criteriaBuilder.asc(from.get("prefijo")), criteriaBuilder.asc(from.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasRecibo> typedQuery = em.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<VentasRecibo> countRoot = countQuery.from(VentasRecibo.class);
    	countRoot.join("cliente",JoinType.INNER );
        countQuery.select(criteriaBuilder.count(countRoot)).where(criterio);
        long  documentoCount = em.createQuery(countQuery).getSingleResult();
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, documentoCount);

	}

	@Override
	public List<VentasRecibo> buscarVentasRecibos(VentasReciboBusquedaDTO documento) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VentasRecibo> criteriaQuery = criteriaBuilder.createQuery(VentasRecibo.class);
		Root<VentasRecibo> from = criteriaQuery.from(VentasRecibo.class);
		 
		
		//Definimos predicados (filtros de busqueda)
		Join<VentasRecibo, Cliente> joinCliente = from.join("cliente",JoinType.INNER );
		List<Predicate> criterios = definirCriteriosDeBusqueda(documento, criteriaBuilder, from, joinCliente);

		Predicate criterio = criteriaBuilder.and(criterios.toArray(new Predicate[criterios.size()]));
		criteriaQuery.where(criterio).orderBy(criteriaBuilder.asc(from.get("fecha")),
				criteriaBuilder.asc(from.get("prefijo")), criteriaBuilder.asc(from.get("numero")) );
        
		//Hacemos la Query para los construir la Pagina
		TypedQuery<VentasRecibo> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
		 
	}

	private List<Predicate> definirCriteriosDeBusqueda(DocumentoDTO documento,
			CriteriaBuilder criteriaBuilder, Root<?> from, Join<?, Cliente> joinCliente) {
		List<Predicate> criterios = new ArrayList<Predicate>();
 		
		
		
		if(!documento.getNombre().equals("")) {
			criterios.add( criteriaBuilder.like(joinCliente.get("nombre"), "%" + documento.getNombre() + "%"));
		}
		 
		
		if(!documento.getRazonsocial().equals("")) {
			criterios.add( criteriaBuilder.like(joinCliente.get("razonsocial"), "%" + documento.getRazonsocial() + "%"));
		}
		
 
		
		if(documento.getPrefijo() != null && !documento.getPrefijo().equals("")) {
			criterios.add(
                    criteriaBuilder.like(from.get("prefijo"),   documento.getPrefijo())
            );
		}
		
		if(documento.getNumeromin() != null) {
			criterios.add(
                    criteriaBuilder.greaterThanOrEqualTo(from.get("numero"), documento.getNumeromin())
            );
		}
		
		if(documento.getNumeromax() != null) {
			criterios.add(
                    criteriaBuilder.lessThanOrEqualTo(from.get("numero"), documento.getNumeromax() )
            );
		}
		
		if(documento.getFechainicio() != null && documento.getFechafin() != null) {
				criterios.add(criteriaBuilder.between(from.get("fecha"), documento.getFechainicio(), documento.getFechafin()));
		}
		
		if(documento.getFechainicio() != null && documento.getFechafin() == null) {
			criterios.add(criteriaBuilder.greaterThanOrEqualTo(from.<Date>get("fecha"), documento.getFechainicio()));
		}
		if(documento.getFechainicio() == null && documento.getFechafin() != null) {
			criterios.add(criteriaBuilder.lessThanOrEqualTo(from.<Date>get("fecha"), documento.getFechafin()));
		}
		
		
		if(documento.getImportemin() == null && documento.getImportemax() != null) {
			criterios.add(
                    criteriaBuilder.lessThanOrEqualTo(from.get("importetotal"), documento.getImportemax())
            );
		}
		
		if(documento.getImportemin() != null && documento.getImportemax() == null) {
			criterios.add(
                    criteriaBuilder.greaterThanOrEqualTo(from.get("importetotal"), documento.getImportemin())
            );
		}
		
		
		if(documento.getImportemin() != null && documento.getImportemax() != null) {
			Predicate menorOIgualque = criteriaBuilder.lessThanOrEqualTo(from.get("importetotal"), documento.getImportemax());
			Predicate mayorOIgualque = criteriaBuilder.greaterThanOrEqualTo(from.get("importetotal"), documento.getImportemin());
			
			criterios.add( criteriaBuilder.and(menorOIgualque, mayorOIgualque));
		}
		return criterios;
	}

 
 

}
