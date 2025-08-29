package com.sena.JustMe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "factura")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String num_pago;
	private String otros_detalles;
	
	//constructor vacio
	public Factura() {
	
	}

	//cosntructor con campos
	public Factura(Integer id, String num_pago, String otros_detalles) {
		super();
		this.id = id;
		this.num_pago = num_pago;
		this.otros_detalles = otros_detalles;
	}

	//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	//Metodo to string 
	@Override
	public String toString() {
		return "Factura [id=" + id + ", num_pago=" + num_pago + ", otros_detalles=" + otros_detalles + "]";
	}
	
	
	
	
	
	

	
}
