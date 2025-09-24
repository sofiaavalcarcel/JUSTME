package com.sena.JustMe.service;

import com.sena.JustMe.model.Rol;
import com.sena.JustMe.repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImplement implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public Rol save(Rol rol) {
    	return rolRepository.save(rol);
    
    }

    @Override
    public Optional<Rol> findById(Integer id) {
        return rolRepository.findById(id);
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        rolRepository.deleteById(id);
    }
}
