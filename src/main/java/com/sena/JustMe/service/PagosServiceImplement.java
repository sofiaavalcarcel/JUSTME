package com.sena.JustMe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.JustMe.model.Pagos;
import com.sena.JustMe.repository.IPagosRepository;

@Service
public class PagosServiceImplement implements IPagosService {

    @Autowired
    private IPagosRepository pagosRepository;

    @Override
    public Pagos guardar(Pagos pago) {
        return pagosRepository.save(pago);
    }

    @Override
    public List<Pagos> listarPorUsuario(Integer idUsuario) {
        return pagosRepository.findByUsuarioIdusuarios(idUsuario);
    }

    // Listar todos los pagos (para panel administrador)
    @Override
    public List<Pagos> listarTodos() {
        return pagosRepository.findAll();
    }

    // Obtener el total de ingresos sumando los montos de todos los pagos
    @Override
    public Double obtenerTotalIngresos() {
        return pagosRepository.findAll().stream()
                .mapToDouble(p -> p.getMonto() != null ? p.getMonto() : 0.0)
                .sum();
    }

    // Listar pagos más recientes, limitando por el parámetro "limite"
    @Override
    public List<Pagos> listarPagosRecientes(int limite) {
        List<Pagos> ordenados = pagosRepository.findAllByOrderByFechaCreacionDesc();
        if (ordenados.size() <= limite) {
            return ordenados;
        }
        return ordenados.subList(0, limite);
    }
}
