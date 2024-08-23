package org.cibertec.edu.pe.rest.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.cibertec.edu.pe.model.Categoria;
import org.cibertec.edu.pe.services.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "Bearer " + HttpHeaders.AUTHORIZATION)
@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestController {
    @Autowired
    private ICategoriaService service;

    @GetMapping
    public ResponseEntity<Object> listaCategorias() {
        List<Categoria> categorias = service.findAll();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<Object> createCategoria(@RequestBody Categoria categoria) {
        Categoria result = service.save(categoria);
        ResponseEntity<Object> resp = ResponseEntity.status(HttpStatus.CREATED).body(result);
        return resp;
    }

    @PutMapping
    public ResponseEntity<Object> updateCategoria(@RequestBody Categoria categoria) {
        Optional<Categoria> result = Optional.ofNullable(service.update(categoria));
        if (result.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Integer id) {
        Optional<Categoria> result = service.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
