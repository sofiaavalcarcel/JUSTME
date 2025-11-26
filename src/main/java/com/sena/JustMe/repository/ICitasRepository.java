package com.sena.JustMe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.JustMe.model.Citas;

@Repository
public interface ICitasRepository extends JpaRepository<Citas, Integer> {

    // Buscar citas de un cliente (Usuario que reserva)
    List<Citas> findByUsuario_Idusuarios(Integer idUsuario);

    // Buscar citas de un profesional (A través del servicio)
    List<Citas> findByServicio_Usuario_Idusuarios(Integer idProfesional);
}
