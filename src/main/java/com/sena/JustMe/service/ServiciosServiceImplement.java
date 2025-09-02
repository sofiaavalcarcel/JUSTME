package com.sena.JustMe.service;

import com.sena.JustMe.model.Servicios;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosServiceImplement implements IServiciosService {

    private final com.sena.JustMe.repository.IServiciosRepository serviciosRepository;

    public ServiciosServiceImplement(com.sena.JustMe.repository.IServiciosRepository serviciosRepository) {
        this.serviciosRepository = serviciosRepository;
    }

    @Override
    public List<Servicios> listarServicios() {
        return serviciosRepository.findAll();
    }
}
