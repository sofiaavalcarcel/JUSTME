package com.sena.JustMe.service;

import java.util.List;
import java.util.Optional;

import com.sena.JustMe.model.Citas;

public interface ICitasService {

    List<Citas> listarCitas();

    Optional<Citas> buscarPorId(Integer id);

    Citas guardar(Citas cita);

    // Citas de un cliente
    List<Citas> listarCitasPorCliente(Integer idUsuario);

    // Citas de un profesional
    List<Citas> listarCitasPorProfesional(Integer idProfesional);
}
