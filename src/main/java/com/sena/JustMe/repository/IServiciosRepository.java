package com.sena.JustMe.repository;

import com.sena.JustMe.model.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IServiciosRepository extends JpaRepository<Servicios, Integer> {
	List<Servicios> findByUsuario_Idusuarios(Long idUsuario);

	@Query("SELECT s FROM Servicios s WHERE LOWER(s.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))")
    List<Servicios> findByCategoriaContainingIgnoreCase(@Param("categoria") String categoria);

    @Query("SELECT s FROM Servicios s WHERE LOWER(s.nombre_servicios) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Servicios> findByNombre_serviciosContainingIgnoreCase(@Param("nombre") String nombre_servicios);

    @Query("SELECT s FROM Servicios s WHERE LOWER(s.categoria) LIKE LOWER(CONCAT('%', :categoria, '%')) AND LOWER(s.nombre_servicios) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Servicios> findByCategoriaContainingIgnoreCaseAndNombre_serviciosContainingIgnoreCase(@Param("categoria") String categoria, @Param("nombre") String nombre_servicios);

}


