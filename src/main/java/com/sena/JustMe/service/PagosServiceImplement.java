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
}
