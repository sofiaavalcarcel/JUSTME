package com.sena.JustMe.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;

@Entity
@Table(name = "citas_reservas")
public class Citas_reservas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcitas_reservas;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date fecha_hora;

	private String direccion_servicio;
	private String estado_cita;
	private String precio;
	private String observacionesCl;
	private String observacionesLb;
	private String fechaEdicion;

	// 🔹 Relación con Servicios (FK correcta)
	@ManyToOne
	@JoinColumn(name = "idservicio", referencedColumnName = "idservicios")
	private Servicios servicio;

	// 🔹 Constructor vacío
	public Citas_reservas() {
	}

	// 🔹 Constructor con parámetros
	public Citas_reservas(Integer idcitas_reservas, Date fecha_hora, String direccion_servicio, String estado_cita,
			String precio, String observacionesCl, String observacionesLb, String fechaEdicion, Servicios servicio) {
		this.idcitas_reservas = idcitas_reservas;
		this.fecha_hora = fecha_hora;
		this.direccion_servicio = direccion_servicio;
		this.estado_cita = estado_cita;
		this.precio = precio;
		this.observacionesCl = observacionesCl;
		this.observacionesLb = observacionesLb;
		this.fechaEdicion = fechaEdicion;
		this.servicio = servicio;
	}

	// 🔹 Getters y Setters
	public Integer getIdcitas_reservas() {
		return idcitas_reservas;
	}

	public void setIdcitas_reservas(Integer idcitas_reservas) {
		this.idcitas_reservas = idcitas_reservas;
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

	public String getObservacionesCl() {
		return observacionesCl;
	}

	public void setObservacionesCl(String observacionesCl) {
		this.observacionesCl = observacionesCl;
	}

	public String getObservacionesLb() {
		return observacionesLb;
	}

	public void setObservacionesLb(String observacionesLb) {
		this.observacionesLb = observacionesLb;
	}

	public String getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(String fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	// 🔹 Getter y Setter de la relación con Servicio
	public Servicios getServicio() {
		return servicio;
	}

	public void setServicio(Servicios servicio) {
		this.servicio = servicio;
	}

	// ✅ Alias estándar para compatibilidad (por si en el controller se usa getId)
	public Integer getId() {
		return idcitas_reservas;
	}

	public void setId(Integer id) {
		this.idcitas_reservas = id;
	}

	@Override
	public String toString() {
		return "Citas_reservas [idcitas_reservas=" + idcitas_reservas + ", fecha_hora=" + fecha_hora
				+ ", direccion_servicio=" + direccion_servicio + ", estado_cita=" + estado_cita + ", precio=" + precio
				+ ", observacionesCl=" + observacionesCl + ", observacionesLb=" + observacionesLb + ", fechaEdicion="
				+ fechaEdicion + ", servicio=" + (servicio != null ? servicio.getId() : "null") + "]";
	}
}
