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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 
@Entity
@Table(name = "ventas_facturas")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VentasFactura  implements Serializable, Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@NotNull
	private String prefijo;

	//@NotNull
	private Long numero;
	
	private  String documento;

	@Column(columnDefinition = "BOOLEAN")
	private Boolean anulada;

	@Column(name = "saldo_pendiente")
	@Digits(integer=12, fraction=2)
	private BigDecimal saldopendiente;
	
	
	@Column(name = "importe_iva")
	@Digits(integer=12, fraction=2)
	private BigDecimal importeiva;

	@Digits(integer=12, fraction=2)
	private BigDecimal importe;

	@Column(name = "importe_total")
	@Digits(integer=12, fraction=2)
	private BigDecimal importetotal;
	
	@Column(name = "alicuota_iva")
	private Double alicuotaiva;

	private String observacion;
	
	private String letra;
	
	private String detalle;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(name = "condicion_de_venta")
	private String condventa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	private List<ItemVentasFactura> items;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	private List<ItemVentasFacturaCredito> itemscreditos;
	
	public List<ItemVentasFacturaCredito> getItemscreditos() {
		return itemscreditos;
	}

	public void setItemscreditos(List<ItemVentasFacturaCredito> itemscreditos) {
		this.itemscreditos = itemscreditos;
	}


 	private static final long serialVersionUID = 1L;

	public VentasFactura() {
		this.items = new ArrayList<ItemVentasFactura>();
		this.itemscreditos = new ArrayList<ItemVentasFacturaCredito>();
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
		anulada = false;
 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

 	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemVentasFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemVentasFactura> items) {
		this.items = items;
	}

	public void addItemFactura(ItemVentasFactura item) {
		this.items.add(item);
	}

	public void addItemFacturaCredito(ItemVentasFacturaCredito item) {
		this.itemscreditos.add(item);
	}
	
 
	//TODO REVISAR: CAMBIAR A BIGDECIMAL?
	public Double getSubTotal() {
		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {
			total += items.get(i).calcularImporte().doubleValue();
		}
		return total;
	}

	public Double totalMasIva() {

		return this.getSubTotal() * 1.21;
	}

	public Double importeIva() {

		return this.getSubTotal() * 0.21;
	}
 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Boolean getAnulada() {
		return anulada;
	}

	public void setAnulada(Boolean anulada) {
		this.anulada = anulada;
	}
 
 	public BigDecimal getSaldopendiente() {
		return saldopendiente;
	}

	public void setSaldopendiente(BigDecimal saldopendiente) {
		this.saldopendiente = saldopendiente;
	}

	public BigDecimal getImporteiva() {
		return importeiva;
	}

	public void setImporteiva(BigDecimal importeiva) {
		this.importeiva = importeiva;
	}

	public BigDecimal getImportetotal() {
		return importetotal;
	}

	public void setImportetotal(BigDecimal importetotal) {
		this.importetotal = importetotal;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
 
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Double getAlicuotaiva() {
		return alicuotaiva;
	}

	public void setAlicuotaiva(Double alicuotaiva) {
		this.alicuotaiva = alicuotaiva;
	}

	public String getCondventa() {
		return condventa;
	}

	public void setCondventa(String condventa) {
		this.condventa = condventa;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@JsonIgnore
	public List<Item> getLineas() {
		
		List<Item> lineas = new  ArrayList<Item>();
		
		if(this.getItems() != null) {
			for(ItemVentasFactura item: this.getItems() ) {
				Item itemLinea = new Item();
				itemLinea.setProducto(item.getProducto());
				itemLinea.setPrecio(item.getPrecio());
				itemLinea.setCantidad(item.getCantidad());
				itemLinea.setDescuento(item.getDescuento());
				lineas.add(itemLinea);
			}
		}
		return lineas;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", prefijo=" + prefijo + ", numero=" + numero + ", documento=" + documento
				+ ", anulada=" + anulada + ", saldopendiente=" + saldopendiente + ", importeiva=" + importeiva
				+ ", importe=" + importe + ", importetotal=" + importetotal + ", alicuotaiva=" + alicuotaiva
				+ ", observacion=" + observacion + ", letra=" + letra + ", detalle=" + detalle + ", fecha=" + fecha
				+ ", condventa=" + condventa + ", createAt=" + createAt + ", cliente=" + cliente + ", items=" 
				+ this.lineasToString() + ", itemscreditos=" + itemscreditos + "]";
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

	@Override
	public VentasPresupuesto getOrdenentrega() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
