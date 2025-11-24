package com.sena.JustMe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.JustMe.model.Usuarios;

public interface IUsuarioRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByEmail(String email);
    Optional<Usuarios> findByEmailOrNumero(String email, String numero);
}
