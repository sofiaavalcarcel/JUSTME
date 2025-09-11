package com.sena.JustMe.service;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.repository.IServiciosRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiciosServiceImplement implements IServiciosService {

    private final IServiciosRepository serviciosRepository;

    public ServiciosServiceImplement(IServiciosRepository serviciosRepository) {
        this.serviciosRepository = serviciosRepository;
    }

    @Override
    public List<Servicios> listarServicios() {
        return serviciosRepository.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        serviciosRepository.deleteById(id);
    }

    @Override
    public Optional<Servicios> buscarPorId(Integer id) {
        return serviciosRepository.findById(id);
    }

    @Override
    public Servicios editarServicio(Integer id, Servicios nuevosDatos) {
        return serviciosRepository.findById(id).map(servicio -> {
            servicio.setNombre_servicios(nuevosDatos.getNombre_servicios());
            servicio.setDescripcion(nuevosDatos.getDescripcion());
            servicio.setPrecio_base(nuevosDatos.getPrecio_base());
            servicio.setCategoria(nuevosDatos.getCategoria());
            servicio.setEstado(nuevosDatos.getEstado());
            return serviciosRepository.save(servicio);
        }).orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
    }

}
