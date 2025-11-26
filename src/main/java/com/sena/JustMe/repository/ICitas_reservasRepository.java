package com.sena.JustMe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.JustMe.model.Citas_reservas;

public interface ICitas_reservasRepository extends JpaRepository<Citas_reservas, Integer> {

	List<Citas_reservas> findByServicio_Usuario_Idusuarios(Integer idUsuario);

	List<Citas_reservas> findByUsuario_Idusuarios(Integer idUsuario);
}
