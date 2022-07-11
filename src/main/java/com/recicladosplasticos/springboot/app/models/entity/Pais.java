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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "paises")
public class Pais implements Serializable{

 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String codigo;
	@NotEmpty
	private String nombre;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	@NotNull
	private Date createAt;

	@NotNull
	@OneToMany(mappedBy = "pais" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Provincia> provincias;
	
	
	public Pais() {
		
		provincias = new ArrayList<Provincia>();

	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Provincia> getProvincias() {
		return provincias;
	}
	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void agregarProvincia(Provincia provincia) {
		provincias.add(provincia);
	}
	
	
}
