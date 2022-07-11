package com.recicladosplasticos.springboot.app.models.dao;

 
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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



public interface BuscarDAO {
	
	public Page<Cliente> buscarClientes(String codigo, String nombre, String razonsocial, String cuit, String actividad,
			String direccion, String codigopostal, String localidad, String partido, String provincia, String pais,
			String contacto, String email, String telefono,  Pageable pageable); 
	
	public Page<VentasFactura> buscarVentasFacturas(VentasFacturaBusquedaDTO factura, Pageable pageable);
	
	public List<VentasFactura> buscarVentasFacturas(VentasFacturaBusquedaDTO factura);
	
	public Page<VentasNotaDeCredito> buscarVentasNotasDeCredito(VentasNotaDeCreditoBusquedaDTO credito, Pageable pageable);
	
	public List<VentasNotaDeCredito> buscarVentasNotasDeCredito(VentasNotaDeCreditoBusquedaDTO credito);
	
	public Page<VentasNotaDeDebito> buscarVentasNotasDeDebito(VentasNotaDeDebitoBusquedaDTO debito, Pageable pageable);

	public List<VentasNotaDeDebito> buscarVentasNotasDeDebito(VentasNotaDeDebitoBusquedaDTO debito);
	
	public Page<VentasPresupuesto> buscarVentasPresupuestos(VentasPresupuestoBusquedaDTO presupuesto, Pageable pageable);
	
	public List<VentasPresupuesto> buscarVentasPresupuestos(VentasPresupuestoBusquedaDTO presupuesto);
	
	public Page<VentasRemito> buscarVentasRemitos(VentasRemitoBusquedaDTO remito, Pageable pageable);
	
	public List<VentasRemito> buscarVentasRemitos(VentasRemitoBusquedaDTO remito);
	
	public Page<VentasDevolucion> buscarVentasDevoluciones(VentasDevolucionBusquedaDTO devolucion, Pageable pageable);
	
	public List<VentasDevolucion> buscarVentasDevoluciones(VentasDevolucionBusquedaDTO devolucion);
	
	public Page<VentasRecibo> buscarVentasRecibos(VentasReciboBusquedaDTO recibo, Pageable pageable);
	
	public List<VentasRecibo> buscarVentasRecibos(VentasReciboBusquedaDTO recibo);

}
