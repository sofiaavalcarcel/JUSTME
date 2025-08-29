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

	private Integer id;
	private String metodo_pago;

	// constructor sin campos
	public Modopago() {

	}

	//construcot con campos
	public Modopago(Integer id, String metodo_pago) {
		super();
		this.id = id;
		this.metodo_pago = metodo_pago;
	}

	//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return "Modopago [id=" + id + ", metodo_pago=" + metodo_pago + "]";
	}
	
	

}
