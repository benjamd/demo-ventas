package com.recicladosplasticos.springboot.app.models.service;

 
import java.math.RoundingMode;
 
import java.util.List;
import java.util.Set;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
 
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.recicladosplasticos.springboot.app.models.dao.BuscarDAO;
import com.recicladosplasticos.springboot.app.models.dao.ClienteDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasFacturaDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasPresupuestoDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasReciboDAO;
import com.recicladosplasticos.springboot.app.models.dao.ProductoDao;
import com.recicladosplasticos.springboot.app.models.dao.PuntoDeVentaDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasRemitoDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasDevolucionDAO; 
import com.recicladosplasticos.springboot.app.models.dao.VentasNotaDeCreditoDAO;
import com.recicladosplasticos.springboot.app.models.dao.VentasNotaDeDebitoDAO;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.VentasDevolucion;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura; 
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboFactura;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasDevolucion;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasPresupuesto;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasRemito;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasPresupuesto;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;
import com.recicladosplasticos.springboot.app.models.entity.VentasReciboCreditoDebitado;
import com.recicladosplasticos.springboot.app.models.entity.VentasReciboFacturaAcreditada;
import com.recicladosplasticos.springboot.app.models.entity.VentasReciboPendiente;
import com.recicladosplasticos.springboot.app.models.entity.Producto;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.entity.VentasRemito;
 
@Service
public class ClienteServiceImpl  implements ClienteService {

	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private ProductoDao productoDao;

	@Autowired
	private VentasFacturaDAO ventasFacturaDAO;
		
	@Autowired
	private VentasNotaDeCreditoDAO ventaNotaDeCreditoDao;
	
	@Autowired
	private VentasNotaDeDebitoDAO ventaNotaDeDebitoDao;
	
	@Autowired
	private VentasPresupuestoDAO ventasPresupuestoDAO;
	
	@Autowired
	private VentasRemitoDAO remitoVentasDao;
	
	@Autowired
	private VentasDevolucionDAO devolucionDao;
	
	@Autowired
	private VentasReciboDAO reciboDao;
 
	@Autowired
	private PuntoDeVentaDAO puntoventaDao;
	 
