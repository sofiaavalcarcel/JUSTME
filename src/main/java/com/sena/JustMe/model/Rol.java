package com.sena.JustMe.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String idrol;
	private String nombre;
	

	// Constructor vacio
	public Rol() {

	}

	// Constructor con campos
	public Rol(String idrol, String nombre) {
		super();
		this.idrol = idrol;
		this.nombre = nombre;
	}

	// GETTERS Y SETTERS
	public String getIdrol() {
		return idrol;
	}

	public void setIdrol(String idrol) {
		this.idrol = idrol;
	}

	public String getNombres() {
		return nombre;
	}

	public void setNombres(String nombre) {
		this.nombre = nombre;
	}

	// Metodo to string
	@Override
	public String toString() {
		return "Rol [idrol=" + idrol + ", nombre=" + nombre + "]";
	}

}
