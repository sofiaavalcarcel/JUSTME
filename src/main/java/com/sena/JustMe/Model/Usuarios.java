package com.sena.JustMe.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuarios {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	private String nombre;
	private String apellido;
	private String email;
	private String contraseña;
	private String direccion;
	private String biografia;
	private String documentos;
	private String portafolio;
	private String estado;
	private String disponibilidad;

	// constructor vacio
	public Usuarios() {

	}

//constructor con datos
	public Usuarios(Integer id, String nombre, String apellido, String email, String contraseña, String direccion,
			String biografia, String documentos, String portafolio, String estado, String disponibilidad,
			Integer idrol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contraseña = contraseña;
		this.direccion = direccion;
		this.biografia = biografia;
		this.documentos = documentos;
		this.portafolio = portafolio;
		this.estado = estado;
		this.disponibilidad = disponibilidad;

	}

	// GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getDocumentos() {
		return documentos;
	}

	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	public String getPortafolio() {
		return portafolio;
	}

	public void setPortafolio(String portafolio) {
		this.portafolio = portafolio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	// metodo TOSTRING
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
				+ ", contraseña=" + contraseña + ", direccion=" + direccion + ", biografia=" + biografia
				+ ", documentos=" + documentos + ", portafolio=" + portafolio + ", estado=" + estado
				+ ", disponibilidad=" + disponibilidad + "]";
	}

}
