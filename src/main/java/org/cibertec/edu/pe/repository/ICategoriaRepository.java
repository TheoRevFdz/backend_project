package org.cibertec.edu.pe.repository;

import java.util.Optional;

import org.cibertec.edu.pe.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{

	Optional<Categoria> findByDescripcion(String descripcion);
}