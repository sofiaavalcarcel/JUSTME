package com.sena.JustMe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reseña")
public class Reseña {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	private String calificacion;
	private String comentario;
	private String fecha_reseña;

	// contructor sin campos

	public Reseña() {
		super();
		// TODO Auto-generated constructor stub
	}

	// contructor con campos
	public Reseña(Integer id, String calificacion, String comentario, String fecha_reseña) {
		super();
		this.id = id;
		this.calificacion = calificacion;
		this.comentario = comentario;
		this.fecha_reseña = fecha_reseña;
	}

	// getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFecha_reseña() {
		return fecha_reseña;
	}

	public void setFecha_reseña(String fecha_reseña) {
		this.fecha_reseña = fecha_reseña;
	}

	
	//metodo to string
	@Override
	public String toString() {
		return "Reseña [id=" + id + ", calificacion=" + calificacion + ", comentario=" + comentario + ", fecha_reseña="
				+ fecha_reseña + "]";
	}
}
