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

    // 👇 ahora solo guardas el id del usuario
    @Column(name = "idusuarios")
    private Integer idusuarios;
    
    public Servicios() {

	}

	public Servicios(Integer idservicios, String nombre_servicios, String descripcion, Double precio_base,
			String categoria, String estado, Integer idusuarios) {
		super();
		this.idservicios = idservicios;
		this.nombre_servicios = nombre_servicios;
		this.descripcion = descripcion;
		this.precio_base = precio_base;
		this.categoria = categoria;
		this.estado = estado;
		this.idusuarios = idusuarios;
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

	public Integer getIdusuarios() {
		return idusuarios;
	}

	public void setIdusuarios(Integer idusuarios) {
		this.idusuarios = idusuarios;
	}

	@Override
	public String toString() {
		return "Servicios [idservicios=" + idservicios + ", nombre_servicios=" + nombre_servicios + ", descripcion="
				+ descripcion + ", precio_base=" + precio_base + ", categoria=" + categoria + ", estado=" + estado
				+ ", idusuarios=" + idusuarios + "]";
	}
    
}


