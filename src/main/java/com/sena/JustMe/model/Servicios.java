package com.sena.JustMe.model;
import jakarta.persistence.*;
@Entity
@Table(name = "servicios")
public class Servicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idservicios;

    private String nombre_servicios;
    private String descripcion;
    private Double precio_base;
    private String categoria;
    private String estado;
    private String imagen;


    public Servicios() {

	}


	public Servicios(Integer idservicios, String nombre_servicios, String descripcion, Double precio_base,
			String categoria, String estado, String imagen) {
		super();
		this.idservicios = idservicios;
		this.nombre_servicios = nombre_servicios;
		this.descripcion = descripcion;
		this.precio_base = precio_base;
		this.categoria = categoria;
		this.estado = estado;
		this.imagen = imagen;
	}


	public Integer getIdservicios() {
		return idservicios;
	}


	public void setIdservicios(Integer idservicios) {
		this.idservicios = idservicios;
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


	public Double getPrecio_base() {
		return precio_base;
	}


	public void setPrecio_base(Double precio_base) {
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


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	@Override
	public String toString() {
		return "Servicios [idservicios=" + idservicios + ", nombre_servicios=" + nombre_servicios + ", descripcion="
				+ descripcion + ", precio_base=" + precio_base + ", categoria=" + categoria + ", estado=" + estado
				+ ", imagen=" + imagen + "]";
	}
    
    

}