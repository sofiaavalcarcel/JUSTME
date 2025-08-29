package com.sena.JustMe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
	@Table(name = "servicios")
	public class Servicios {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private String nombre_servicios;
	private String descripcion;
	private String precio_base;
	private String categoria;
	private String estado;

	
	//constructor vacio
	public Servicios() {
		
	}

	//contructor con campos
	public Servicios(Integer id, String nombre_servicios, String descripcion, String precio_base, String categoria,
			String estado) {
		super();
		this.id = id;
		this.nombre_servicios = nombre_servicios;
		this.descripcion = descripcion;
		this.precio_base = precio_base;
		this.categoria = categoria;
		this.estado = estado;
	}
	
	
	//getters and setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre_servicios() {
		return nombre_servicios;
	}

	public void setNombre_servicios(String nombre_servicios) {
		this.nombre_servicios = nombre_servicios;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPrecio_base() {
		return precio_base;
	}

	public void setPrecio_base(String precio_base) {
		this.precio_base = precio_base;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	//to string
	@Override
	public String toString() {
		return "Servicios [id=" + id + ", nombre_servicios=" + nombre_servicios + ", descripcion=" + descripcion
				+ ", precio_base=" + precio_base + ", categoria=" + categoria + ", estado=" + estado + "]";
	}
	

	
	
	
}
