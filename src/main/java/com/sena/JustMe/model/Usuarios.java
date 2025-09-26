package com.sena.JustMe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idusuarios;

	private String nombre;
	private String apellido;
	private String email;
	private String numero;
	private String fotoperfil;
	private String contrasena;

	private String direccion;
	private String biografia;
	private String documentos;
	private String portafolio;
	private String estado;
	private String disponibilidad;
	

	@ManyToOne
	@JoinColumn(name = "rol_idrol", referencedColumnName = "idrol")
	private Rol rol;


	public Usuarios() {
	}

	public Usuarios(Integer id, String nombre, String apellido, String email, String contrasena, String direccion,
			String biografia, String documentos, String portafolio, String estado, String disponibilidad, Rol rol, String numero) {
		this.idusuarios = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contrasena = contrasena;
		this.direccion = direccion;
		this.biografia = biografia;
		this.documentos = documentos;
		this.portafolio = portafolio;
		this.estado = estado;
		this.disponibilidad = disponibilidad;
		this.rol = rol;
		this.numero = numero;
	}

	// GETTERS AND SETTERS
	public Integer getId() {
		return idusuarios;
	}

	public void setId(Integer id) {
		this.idusuarios = id;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public void setNumero (String numero) {
		this.numero = numero;
	}
	
	public String getNumero () {
		return numero;
	}
	public String getFotoperfil() {
	    return fotoperfil;
	}

	public void setFotoperfil(String fotoperfil) {
	    this.fotoperfil = fotoperfil;
	}

	@Override
	public String toString() {
		return "Usuarios [idusuarios=" + idusuarios + ", nombre=" + nombre + ", apellido=" + apellido + ", email="
				+ email + ", numero=" + numero + ", contrasena=" + contrasena + ", direccion=" + direccion
				+ ", biografia=" + biografia + ", documentos=" + documentos + ", portafolio=" + portafolio + ", estado="
				+ estado + ", disponibilidad=" + disponibilidad + ", rol=" + rol + "]";
	}
	
	


}
