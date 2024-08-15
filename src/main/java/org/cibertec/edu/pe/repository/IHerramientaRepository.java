package org.cibertec.edu.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.cibertec.edu.pe.model.Herramienta;
import org.springframework.stereotype.Repository;

@Repository
public interface IHerramientaRepository extends JpaRepository<Herramienta, Integer> {

	@Query("SELECT h FROM Herramienta h WHERE h.categoria.idCategoria = :idCategoria")
    List<Herramienta> findByCategoriaId(@Param("idCategoria") int idCategoria);


	
	
	
}