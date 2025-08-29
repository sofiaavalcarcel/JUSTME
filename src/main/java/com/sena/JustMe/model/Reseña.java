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

	private Integer idreseña;
	private String calificacion;
	private String comentario;
	private String fecha_reseña;

	// contructor sin campos

	public Reseña() {

	}

	// contructor con campos
	public Reseña(Integer idreseña, String calificacion, String comentario, String fecha_reseña) {
		super();
		this.idreseña = idreseña;
		this.calificacion = calificacion;
		this.comentario = comentario;
		this.fecha_reseña = fecha_reseña;
	}

	// getters and setters

	public Integer getIdreseña() {
		return idreseña;
	}

	public void setIdreseña(Integer idreseña) {
		this.idreseña = idreseña;
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

	// metodo to string
	@Override
	public String toString() {
		return "Reseña [idreseña=" + idreseña + ", calificacion=" + calificacion + ", comentario=" + comentario
				+ ", fecha_reseña=" + fecha_reseña + "]";
	}
}
