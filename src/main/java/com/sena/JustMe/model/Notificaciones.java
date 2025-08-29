package com.sena.JustMe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notificaciones")
public class Notificaciones {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idnotificaciones;
	private String tipo_notificiaciones;
	private String mensaje;

	// Constructo vacio
	public Notificaciones() {

	}

	// constructor con campos

	public Notificaciones(Integer idnotificaciones, String tipo_notificiaciones, String mensaje) {
		super();
		this.idnotificaciones = idnotificaciones;
		this.tipo_notificiaciones = tipo_notificiaciones;
		this.mensaje = mensaje;
	}

	// getters and setters

	public Integer getIdnotificaciones() {
		return idnotificaciones;
	}

	public void setIdnotificaciones(Integer idnotificaciones) {
		this.idnotificaciones = idnotificaciones;
	}

	public String getTipo_notificiaciones() {
		return tipo_notificiaciones;
	}

	public void setTipo_notificiaciones(String tipo_notificiaciones) {
		this.tipo_notificiaciones = tipo_notificiaciones;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	// metodo to string

	@Override
	public String toString() {
		return "Notificaciones [idnotificaciones=" + idnotificaciones + ", tipo_notificiaciones=" + tipo_notificiaciones
				+ ", mensaje=" + mensaje + "]";
	}

}
