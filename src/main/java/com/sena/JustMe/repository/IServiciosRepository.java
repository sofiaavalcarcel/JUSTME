package com.sena.JustMe.repository;

import com.sena.JustMe.model.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IServiciosRepository extends JpaRepository<Servicios, Integer> {
	 List<Servicios> findByUsuario_Idusuarios(Long idUsuario);
}
