package org.cibertec.edu.pe.services.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.cibertec.edu.pe.model.Rol;
import org.cibertec.edu.pe.model.Usuario;
import org.cibertec.edu.pe.dto.UsuarioRegistroDTO;
import org.cibertec.edu.pe.security.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.cibertec.edu.pe.repository.UsuarioRepositorio;
import org.cibertec.edu.pe.services.IUsuarioService;

@Slf4j
@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthProvider authProvider;

    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio) {
        super();
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Rol rol = Rol.builder().nombre("ROLE_USER").build();
        Usuario usuario = Usuario.builder()
                .nombre(registroDTO.getNombre())
                .apellido(registroDTO.getApellido())
                .password(passwordEncoder.encode(registroDTO.getPassword()))
                .roles(Collections.singletonList(rol))
                .build();
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Optional<Usuario> create(Usuario user) {
        return Optional.of(usuarioRepositorio.save(user));
    }

    @Override
    public Optional<Usuario> signIn(Usuario user) {
        return Optional.ofNullable(Optional.ofNullable(usuarioRepositorio.findByEmail(user.getEmail()))
                .map(u -> {
                    boolean isValid = passwordEncoder.matches(user.getPassword(), u.getPassword());
                    if (isValid) {
                        final String token = authProvider.createToken(u);
                        log.info(String.format("Token: %s", token));
                        u.setPassword(null);
                        u.setToken(token);
                        return u;
                    }
                    return null;
                }).orElseGet(() -> null));
    }
}
