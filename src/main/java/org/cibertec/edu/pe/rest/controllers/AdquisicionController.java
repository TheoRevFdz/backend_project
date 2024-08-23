package org.cibertec.edu.pe.rest.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.cibertec.edu.pe.model.Adquisicion;
import org.cibertec.edu.pe.services.IAdquisicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@RestController
@RequestMapping("/api/adquisiciones")
public class AdquisicionController {
    @Autowired
    private IAdquisicionService service;

    @GetMapping
    public ResponseEntity<Object> findAllAdquisiciones() {
        List<Adquisicion> adquisiciones = service.obtenerAdquisiciones();
        ResponseEntity<Object> resp = ResponseEntity.ok(adquisiciones);
        return resp;
    }
}
