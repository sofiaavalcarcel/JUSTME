package com.sena.JustMe.service;

import com.sena.JustMe.model.Rol;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.repository.IUsuarioRepository;
import com.sena.JustMe.repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImplement implements IUsuariosService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public Usuarios save(Usuarios usuario) {
        // Buscar el rol con ID = 3 (ejemplo: CLIENTE)
        Rol rolCliente = rolRepository.findById(3)
                .orElseThrow(() -> new RuntimeException("El rol con ID=3 no existe en la BD"));

        // Asignar rol automáticamente
        usuario.setRol(rolCliente);

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
