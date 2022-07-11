package com.recicladosplasticos.springboot.app.models.entity;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
 
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
 

import org.springframework.format.annotation.DateTimeFormat;

 
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ventas_clientes")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	//@NotNull(message =  "Código no puede ser un campo nulo")
	@Min(1)
	private Long codigo;
	
	@NotEmpty(message = "Nombre no puede ser un campo vacío")
	private String nombre;

	@NotEmpty(message = "Razón Social no puede ser un campo vacío")
	@Column(name = "razon_social")
	private String razonsocial;

	@NotEmpty(message = "Email no puede ser un campo vacío")
	//@Email(message = "Email no tiene el formato de correo ej.: usuario@dominio.com")
	private String email;

	//@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	@NotEmpty(message = "Actividad no puede ser un campo vacío")
	private String actividad;
	
	@NotEmpty(message = "Cuit no puede ser un campo vacío")
	private String cuit;
	
	//@NotEmpty(message = "Iva no puede ser un campo vacío")
	private String iva;
	
	@NotEmpty(message = "Dirección no puede ser un campo vacío")
	private String direccion;
	
	@NotEmpty(message = "Codigo Postal no puede ser un campo vacío")
	@Column(name = "codigo_postal")
	private String codigopostal;
	
	@NotEmpty(message = "Localidad no puede ser un campo vacío")
	private String localidad;
	
	@NotEmpty(message = "Partido no puede ser un campo vacío")
	private String partido;
	 
	private String provincia;
	
 	private String pais;
	
	@NotEmpty(message = "Contacto no puede ser un campo vacío")
	private String contacto;
	
	@NotEmpty(message = "Teléfono, int. o Celular no puede ser un campo vacío")
	private String telefono;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VentasFactura> ventasFacturas;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference(value="cliente-entrega")
	private List<VentasPresupuesto> ordenesEntrega;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference(value="cliente-credito")
	private List<VentasNotaDeCredito> notasDeCredito;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference(value="cliente-debito")
	private List<VentasNotaDeDebito> notasDedebito;

	private static final long serialVersionUID = 1L;

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	public List<VentasPresupuesto> getOrdenesEntrega() {
		return ordenesEntrega;
	}

	public void setOrdenesEntrega(List<VentasPresupuesto> ordenesEntrega) {
		this.ordenesEntrega = ordenesEntrega;
	}

	public Cliente() {
		ventasFacturas = new ArrayList<VentasFactura>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonsocial() {
		return razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

 	public List<VentasFactura> getFacturas() {
		return ventasFacturas;
	}

	public void setFacturas(List<VentasFactura> ventasFacturas) {
		this.ventasFacturas = ventasFacturas;
	}

	public void addFactura(VentasFactura ventasFactura) {
		ventasFacturas.add(ventasFactura);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return nombre + " " + razonsocial;
	}

	public String getActividad() {
		return actividad;
	}



	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigopostal() {
		return codigopostal;
	}

	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<VentasNotaDeCredito> getNotasDeCredito() {
		return notasDeCredito;
	}

	public void setNotasDeCredito(List<VentasNotaDeCredito> notasDeCredito) {
		this.notasDeCredito = notasDeCredito;
	}

	public List<VentasNotaDeDebito> getNotasDedebito() {
		return notasDedebito;
	}

	public void setNotasDedebito(List<VentasNotaDeDebito> notasDedebito) {
		this.notasDedebito = notasDedebito;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
 
	
	public String toStringCliente() {
		return "Cliente [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", razonsocial=" + razonsocial
				+ ", email=" + email + ", createAt=" + createAt + ", actividad=" + actividad + ", cuit=" + cuit
				+ ", iva=" + iva + ", direccion=" + direccion + ", codigopostal=" + codigopostal + ", localidad="
				+ localidad + ", partido=" + partido + ", provincia=" + provincia + ", pais=" + pais + ", contacto="
				+ contacto + ", telefono=" + telefono + "]";
	}

	
}
