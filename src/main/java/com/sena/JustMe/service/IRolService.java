package com.sena.JustMe.service;

import java.util.List;
import java.util.Optional;

import com.sena.JustMe.model.Rol;

public interface IRolService {
    Rol save(Rol rol);
    Optional<Rol> findById(Integer id);
    Optional<Rol> findByNombre(String nombre);
    List<Rol> findAll();
    void delete(Integer id);
}
