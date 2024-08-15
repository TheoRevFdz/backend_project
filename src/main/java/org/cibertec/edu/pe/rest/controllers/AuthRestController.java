package org.cibertec.edu.pe.rest.controllers;

import org.cibertec.edu.pe.model.Usuario;
import org.cibertec.edu.pe.services.IUsuarioService;
import org.cibertec.edu.pe.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    @Autowired
    private IUsuarioService usersService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<Usuario> signup(@RequestBody Usuario user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersService.create(user)
                .map(u -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(u))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<MessageUtil> login(@RequestBody Usuario user) {
        return usersService.signIn(user)
                .map(u -> ResponseEntity.ok(MessageUtil.builder()
                        .message("Acceso correcto")
                        .status(HttpStatus.OK.value())
                        .timestamp(new Date())
                        .result(u)
                        .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(MessageUtil.builder()
                                .message("Usuario o password incorrectos.")
                                .timestamp(new Date())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .build()));
    }
}