	@Autowired
	private BuscarDAO buscarDao;
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDAO.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDAO.deleteById(id);

	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDAO.findAll(pageable);
	}

	
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@Transactional
	public void saveFactura(VentasFactura ventasFactura) {
		ventasFacturaDAO.save(ventasFactura);
	} 
	
	@Override
	@Transactional
	public void saveFacturaProductos(VentasFactura ventasFactura) {
		for(ItemVentasFactura item: ventasFactura.getItems()) {
			Producto producto = item.getProducto();
			producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
			productoDao.save(producto);
		}
		ventasFacturaDAO.save(ventasFactura);
	} 
	
	@Override
	@Transactional(readOnly=true)
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public VentasFactura findFacturaById(Long id) {
		return ventasFacturaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		ventasFacturaDAO.deleteById(id);
	}
	
	@Override
	@Transactional
	public void deleteFactura(VentasFactura ventasFactura, Long id) {
		if(ventasFactura.getItems() != null && ventasFactura.getItems().size() > 0) {
			for(ItemVentasFactura item: ventasFactura.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().add(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
		}
		ventasFacturaDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAllSortedByNombre() {
		return null; //(List<Cliente>) clienteDao.findByOrderedByNombreAsc();
	}

	@Override
	@Transactional(readOnly = true)
	public VentasNotaDeCredito findCreditoById(Long id) {
		return  ventaNotaDeCreditoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void saveCredito(VentasNotaDeCredito credito) {
		ventaNotaDeCreditoDao.save(credito);
	}

	@Override
	@Transactional
	public void deleteCredito(Long id) {
		ventaNotaDeCreditoDao.deleteById(id);
	}

	@Override
	@Transactional
	public void saveDebito(VentasNotaDeDebito debito) {
		ventaNotaDeDebitoDao.save(debito);
	}

	@Override
	@Transactional(readOnly = true)
	public VentasNotaDeDebito findDebitoById(Long id) {
		return ventaNotaDeDebitoDao.findById(id).orElse(null) ;
	}

	@Override
	@Transactional
	public void deleteDebito(Long id) {
		ventaNotaDeDebitoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public VentasPresupuesto findOrdenDeEntregaById(Long id) {
		return ventasPresupuestoDAO.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public void saveOrdenDeEntrega(VentasPresupuesto ordenentrega) {
		ventasPresupuestoDAO.save(ordenentrega);
	}

	@Override
	@Transactional
	public void deleteOrdenDeEntrega(Long id) {
		ventasPresupuestoDAO.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public VentasRemito findRemitoVentasById(Long id) {
		 
		return remitoVentasDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void saveRemitoVentas(VentasRemito ventasRemito) {
		remitoVentasDao.save(ventasRemito);
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteRemitoVentas(Long id) {
		remitoVentasDao.deleteById(id);
		
	}


	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findCliente(String codigo, String nombre, String razonsocial, String cuit, String actividad,
			String direccion, String codigopostal, String localidad, String partido, String provincia, String pais,
			String contacto, String email, String telefono,  Pageable pageable) {
		Page<Cliente> clientes = buscarDao.buscarClientes(codigo, nombre, razonsocial, cuit, actividad, direccion, codigopostal, localidad, partido, provincia, pais, contacto, email, telefono, pageable);
		return clientes;
	}

	@Override
	@Transactional(readOnly = true)
	public VentasDevolucion findDevolucionVentasById(Long id) {
		return devolucionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional 
	public void saveDevolucionVentas(VentasDevolucion ventasDevolucion) {
		devolucionDao.save(ventasDevolucion);
	}

	@Override
	@Transactional 
	public void deleteDevolucionVentas(Long id) {
		devolucionDao.deleteById(id);
	}

 
 

 

	@Override
	@Transactional
	public void saveRecibo(VentasRecibo recibo,   Set<VentasRecibo> recibosimputados,
			Set<VentasFactura> facturasimputadas, Set<VentasNotaDeCredito> creditosimputados, Set<VentasNotaDeDebito> debitosimputados) {
		reciboDao.save(recibo);
		if(recibosimputados != null && recibosimputados.size() > 0 ) {
			for(VentasRecibo reciboimputado: recibosimputados) {
				reciboDao.save(reciboimputado);
			}
		}
		if(facturasimputadas != null && facturasimputadas.size() > 0 ) {
			for(VentasFactura ventasFactura: facturasimputadas) {
				ventasFacturaDAO.save(ventasFactura);
			}
		}
		if(debitosimputados != null && debitosimputados.size() > 0 ) {
			for(VentasNotaDeDebito debito: debitosimputados) {
				ventaNotaDeDebitoDao.save(debito);
			}
		}
		if(creditosimputados != null && creditosimputados.size() > 0 ) {
			for(VentasNotaDeCredito credito: creditosimputados) {
				ventaNotaDeCreditoDao.save(credito);
			}
		}
	}

	@Override
	@Transactional
	public void deleteRecibo(VentasRecibo recibo, Long id) {
		//FACTURAS ACREDITADAS
		if(recibo.getFacturasacreditadas()  != null) {
			for(VentasReciboFacturaAcreditada itemfactura: recibo.getFacturasacreditadas()) {
			
				VentasFactura ventasFactura = itemfactura.getFactura();
				ventasFactura.setSaldopendiente(ventasFactura.getSaldopendiente().add(itemfactura.getImportecredito()).setScale(2,RoundingMode.HALF_EVEN) );
				VentasNotaDeCredito credito = itemfactura.getCredito();
				credito.setSaldopendiente(credito.getSaldopendiente().add(itemfactura.getImportecredito()).setScale(2,RoundingMode.HALF_EVEN));
			  
			}
		}
		//CREDITOS DEBITADOS
		if(recibo.getCreditosdebitados() != null) {
			for(VentasReciboCreditoDebitado itemcredito: recibo.getCreditosdebitados()) {
			
				VentasNotaDeCredito credito = itemcredito.getCredito();
				credito.setSaldopendiente(credito.getSaldopendiente().add(itemcredito.getImportedebito()).setScale(2,RoundingMode.HALF_EVEN));
				VentasNotaDeDebito debito = itemcredito.getDebito();
				debito.setSaldopendiente(debito.getSaldopendiente().add(itemcredito.getImportedebito()).setScale(2,RoundingMode.HALF_EVEN));
			}
		}
		//SALVANDO FACTURAS
		if(recibo.getFacturas() != null) {
			for(VentasItemReciboFactura itemfactura: recibo.getFacturas()) {
				
				VentasFactura ventasFactura = itemfactura.getFactura();
				ventasFactura.setSaldopendiente(ventasFactura.getSaldopendiente().add(itemfactura.getImporteimputado()).setScale(2,RoundingMode.HALF_EVEN));
					  
				ventasFacturaDAO.save(ventasFactura);
			}
		}
		//SALVANDO CREDITOS
		if(recibo.getCreditos() != null) {
			for(VentasItemReciboCredito itemcredito: recibo.getCreditos()) {
				
				VentasNotaDeCredito credito = itemcredito.getCredito();
				ventaNotaDeCreditoDao.save(credito);
			}
		}		
		//SALVANDO DEBITOS
		if(recibo.getDebitos() != null) {
			for(VentasItemReciboDebito itemdebito: recibo.getDebitos()) {
				
				VentasNotaDeDebito debito = itemdebito.getDebito();
				ventaNotaDeDebitoDao.save(debito);
			}
		}	
		//ORDEN DE PAGO
		if(recibo.getRecibos() != null) {
			for(VentasReciboPendiente itemrecibopendiente: recibo.getRecibos()) {
				VentasRecibo recibopendiente = itemrecibopendiente.getRecibo();
				recibopendiente.setSaldopendiente(recibopendiente.getSaldopendiente().add( itemrecibopendiente.getImportependiente()).setScale(2,RoundingMode.HALF_EVEN));
				reciboDao.save(recibopendiente);
			}
		}
		reciboDao.deleteById(id);
	}
	

	@Override
	@Transactional(readOnly = true)
	public VentasRecibo findReciboById(Long id) {
		return reciboDao.findById(id).orElse(null);
	}

 
	@Override
	@Transactional 
	public void deleteCredito(VentasNotaDeCredito credito, Long id) {
		if(credito.getItems() != null && credito.getItems().size() > 0) {
			for(ItemVentasNotaDeCredito item: credito.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
		}
		ventaNotaDeCreditoDao.deleteById(id);
	}

	@Override
	@Transactional 
	public void deleteDebito(VentasNotaDeDebito debito, Long id) {
		if(debito.getItems() != null && debito.getItems().size() > 0) {
			for(ItemVentasNotaDeDebito item: debito.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().add(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
		}
		ventaNotaDeDebitoDao.deleteById(id);
	}
	
	@Override
	@Transactional 
	public void deleteOrdenDeEntrega(VentasPresupuesto ordenenterga, Long id) {
		if(ordenenterga.getItems() != null && ordenenterga.getItems().size() > 0) {
			for(ItemVentasPresupuesto item: ordenenterga.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().add(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
		}
		ventasPresupuestoDAO.deleteById(id);
	}

	@Override
	@Transactional 
	public void deleteRemitoVentas(VentasRemito ventasRemito, Long id) {
		if(ventasRemito.getItems() != null && ventasRemito.getItems().size() > 0) {
			for(ItemVentasRemito item: ventasRemito.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().add(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
		}
		remitoVentasDao.deleteById(id);
	}

	@Override
	@Transactional 
	public void saveCreditoProductos(VentasNotaDeCredito credito) {
		for(ItemVentasNotaDeCredito item: credito.getItems()) {
			Producto producto = item.getProducto();
			producto.setStock(producto.getStock().add(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
			productoDao.save(producto);
		}
		ventaNotaDeCreditoDao.save(credito);
	}

	@Override
	@Transactional 
	public void saveDebitoProductos(VentasNotaDeDebito debito) {
		for(ItemVentasNotaDeDebito item: debito.getItems()) {
			Producto producto = item.getProducto();
			producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
			productoDao.save(producto);
		}
		ventaNotaDeDebitoDao.save(debito);
	}

	@Override
	@Transactional 
	public void saveOrdenDeEntregaProductos(VentasPresupuesto ordenentrega) {
		for(ItemVentasPresupuesto item: ordenentrega.getItems()) {
			Producto producto = item.getProducto();
			producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
			productoDao.save(producto);
		}
		ventasPresupuestoDAO.save(ordenentrega);
	}

	@Override
	@Transactional 
	public void saveRemitoVentasProductos(VentasRemito ventasRemito) {
		for(ItemVentasRemito item: ventasRemito.getItems()) {
			Producto producto = item.getProducto();
			producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
			productoDao.save(producto);
		}
		remitoVentasDao.save(ventasRemito);
	}

	@Override
	@Transactional 
	public void saveDevolucionVentasProductos(VentasDevolucion ventasDevolucion) {
		for(ItemVentasDevolucion item: ventasDevolucion.getItems()) {
			Producto producto = item.getProducto();
			producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
			productoDao.save(producto);
		}
		devolucionDao.save(ventasDevolucion);
	}

	@Override
	@Transactional 
	public void deleteDevolucionVentas(VentasDevolucion ventasDevolucion, Long id) {
		if(ventasDevolucion.getItems() != null && ventasDevolucion.getItems().size() > 0) {
			for(ItemVentasDevolucion item: ventasDevolucion.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().add(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
		}
		devolucionDao.deleteById(id);
	}

 
	
	@Override
	@Transactional 
	public PuntoDeVenta saveFactura(Long puntoDeVentaId, VentasFactura ventasFactura) {
		PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		if(ptovta != null) {
			ventasFactura.setPrefijo(ptovta.getPrefijo());
			ventasFactura.setNumero(ptovta.getNumero());
			ventasFactura.setLetra(ptovta.getTipo());
			ventasFactura.setDocumento(ptovta.getDocumento());
			ventasFacturaDAO.save(ventasFactura);
			ptovta.setNumero(ptovta.getNumero()+1);
			puntoventaDao.save(ptovta);
		}
		return  ptovta;
	}
	
	@Override
	@Transactional 
	public PuntoDeVenta saveFacturaProductos(Long puntoDeVentaId, VentasFactura ventasFactura) {
		PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		if(ptovta != null) {
			for(ItemVentasFactura item: ventasFactura.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
			ventasFactura.setPrefijo(ptovta.getPrefijo());
			ventasFactura.setNumero(ptovta.getNumero());
			ventasFactura.setLetra(ptovta.getTipo());
			ventasFactura.setDocumento(ptovta.getDocumento());
			ventasFacturaDAO.save(ventasFactura);
			ptovta.setNumero(ptovta.getNumero()+1);
			puntoventaDao.save(ptovta);
		}
		return  ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveOrdenDeEntrega(Long puntoDeVentaId, VentasPresupuesto ordenentrega) {
		PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		 if(ptovta != null) {	
			ordenentrega.setLetra(ptovta.getTipo());
			ordenentrega.setPrefijo(ptovta.getPrefijo());
			ordenentrega.setNumero(ptovta.getNumero());
			ordenentrega.setDocumento(ptovta.getDocumento());
			ventasPresupuestoDAO.save(ordenentrega);
			ptovta.setNumero(ptovta.getNumero()+1);
			puntoventaDao.save(ptovta);
		 }	
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveOrdenDeEntregaProductos(Long puntoDeVentaId, VentasPresupuesto ordenentrega) {
		
	 PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
	 if(ptovta != null) {	
		for(ItemVentasPresupuesto item: ordenentrega.getItems()) {
			Producto producto = item.getProducto();
			producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
			productoDao.save(producto);
		}
		ordenentrega.setLetra(ptovta.getTipo());
		ordenentrega.setPrefijo(ptovta.getPrefijo());
		ordenentrega.setNumero(ptovta.getNumero());
		ordenentrega.setDocumento(ptovta.getDocumento());
		ventasPresupuestoDAO.save(ordenentrega);
		ptovta.setNumero(ptovta.getNumero()+1);
		puntoventaDao.save(ptovta);
	 }	
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveCredito(Long puntoDeVentaId, VentasNotaDeCredito credito) {
		PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		if(ptovta != null) {
			credito.setLetra(ptovta.getTipo());
			credito.setPrefijo(ptovta.getPrefijo());
			credito.setNumero(ptovta.getNumero());
			credito.setDocumento(ptovta.getDocumento());
			ventaNotaDeCreditoDao.save(credito);
			ptovta.setNumero(ptovta.getNumero()+1 );
			puntoventaDao.save(ptovta);
		}
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveCreditoProductos(Long puntoDeVentaId, VentasNotaDeCredito credito) {
		PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		if(ptovta != null) {
			for(ItemVentasNotaDeCredito item: credito.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().add(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
			credito.setLetra(ptovta.getTipo());
			credito.setPrefijo(ptovta.getPrefijo());
			credito.setNumero(ptovta.getNumero());
			credito.setDocumento(ptovta.getDocumento());
			ventaNotaDeCreditoDao.save(credito);
			ptovta.setNumero(ptovta.getNumero()+1 );
			puntoventaDao.save(ptovta);
		}
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveDebito(Long puntoDeVentaId, VentasNotaDeDebito debito) {
		PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		if(ptovta != null) {
			debito.setLetra(ptovta.getTipo());
			debito.setPrefijo(ptovta.getPrefijo());
			debito.setNumero(ptovta.getNumero());
			debito.setDocumento(ptovta.getDocumento());
			ventaNotaDeDebitoDao.save(debito);
			ptovta.setNumero(ptovta.getNumero()+1 );
			puntoventaDao.save(ptovta);
		}
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveDebitoProductos(Long puntoDeVentaId, VentasNotaDeDebito debito) {
		PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		if(ptovta != null) {
			for(ItemVentasNotaDeDebito item: debito.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
			debito.setLetra(ptovta.getTipo());
			debito.setPrefijo(ptovta.getPrefijo());
			debito.setNumero(ptovta.getNumero());
			debito.setDocumento(ptovta.getDocumento());
			ventaNotaDeDebitoDao.save(debito);
			ptovta.setNumero(ptovta.getNumero()+1 );
			puntoventaDao.save(ptovta);
		}
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveRecibo(VentasRecibo recibo, Long puntoDeVentaId, Set<VentasRecibo> recibosimputados,
			Set<VentasFactura> facturasimputadas, Set<VentasNotaDeCredito> creditosimputados, Set<VentasNotaDeDebito> debitosimputados) {
		 PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		 if(ptovta != null) {	
				recibo.setPrefijo(ptovta.getPrefijo());
				recibo.setNumero(ptovta.getNumero());
				recibo.setDocumento(ptovta.getDocumento());
				reciboDao.save(recibo);
				ptovta.setNumero(ptovta.getNumero()+1 );
				puntoventaDao.save(ptovta);
				if(recibosimputados != null && recibosimputados.size() > 0 ) {
					for(VentasRecibo reciboimputado: recibosimputados) {
						reciboDao.save(reciboimputado);
					}
				}
				if(facturasimputadas != null && facturasimputadas.size() > 0 ) {
					for(VentasFactura ventasFactura: facturasimputadas) {
						ventasFacturaDAO.save(ventasFactura);
					}
				}
				if(debitosimputados != null && debitosimputados.size() > 0 ) {
					for(VentasNotaDeDebito debito: debitosimputados) {
						ventaNotaDeDebitoDao.save(debito);
					}
				}
				if(creditosimputados != null && creditosimputados.size() > 0 ) {
					for(VentasNotaDeCredito credito: creditosimputados) {
						ventaNotaDeCreditoDao.save(credito);
					}
				}
				
		 }	
			return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveRemitoVentas(Long puntoDeVentaId, VentasRemito ventasRemito) {
		 PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		 if(ptovta != null) {	
			ventasRemito.setLetra(ptovta.getTipo());
			ventasRemito.setPrefijo(ptovta.getPrefijo());
			ventasRemito.setNumero(ptovta.getNumero());
			ventasRemito.setDocumento(ptovta.getDocumento());
			remitoVentasDao.save(ventasRemito);
			ptovta.setNumero(ptovta.getNumero()+1 );
			puntoventaDao.save(ptovta);
		 }	
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveRemitoVentasProductos(Long puntoDeVentaId, VentasRemito ventasRemito) {
		 PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		 if(ptovta != null) {	
			for(ItemVentasRemito item: ventasRemito.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
			ventasRemito.setLetra(ptovta.getTipo());
			ventasRemito.setPrefijo(ptovta.getPrefijo());
			ventasRemito.setNumero(ptovta.getNumero());
			ventasRemito.setDocumento(ptovta.getDocumento());
			remitoVentasDao.save(ventasRemito);
			ptovta.setNumero(ptovta.getNumero()+1 );
			puntoventaDao.save(ptovta);

		 }	
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveDevolucionVentas(Long puntoDeVentaId, VentasDevolucion ventasDevolucion) {
		 PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		 if(ptovta != null) {	
			 ventasDevolucion.setLetra(ptovta.getTipo());
			 ventasDevolucion.setPrefijo(ptovta.getPrefijo());
			 ventasDevolucion.setNumero(ptovta.getNumero());
			 ventasDevolucion.setDocumento(ptovta.getDocumento());
			devolucionDao.save(ventasDevolucion);
			ptovta.setNumero(ptovta.getNumero()+1 );
			puntoventaDao.save(ptovta);
		 }	
		return ptovta;
	}

	@Override
	@Transactional 
	public PuntoDeVenta saveDevolucionVentasProductos(Long puntoDeVentaId, VentasDevolucion ventasDevolucion) {
		 PuntoDeVenta ptovta = puntoventaDao.findById(puntoDeVentaId).orElse(null);
		 if(ptovta != null) {	
			for(ItemVentasDevolucion item: ventasDevolucion.getItems()) {
				Producto producto = item.getProducto();
				producto.setStock(producto.getStock().subtract(item.getCantidad()).setScale(2,RoundingMode.HALF_EVEN));
				productoDao.save(producto);
			}
			ventasDevolucion.setLetra(ptovta.getTipo());
			ventasDevolucion.setPrefijo(ptovta.getPrefijo());
			ventasDevolucion.setNumero(ptovta.getNumero());
			ventasDevolucion.setDocumento(ptovta.getDocumento());
			devolucionDao.save(ventasDevolucion);
			ptovta.setNumero(ptovta.getNumero()+1 );
			puntoventaDao.save(ptovta);
		 }	
		return ptovta;
	}

 
 
}
