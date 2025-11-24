package com.sena.JustMe.service;

import com.sena.JustMe.model.Usuarios;

import java.util.List;
import java.util.Optional;

public interface IUsuariosService {
    
    Usuarios save(Usuarios usuario);

    Optional<Usuarios> findById(Integer id);

    void delete(Integer id);

    Optional<Usuarios> findByEmail(String email);

    List<Usuarios> findAll();

    void changePassword(Integer id, String currentPassword, String newPassword);
}
