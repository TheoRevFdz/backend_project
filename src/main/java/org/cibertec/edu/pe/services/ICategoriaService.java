package org.cibertec.edu.pe.services;

import org.cibertec.edu.pe.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    List<Categoria> findAll();

    Optional<Categoria> findById(Integer id);

    Categoria save(Categoria categoria);

    Categoria update(Categoria categoria);
}
