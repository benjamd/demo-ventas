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

@Entity
@Table(name = "ventas_devoluciones")
public class VentasDevolucion  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@NotNull
	private String prefijo;

	//@NotNull
	private Long numero;

	@Column(columnDefinition = "BOOLEAN")
	private Boolean anulada;

	@Column(name = "cantidad_total")
	@Digits(integer=12, fraction=2)
	private BigDecimal cantidadtotal;

	private String observacion;
	
	private String letra;
	
	private Integer lineas;
	
	private String detalle;
	
	private String documento;
	
	
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "devolucion_id")
	private List<ItemVentasDevolucion> items;
	
	
	
 
 
	private static final long serialVersionUID = 1L;

	public  VentasDevolucion() {
		
		this.items = new ArrayList<ItemVentasDevolucion>();
	} 
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
		fecha = new Date();
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

	public List<ItemVentasDevolucion> getItems() {
		return items;
	}

	public void setItems(List<ItemVentasDevolucion> items) {
		this.items = items;
	}

	public void addItemDevolucionVentas(ItemVentasDevolucion item) {
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

	public Double totalMasIva() {

		return this.getSubTotal() * 1.21;
	}

	public Double importeIva() {

		return this.getSubTotal() * 0.21;
	}
*/
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCantidadtotal() {
		return cantidadtotal;
	}

	public void setCantidadtotal(BigDecimal cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Integer getLineas() {
		return lineas;
	}

	public void setLineas(Integer lineas) {
		this.lineas = lineas;
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


}
