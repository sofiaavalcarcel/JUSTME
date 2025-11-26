package com.sena.JustMe.service;

import java.util.List;
import java.util.Optional;

import com.sena.JustMe.model.Citas_reservas;

public interface ICitas_reservasService {
	List<Citas_reservas> listarcitas();

	Optional<Citas_reservas> buscarPorId(Integer id);

	Citas_reservas guardar(Citas_reservas cita);

	List<Citas_reservas> listarcitasPorUsuario(Integer idUsuario);

	List<Citas_reservas> listarcitasPorCliente(Integer idUsuario);

}
