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
 

@Entity
@Table(name = "ventas_ordenes_de_entrega")
public class VentasPresupuesto  implements Serializable, Documento {

  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@NotNull
	private String prefijo;

	//@NotNull
	private Long numero;

	@Column(columnDefinition = "BOOLEAN")
	private Boolean anulada;

	@Column(name = "saldo_pendiente")
	@Digits(integer=12, fraction=2)
	private BigDecimal saldopendiente;

	@Digits(integer=12, fraction=2)
	private BigDecimal importe;

 	private String observacion;
	
	private String letra;
	
	private  String documento;
	
	private String detalle;
	
	private String condventa;
	 
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference(value="cliente-entrega")
	private Cliente cliente;
	


	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "orden_entrega_id")
	private List<ItemVentasPresupuesto> items;
	 
 	private static final long serialVersionUID = 1L;

	public VentasPresupuesto() {
		this.items = new ArrayList<ItemVentasPresupuesto>();
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

	public List<ItemVentasPresupuesto> getItems() {
		return items;
	}

	public void setItems(List<ItemVentasPresupuesto> items) {
		this.items = items;
	}

	public void addItemVentasPresupuesto(ItemVentasPresupuesto item) {
		this.items.add(item);
	}

 
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

  
	public String getCondventa() {
		return condventa;
	}

	public void setCondventa(String condventa) {
		this.condventa = condventa;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
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
	
 
	@Override
	public Double getAlicuotaiva() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlicuotaiva(Double alicuotaiva) {
		// TODO Auto-generated method stub
		
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
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

	
	public BigDecimal getImportetotal() {
		
		return this.getImporte();
	}

	@Override
	public void setImportetotal(BigDecimal importetotal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Item> getLineas() {
		
		List<Item> lineas = new  ArrayList<Item>();
		
		if(this.getItems() != null) {
			for(ItemVentasPresupuesto item: this.getItems() ) {
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
	public VentasPresupuesto getOrdenentrega() {
		 
		return null;
	}

	@Override
	public String toString() {
		return "OrdenDeEntrega [id=" + id + ", prefijo=" + prefijo + ", numero=" + numero + ", anulada=" + anulada
				+ ", saldopendiente=" + saldopendiente + ", importe=" + importe + ", observacion=" + observacion
				+ ", letra=" + letra + ", documento=" + documento + ", detalle=" + detalle + ", condventa=" + condventa
				+ ", fecha=" + fecha + ", createAt=" + createAt + ", cliente=" + cliente + ", items=" + this.lineasToString() + "]";
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
