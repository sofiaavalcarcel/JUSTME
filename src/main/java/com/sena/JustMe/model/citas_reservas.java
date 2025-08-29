package com.sena.JustMe.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "citas_reservas")
public class citas_reservas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private Date fecha_hora;
	private String direccion_servicio;
	private String estado_cita;
	private String precio;
	private String observacionesCL;
	private String observacionesLB;
	private String fechaEdicion;
	
	public citas_reservas() {
	}

	
	public citas_reservas(Integer id, Date fecha_hora, String direccion_servicio, String estado_cita, String precio,
			String observacionesCL, String observacionesLB, String fechaEdicion) {
		super();
		this.id = id;
		this.fecha_hora = fecha_hora;
		this.direccion_servicio = direccion_servicio;
		this.estado_cita = estado_cita;
		this.precio = precio;
		this.observacionesCL = observacionesCL;
		this.observacionesLB = observacionesLB;
		this.fechaEdicion = fechaEdicion;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getFecha_hora() {
		return fecha_hora;
	}


	public void setFecha_hora(Date fecha_hora) {
		this.fecha_hora = fecha_hora;
	}


	public String getDireccion_servicio() {
		return direccion_servicio;
	}


	public void setDireccion_servicio(String direccion_servicio) {
		this.direccion_servicio = direccion_servicio;
	}


	public String getEstado_cita() {
		return estado_cita;
	}


	public void setEstado_cita(String estado_cita) {
		this.estado_cita = estado_cita;
	}


	public String getPrecio() {
		return precio;
	}


	public void setPrecio(String precio) {
		this.precio = precio;
	}


	public String getObservacionesCL() {
		return observacionesCL;
	}


	public void setObservacionesCL(String observacionesCL) {
		this.observacionesCL = observacionesCL;
	}


	public String getObservacionesLB() {
		return observacionesLB;
	}


	public void setObservacionesLB(String observacionesLB) {
		this.observacionesLB = observacionesLB;
	}


	public String getFechaEdicion() {
		return fechaEdicion;
	}


	public void setFechaEdicion(String fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}


	@Override
	public String toString() {
		return "citas_reservas [id=" + id + ", fecha_hora=" + fecha_hora + ", direccion_servicio=" + direccion_servicio
				+ ", estado_cita=" + estado_cita + ", precio=" + precio + ", observacionesCL=" + observacionesCL
				+ ", observacionesLB=" + observacionesLB + ", fechaEdicion=" + fechaEdicion + "]";
	}
	
	
	
	


}
