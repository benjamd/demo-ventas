package com.recicladosplasticos.springboot.app.models.service;

 
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.VentasDevolucion;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura; 
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasPresupuesto;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta; 
import com.recicladosplasticos.springboot.app.models.entity.VentasRemito;

public interface ClienteService {

	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);
	
	public List<Cliente> findAllSortedByNombre();

	public void save(Cliente cliente);

	public Cliente findOne(Long id);

	public void delete(Long id);

	public List<Producto> findByNombre(String term);

	public void saveFactura(VentasFactura ventasFactura);
	
	public PuntoDeVenta saveFactura(Long puntoDeVentaId, VentasFactura ventasFactura);
	
	public PuntoDeVenta saveFacturaProductos(Long puntoDeVentaId, VentasFactura ventasFactura);
	
	public void saveFacturaProductos(VentasFactura ventasFactura);
	
	public void saveCredito(VentasNotaDeCredito credito);
	
	public PuntoDeVenta saveCredito(Long puntoDeVentaId, VentasNotaDeCredito credito);
	
	public void saveCreditoProductos(VentasNotaDeCredito credito);
	
	public PuntoDeVenta saveCreditoProductos(Long puntoDeVentaId,VentasNotaDeCredito credito);
	
	public void saveDebito(VentasNotaDeDebito debito);
	
	public PuntoDeVenta saveDebito(Long puntoDeVentaId, VentasNotaDeDebito debito);
	
	public void saveDebitoProductos(VentasNotaDeDebito debito);
	
	public PuntoDeVenta saveDebitoProductos(Long puntoDeVentaId,VentasNotaDeDebito debito);
	
	public Producto findProductoById(Long id);
	
	public VentasFactura findFacturaById(Long id);
	
	public void deleteFactura(Long id);
	
	public void deleteFactura(VentasFactura ventasFactura, Long id);
	
	public void deleteCredito(Long id);
	
	public void deleteCredito(VentasNotaDeCredito credito, Long id);
	
	public void deleteDebito(Long id);
	
	public void deleteDebito(VentasNotaDeDebito debito, Long id);

	public VentasNotaDeCredito findCreditoById(Long id);
	
	public VentasNotaDeDebito findDebitoById(Long id);
	
	public VentasPresupuesto findOrdenDeEntregaById(Long id);
	
	public void saveOrdenDeEntrega(VentasPresupuesto ordenentrega);
	
	public PuntoDeVenta  saveOrdenDeEntrega(Long puntoDeVentaId, VentasPresupuesto ordenentrega);
	
	public void saveOrdenDeEntregaProductos(VentasPresupuesto ordenentrega);
	
	public PuntoDeVenta saveOrdenDeEntregaProductos(Long puntoDeVentaId,  VentasPresupuesto ordenentrega);
	
	public void deleteOrdenDeEntrega(Long id);
	
	public void deleteOrdenDeEntrega(VentasPresupuesto ordenenterga, Long id);
	
	public VentasRemito findRemitoVentasById(Long id);
	
	public void saveRemitoVentas(VentasRemito ventasRemito);
	
	public PuntoDeVenta saveRemitoVentas(Long puntoDeVentaId, VentasRemito ventasRemito);
	
	public void saveRemitoVentasProductos(VentasRemito ventasRemito);
	
	public PuntoDeVenta saveRemitoVentasProductos(Long puntoDeVentaId, VentasRemito ventasRemito);
	
	public void deleteRemitoVentas(Long id);
	
	public void deleteRemitoVentas(VentasRemito ventasRemito, Long id);
	
	public Page<Cliente> findCliente(String codigo, String nombre, String razonsocial, String cuit, String actividad,
			String direccion, String codigopostal, String localidad, String partido, String provincia, String pais,
			String contacto, String email, String telefono, Pageable pageable);
	
	public VentasDevolucion findDevolucionVentasById(Long id);
	
	public void saveDevolucionVentas(VentasDevolucion ventasDevolucion);
	
	public PuntoDeVenta saveDevolucionVentas(Long puntoDeVentaId, VentasDevolucion ventasDevolucion);
	
	public void saveDevolucionVentasProductos(VentasDevolucion ventasDevolucion);
	
	public PuntoDeVenta saveDevolucionVentasProductos(Long puntoDeVentaId, VentasDevolucion ventasDevolucion);
	
	public void deleteDevolucionVentas(Long id);
	
	public void deleteDevolucionVentas(VentasDevolucion ventasDevolucion, Long id);
	
	public void saveRecibo(VentasRecibo recibo, Set<VentasRecibo> recibosimputados, Set<VentasFactura> facturasimputadas,
						   Set<VentasNotaDeCredito> creditosimputados, Set<VentasNotaDeDebito> debitosimputados);
	
	public PuntoDeVenta saveRecibo(VentasRecibo recibo, Long puntoDeVentaId, Set<VentasRecibo> recibosimputados, Set<VentasFactura> facturasimputadas,
			   Set<VentasNotaDeCredito> creditosimputados, Set<VentasNotaDeDebito> debitosimputados);
 
	public void deleteRecibo(VentasRecibo recibo, Long id);
	
	public VentasRecibo findReciboById(Long id);
	
}
