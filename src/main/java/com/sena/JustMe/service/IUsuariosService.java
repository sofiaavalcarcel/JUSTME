package com.sena.JustMe.service;

import java.util.List;

import com.sena.JustMe.model.Usuarios;

public interface IUsuariosService {

	List<Usuarios> listarUsuarios();

	Usuarios guardarUsuario(Usuarios usuario);

	Usuarios buscarPorId(Integer id);

	void eliminarUsuario(Integer id);
}
