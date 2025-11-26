package com.sena.JustMe.service;

import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<Citas_reservas> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Citas_reservas guardar(Citas_reservas cita) {
        return repository.save(cita);
    }

    // ✅ CORRECTO
    @Override
    public List<Citas_reservas> listarcitasPorUsuario(Integer idUsuario) {
        return repository.findByServicio_Usuario_Idusuarios(idUsuario);
    }

    @Override
    public List<Citas_reservas> listarcitasPorCliente(Integer idUsuario) {
        return repository.findByUsuario_Idusuarios(idUsuario);
    }
}
