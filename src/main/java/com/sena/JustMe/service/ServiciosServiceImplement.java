package com.sena.JustMe.service;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.repository.IServiciosRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosServiceImplement implements IServiciosService {

    private final IServiciosRepository serviciosRepository;

    public ServiciosServiceImplement(com.sena.JustMe.repository.IServiciosRepository serviciosRepository) {
        this.serviciosRepository = serviciosRepository;
    }

    @Override
    public List<Servicios> listarServicios() {
        return serviciosRepository.findAll();
    }

	@Override
	public Servicios obtenerPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Servicios guardar(Servicios servicio) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void eliminar(Integer id) {
        serviciosRepository.deleteById(id);
	}
}
