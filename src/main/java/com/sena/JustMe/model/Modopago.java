package com.sena.JustMe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "modopago")
public class Modopago {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer idmodopago;
	private String metodo_pago;

	// constructor sin campos
	public Modopago() {

	}

	// construcor con campos

	public Modopago(Integer idmodopago, String metodo_pago) {
		super();
		this.idmodopago = idmodopago;
		this.metodo_pago = metodo_pago;
	}

	// getters and setters
	public Integer getIdmodopago() {
		return idmodopago;
	}

	public void setIdmodopago(Integer idmodopago) {
		this.idmodopago = idmodopago;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	// metodo toString

	@Override
	public String toString() {
		return "Modopago [idmodopago=" + idmodopago + ", metodo_pago=" + metodo_pago + "]";
	}

}
