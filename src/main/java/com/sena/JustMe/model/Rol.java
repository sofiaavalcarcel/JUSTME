package com.sena.JustMe.model;

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
	private String nombres;
	//Constructor vacio
	public Rol() {
		
	}
	
	//Constructor con campos
	public Rol(String idrol, String nombres) {
		super();
		this.idrol = idrol;
		this.nombres = nombres;
	}

	//GETTERS Y SETTERS
	public String getIdrol() {
		return idrol;
	}

	public void setIdrol(String idrol) {
		this.idrol = idrol;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	//Metodo to string
	@Override
	public String toString() {
		return "Rol [idrol=" + idrol + ", nombres=" + nombres + "]";
	}
	
	
	
	
	
	
	
}
