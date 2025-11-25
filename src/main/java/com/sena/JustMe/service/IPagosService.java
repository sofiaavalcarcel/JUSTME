package com.sena.JustMe.service;

import java.util.List;

import com.sena.JustMe.model.Pagos;

public interface IPagosService {

    Pagos guardar(Pagos pago);

    List<Pagos> listarPorUsuario(Integer idUsuario);
}
