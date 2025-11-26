package com.sena.JustMe.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "citas")
public class Citas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date fechaHora;

    private String estado;
    private Double precio;
    private String direccion;

    // Relación con el Cliente (Usuario que reserva)
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "idusuarios")
    private Usuarios usuario;

    // Relación con el Servicio
    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "idservicios")
    private Servicios servicio;

    public Citas() {
    }

    public Citas(Integer idCita, Date fechaHora, String estado, Double precio, String direccion, Usuarios usuario,
            Servicios servicio) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.precio = precio;
        this.direccion = direccion;
        this.usuario = usuario;
        this.servicio = servicio;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
    }
}
