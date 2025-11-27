package com.sena.JustMe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.JustMe.model.Pagos;

public interface IPagosRepository extends JpaRepository<Pagos, Integer> {

    List<Pagos> findByUsuarioIdusuarios(Integer idusuarios);

    // Listar todos los pagos ordenados por fecha de creación descendente
    List<Pagos> findAllByOrderByFechaCreacionDesc();
}
