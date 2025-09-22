package com.sena.JustMe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.JustMe.model.Citas_reservas;
import com.sena.JustMe.repository.ICitas_reservasRepository;

@Service
public class Citas_reservasServiceImplement implements ICitas_reservasService {

    private final ICitas_reservasRepository repository;

    public Citas_reservasServiceImplement(ICitas_reservasRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Citas_reservas> listarcitas() {
        return repository.findAll();
    }
    


}
