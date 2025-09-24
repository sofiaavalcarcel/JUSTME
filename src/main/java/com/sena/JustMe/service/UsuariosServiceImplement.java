package com.sena.JustMe.service;

import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImplement implements IUsuariosService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Usuarios save(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuarios> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuarios> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuarios> findAll() {
        return usuarioRepository.findAll();
    }
}