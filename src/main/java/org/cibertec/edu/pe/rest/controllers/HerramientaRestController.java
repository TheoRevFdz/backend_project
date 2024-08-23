package org.cibertec.edu.pe.rest.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.cibertec.edu.pe.model.Categoria;
import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Herramienta;
import org.cibertec.edu.pe.services.IHerramientasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@RestController
@RequestMapping("/api/herramientas")
public class HerramientaRestController {
    @Autowired
    private IHerramientasService herramientasService;

    @PostMapping("/agregar/{idHerramienta}")
    public ResponseEntity<Object> agregar(HttpSession session, @PathVariable(name = "idHerramienta", required = true) int idHerramienta, @RequestBody Categoria categoria) {
        // Obtener o inicializar el carrito de la sesión
        List<Detalle> carrito = (List<Detalle>) session.getAttribute("carrito");

        Optional<Categoria> resCategoria = Optional.ofNullable(herramientasService.addToCarShop(idHerramienta, carrito));

        Integer idCategoria = null;
        if (resCategoria.isPresent()) {
            return ResponseEntity.ok(resCategoria);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Object> herramientasByCategoria(@PathVariable("idCategoria") int idCategoria, Model model) {
        List<Herramienta> herramientas = herramientasService.findByCategory(idCategoria);
        return ResponseEntity.ok(herramientas);
    }

    @PutMapping("/actualizarCarrito")
    public ResponseEntity<Object> actualizarCarrito(@RequestBody List<Detalle> carrito) {
        try {
            double total = herramientasService.updateCarShop(carrito);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idHerramienta}")
    public ResponseEntity<Object> eliminarDelCarrito(@PathVariable("idHerramienta") int idHerramienta,
                                                     @RequestBody List<Detalle> carrito) {
        try {
            double total = herramientasService.deleteCarShop(carrito, idHerramienta);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
