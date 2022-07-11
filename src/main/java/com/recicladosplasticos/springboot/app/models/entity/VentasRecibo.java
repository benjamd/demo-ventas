package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonManagedReference;
 
 

@Entity
@Table(name = "ventas_recibos")
public class VentasRecibo implements Serializable, Documento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long numero;
	
	private String prefijo;
	
	private String documento;
	@Min(0)
	
	@Digits(integer=12, fraction=2)
	private BigDecimal importetotal;
	
	@Column(name = "saldo_pendiente")
	@Digits(integer=12, fraction=2)
	private BigDecimal saldopendiente;
	
	@Column(name = "saldo_a_cuenta")
	@Digits(integer=12, fraction=2)
	private BigDecimal saldoacuenta;
	
	@Min(0)
	@Digits(integer=12, fraction=2)
	private BigDecimal efectivo;
	
	@Column(columnDefinition = "BOOLEAN")
	private Boolean anulada;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;
	 
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
	private List<VentasItemDepositoTransferencia> itemsdepotransferencias;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
	private List<VentasItemCheque> itemscheques;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
	private List<VentasItemReciboRetencion> itemsretenciones;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
	 	private List<VentasItemReciboFactura> facturas;
 	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
	private List<VentasItemReciboCredito> creditos;
 	 
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
 	private List<VentasItemReciboDebito> debitos;
	
 
 	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
 	private List<VentasReciboPendiente> recibos;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
 	private List<VentasReciboFacturaAcreditada> facturasacreditadas;
	 
	 
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "recibo_id")
 	private List<VentasReciboCreditoDebitado> creditosdebitados;

	private static final long serialVersionUID = 1L;
	
		 
	public VentasRecibo() {
		this.itemscheques =  new ArrayList<VentasItemCheque>();
		this.itemsdepotransferencias = new ArrayList<VentasItemDepositoTransferencia>();
		this.itemsretenciones = new ArrayList<VentasItemReciboRetencion>();
		
		
 		this.facturas = new ArrayList<VentasItemReciboFactura>();
 		this.creditos = new ArrayList<VentasItemReciboCredito>();
 		this.debitos = new ArrayList<VentasItemReciboDebito>(); 
 		this.recibos = new  ArrayList<VentasReciboPendiente>();
	 
 		
 		this.facturasacreditadas = new ArrayList<VentasReciboFacturaAcreditada>();
 		this.creditosdebitados = new ArrayList<VentasReciboCreditoDebitado>();
	}

 	 
		@PrePersist
		public void prePersist() {
			createAt = new Date();
			anulada = false;
	 
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getSaldoacuenta() {
		return saldoacuenta;
	}


	public void setSaldoacuenta(BigDecimal saldoacuenta) {
		this.saldoacuenta = saldoacuenta;
	}


	public BigDecimal getEfectivo() {
		return efectivo;
	}


	public void setEfectivo(BigDecimal efectivo) {
		this.efectivo = efectivo;
	}


	 
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<VentasItemReciboFactura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<VentasItemReciboFactura> facturas) {
		this.facturas = facturas;
	}

	public List<VentasItemReciboCredito> getCreditos() {
		return creditos;
	}

	public void setCreditos(List<VentasItemReciboCredito> creditos) {
		this.creditos = creditos;
	}

	public List<VentasItemReciboDebito> getDebitos() {
		return debitos;
	}

	public void setDebitos(List<VentasItemReciboDebito> debitos) {
		this.debitos = debitos;
	}

	 

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	 


	public Boolean getAnulada() {
		return anulada;
	}


	public void setAnulada(Boolean anulada) {
		this.anulada = anulada;
	}


	public List<VentasItemDepositoTransferencia> getItemsdepotransferencias() {
		return itemsdepotransferencias;
	}

	public void setItemsdepotransferencias(List<VentasItemDepositoTransferencia> itemsdepotransferencias) {
		this.itemsdepotransferencias = itemsdepotransferencias;
	}

	public List<VentasItemCheque> getItemscheques() {
		return itemscheques;
	}

	public void setItemscheques(List<VentasItemCheque> itemscheques) {
		this.itemscheques = itemscheques;
	}
	 
	public void addItemscheques( VentasItemCheque itemcheque) {
		
		this.itemscheques.add(itemcheque);
	}
	public void addItemsdepotransferencias(VentasItemDepositoTransferencia itemsdepotransferencias) {
		
		this.itemsdepotransferencias.add(itemsdepotransferencias);
	}

	
	public void addItemsRetenciones( VentasItemReciboRetencion itemretencion) {
		
		this.itemsretenciones.add(itemretencion);
	}
	
 
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

 
	
	public void addItemReciboFactura(VentasItemReciboFactura itemRecibofactura) {
		
		this.facturas.add(itemRecibofactura);
	}
	
	public void addItemReciboCredito(VentasItemReciboCredito itemRecibocredito) {
		
		this.creditos.add(itemRecibocredito);
	}
	
	public void addItemReciboDebito(VentasItemReciboDebito itemRecibodebito) {
		
		this.debitos.add(itemRecibodebito);
	}
	
 
	public void addItemReciboReciboPendiente(VentasReciboPendiente itemReciboReciboPendiente) {
		
		this.recibos.add(itemReciboReciboPendiente);
	}


	public List<VentasItemReciboRetencion> getItemsretenciones() {
		return itemsretenciones;
	}


	public void setItemsretenciones(List<VentasItemReciboRetencion> itemsretenciones) {
		this.itemsretenciones = itemsretenciones;
	}


	public List<VentasReciboPendiente> getRecibos() {
		return recibos;
	}


	public void setRecibos(List<VentasReciboPendiente> recibos) {
		this.recibos = recibos;
	}

 

 

	public List<VentasReciboFacturaAcreditada> getFacturasacreditadas() {
		return facturasacreditadas;
	}


	public void setFacturasacreditadas(List<VentasReciboFacturaAcreditada> facturasacreditadas) {
		this.facturasacreditadas = facturasacreditadas;
	}

	public void addItemReciboFacturaAcreditada(VentasReciboFacturaAcreditada facturaAcreditada) {
		
		this.facturasacreditadas.add(facturaAcreditada);
	}
	
 
	
	public List<VentasReciboCreditoDebitado> getCreditosdebitados() {
		return creditosdebitados;
	}


	public void setCreditosdebitados(List<VentasReciboCreditoDebitado> creditosdebitados) {
		this.creditosdebitados = creditosdebitados;
	}

	public void addItemReciboCreditoDebitado(VentasReciboCreditoDebitado creditoDebitado) {
		
		this.creditosdebitados.add(creditoDebitado);
	}
	
	
	
 

	public BigDecimal getSaldopendiente() {
		return saldopendiente;
	}


	public void setSaldopendiente(BigDecimal saldopendiente) {
		this.saldopendiente = saldopendiente;
	}


	public BigDecimal getImportetotal() {
		return importetotal;
	}


	public void setImportetotal(BigDecimal importetotal) {
		this.importetotal = importetotal;
	}


	@Override
	public String getObservacion() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setObservacion(String observacion) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Double getSubTotal() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Double totalMasIva() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Double importeIva() {
		// TODO Auto-generated method stub
		return null;
	}

  
	@Override
	public String getLetra() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setLetra(String letra) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Double getAlicuotaiva() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setAlicuotaiva(Double alicuotaiva) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getCondventa() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setCondventa(String condventa) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getDetalle() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setDetalle(String detalle) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public BigDecimal getImporte() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setImporte(BigDecimal importe) {
		// TODO Auto-generated method stub
		
	}

 


	@Override
	public BigDecimal getImporteiva() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setImporteiva(BigDecimal importeiva) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Item> getLineas() {
		 
		return null;
	}


	@Override
	public VentasPresupuesto getOrdenentrega() {
	 
		return null;
	}


	@Override
	public String toString() {
		return "VentasRecibo [id=" + id + ", numero=" + numero + ", prefijo=" + prefijo + ", documento=" + documento
				+ ", importetotal=" + importetotal + ", saldopendiente=" + saldopendiente + ", saldoacuenta="
				+ saldoacuenta + ", efectivo=" + efectivo + ", anulada=" + anulada + ", fecha=" + fecha + ", createAt="
				+ createAt + ", itemsdepotransferencias=" + itemsdepotransferencias + ", itemscheques=" + itemscheques
				+ ", itemsretenciones=" + itemsretenciones + ", cliente=" + cliente + ", facturas=" + facturas
				+ ", creditos=" + creditos + ", debitos=" + debitos + ", recibos=" + recibos + ", facturasacreditadas="
				+ facturasacreditadas +  ", creditosdebitados=" + this.creditosDebitadosToString() + "]";
	}

 
 

 

public String creditosDebitadosToString() {
	
	String items = "[";
	if(this.getCreditosdebitados()  != null) {
	  for (VentasReciboCreditoDebitado    item : this.getCreditosdebitados()) {
			
		items =	items.concat("[id=" + item.getId());
		items =	items.concat(", importe_credito="+ item.getImportedebito().toString());
		items =	items.concat(", orden_credito="+	item.getCredito().toString());
		items =	items.concat(", orden_debito="+	item.getDebito().toString());
		items =	items.concat("],");
	  }
	
	  items = items.concat("]");
	}
	
	return items;
}


public String lineasToString() {
	
	String items = "[";
	
	for (Item item : this.getLineas()) {
		
			
		items =	items.concat("[id=" + item.getProducto().getId().toString());
		items =	items.concat(", nombre="+ item.getProducto().getNombre().toString());
		items =	items.concat(", cantidad="+	item.getCantidad().toString());
		items =	items.concat(", precio="+	item.getPrecio().toString());
		items =	items.concat(", descuento="+	item.getDescuento().toString());
		items =	items.concat("],");
	}
	
	items = items.concat("]");
	
	return items;
}

 
	
	
}
