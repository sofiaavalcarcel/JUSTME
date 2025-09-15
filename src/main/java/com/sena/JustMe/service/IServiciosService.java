package com.sena.JustMe.service;

import com.sena.JustMe.model.Servicios;
import java.util.List;
import java.util.Optional;

public interface IServiciosService {
    List<Servicios> listarServicios();
    void eliminar(Integer id);
    Optional<Servicios> buscarPorId(Integer id);
    Servicios editarServicio(Integer id, Servicios servicioActualizado);
    Servicios guardar(Servicios servicio);
}
