package com.sena.JustMe.service;

import java.util.List;

import com.sena.JustMe.model.Pagos;

public interface IPagosService {

    Pagos guardar(Pagos pago);

    List<Pagos> listarPorUsuario(Integer idUsuario);

    // Listar todos los pagos (para panel administrador)
    List<Pagos> listarTodos();

    // Obtener el total de ingresos (suma de montos de todos los pagos)
    Double obtenerTotalIngresos();

    // Listar pagos más recientes (limitado por parámetro)
    List<Pagos> listarPagosRecientes(int limite);
}
