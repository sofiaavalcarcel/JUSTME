package com.sena.JustMe.service;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.repository.IServiciosRepository;
import com.sena.JustMe.repository.IUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiciosServiceImplement implements IServiciosService {

    private final IServiciosRepository serviciosRepository;

    // 👇 Inyectamos el repositorio de usuarios para poder buscarlos por ID
    @Autowired
    private IUsuarioRepository usuarioRepository;

    public ServiciosServiceImplement(IServiciosRepository serviciosRepository) {
        this.serviciosRepository = serviciosRepository;
    }

    // ====================================================
    // 🔹 Guardar un servicio (nuevo o editado)
    // ====================================================
    public Servicios guardar(Servicios servicio) {
        return serviciosRepository.save(servicio);
    }

    // ====================================================
    // 🔹 Listar todos los servicios
    // ====================================================
    @Override
    public List<Servicios> listarServicios() {
        return serviciosRepository.findAll();
    }

    // ====================================================
    // 🔹 Eliminar servicio por ID
    // ====================================================
    @Override
    public void eliminar(Integer id) {
        serviciosRepository.deleteById(id);
    }

    // ====================================================
    // 🔹 Buscar servicio por ID
    // ====================================================
    @Override
    public Optional<Servicios> buscarPorId(Integer id) {
        return serviciosRepository.findById(id);
    }

    // ====================================================
    // 🔹 Editar servicio existente
    // ====================================================
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

    // ====================================================
    // 🔹 Listar servicios de un usuario en específico
    // ====================================================
    @Override
    public List<Servicios> listarPorUsuario(Long idUsuario) {
        return serviciosRepository.findByUsuario_Idusuarios(idUsuario);
    }

    // ====================================================
    // ✅ Nuevo método: Obtener usuario por ID
    // ====================================================
    public Usuarios getUsuarioById(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));
    }
}
