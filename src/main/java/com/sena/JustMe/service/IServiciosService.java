package com.sena.JustMe.service;

import com.sena.JustMe.model.Servicios;
import java.util.List;

public interface IServiciosService {
    List<Servicios> listarServicios();
    Servicios obtenerPorId(Long id);
    Servicios guardar(Servicios servicio);
    void eliminar(Integer id); 
}
