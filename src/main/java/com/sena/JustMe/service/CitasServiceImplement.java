package com.sena.JustMe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.JustMe.model.Citas;
import com.sena.JustMe.repository.ICitasRepository;

@Service
public class CitasServiceImplement implements ICitasService {

    @Autowired
    private ICitasRepository repository;

    @Override
    public List<Citas> listarCitas() {
        return repository.findAll();
    }

    @Override
    public Optional<Citas> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Citas guardar(Citas cita) {
        return repository.save(cita);
    }

    @Override
    public List<Citas> listarCitasPorCliente(Integer idUsuario) {
        return repository.findByUsuario_Idusuarios(idUsuario);
    }

    @Override
    public List<Citas> listarCitasPorProfesional(Integer idProfesional) {
        return repository.findByServicio_Usuario_Idusuarios(idProfesional);
    }
}
