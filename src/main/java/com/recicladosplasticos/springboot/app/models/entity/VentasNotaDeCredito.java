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

@Entity
@Table(name = "ventas_notas_de_credito")
public class VentasNotaDeCredito implements Serializable, Documento {

 
 
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

	private String letra;
	
	private  String documento;

	private String observacion;
	
	private String detalle;
	
	private String condventa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference(value="cliente-credito")
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "nota_de_credito_id")
	private List<ItemVentasNotaDeCredito> items;
	
	
	
	
 	private static final long serialVersionUID = 1L;

 	
 	
	public VentasNotaDeCredito() {
		this.items = new ArrayList<ItemVentasNotaDeCredito>();
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

	public List<ItemVentasNotaDeCredito> getItems() {
		return items;
	}

	public void setItems(List<ItemVentasNotaDeCredito> items) {
		this.items = items;
	}

	public void addItemCredito(ItemVentasNotaDeCredito item) {
		this.items.add(item);
	}
/*
	public Double getSubTotal() {
		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {
			total += items.get(i).calcularImporte();
		}
		return total;
	}
*/
	public Double totalMasIva() {

		return this.getSubTotal() * 1.21;
	}

	public Double importeIva() {

		return this.getSubTotal() * 0.21;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

 

	public Double getAlicuotaiva() {
		return alicuotaiva;
	}


	public void setAlicuotaiva(Double alicuotaiva) {
		this.alicuotaiva = alicuotaiva;
	}

	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalle) {
		this.detalle = detalle;
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


	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}


	@Override
	public Double getSubTotal() {
		 
		return null;
	}


	@Override
	@JsonIgnore
	public List<Item> getLineas() {
		List<Item> lineas = new  ArrayList<Item>();
		
		if(this.getItems() != null) {
			for(ItemVentasNotaDeCredito item: this.getItems() ) {
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
		return "NotaDeCredito [id=" + id + ", prefijo=" + prefijo + ", numero=" + numero + ", anulada=" + anulada
				+ ", saldopendiente=" + saldopendiente + ", importeiva=" + importeiva + ", importe=" + importe
				+ ", importetotal=" + importetotal + ", alicuotaiva=" + alicuotaiva + ", letra=" + letra
				+ ", documento=" + documento + ", observacion=" + observacion + ", detalle=" + detalle + ", condventa="
				+ condventa + ", createAt=" + createAt + ", fecha=" + fecha + ", cliente=" + cliente + ", items="
				+ this.lineasToString() + "]";
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
