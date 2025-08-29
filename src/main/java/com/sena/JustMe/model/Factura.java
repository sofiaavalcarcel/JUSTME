package com.sena.JustMe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "factura")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idfactura;
	private String num_pago;
	private String otros_detalles;
	
	@ManyToOne
	private Modopago modopago;
	
	@ManyToOne
	private Usuarios usuarios;
	
	@OneToOne(mappedBy = "factura")
    private Citas_reservas citas_reservas;

	// constructor vacio
	public Factura() {

	}

	// constructor con campos

	public Factura(Integer idfactura, String num_pago, String otros_detalles) {
		super();
		this.idfactura = idfactura;
		this.num_pago = num_pago;
		this.otros_detalles = otros_detalles;
	}
	// getters and setters

	public Integer getIdfactura() {
		return idfactura;
	}

	public void setIdfactura(Integer idfactura) {
		this.idfactura = idfactura;
	}

	public String getNum_pago() {
		return num_pago;
	}

	public void setNum_pago(String num_pago) {
		this.num_pago = num_pago;
	}

	public String getOtros_detalles() {
		return otros_detalles;
	}

	public void setOtros_detalles(String otros_detalles) {
		this.otros_detalles = otros_detalles;
	}

	// Metodo to string

	@Override
	public String toString() {
		return "Factura [idfactura=" + idfactura + ", num_pago=" + num_pago + ", otros_detalles=" + otros_detalles
				+ "]";
	}

}
