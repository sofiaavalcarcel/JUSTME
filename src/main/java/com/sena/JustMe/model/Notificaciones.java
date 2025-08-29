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
	private Integer id;
	private String tipo_notificiaciones;
	private String mensaje;
	
	//Constructo vacio
	public Notificaciones() {
		
	}

	//constructor con campos
	public Notificaciones(Integer id, String tipo_notificiaciones, String mensaje) {
		super();
		this.id = id;
		this.tipo_notificiaciones = tipo_notificiaciones;
		this.mensaje = mensaje;
	}

	//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	//metodo to string
	@Override
	public String toString() {
		return "Notificaciones [id=" + id + ", tipo_notificiaciones=" + tipo_notificiaciones + ", mensaje=" + mensaje
				+ "]";
	}
	
	
	
	
	
	
}
