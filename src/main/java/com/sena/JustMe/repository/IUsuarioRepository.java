package com.sena.JustMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.JustMe.model.Usuarios;

public interface IUsuarioRepository extends JpaRepository<Usuarios, Integer> {
	// Aquí puedes agregar métodos personalizados si los necesitas
}
