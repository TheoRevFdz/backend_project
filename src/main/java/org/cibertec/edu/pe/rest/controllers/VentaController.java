package org.cibertec.edu.pe.rest.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Venta;
import org.cibertec.edu.pe.services.IVentasSeervice;
import org.cibertec.edu.pe.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired
    private IVentasSeervice seervice;

    @PostMapping("/pagar")
    public ResponseEntity<Object> pagar(HttpSession session) {
        List<Detalle> carrito = (List<Detalle>) session.getAttribute("carrito");

        Optional<Venta> result = Optional.ofNullable(seervice.pagar(carrito));

        if (result.isPresent()) {
            session.removeAttribute("carrito");
            session.removeAttribute("total");

            return ResponseEntity.ok(MessageUtil.builder()
                    .message("¡Pago y registro de venta exitosos!")
                    .status(HttpStatus.CREATED.value())
                    .timestamp(new Date())
                    .result(result.get())
                    .build()
            );
        }
        return ResponseEntity.badRequest().build();
    }
}
