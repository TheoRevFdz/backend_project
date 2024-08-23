package org.cibertec.edu.pe.services.impl;

import org.cibertec.edu.pe.model.Categoria;
import org.cibertec.edu.pe.repository.ICategoriaRepository;
import org.cibertec.edu.pe.services.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements ICategoriaService {
    @Autowired
    private ICategoriaRepository repository;

    @Override
    public List<Categoria> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Categoria> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria) {
        return repository.findById(categoria.getIdCategoria())
                .map(cate -> repository.save(categoria))
                .orElseGet(() -> null);
    }
}
