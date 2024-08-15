package org.cibertec.edu.pe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.cibertec.edu.pe.model.Usuario;
import org.cibertec.edu.pe.dto.UsuarioRegistroDTO;


public interface IUsuarioService extends UserDetailsService{

	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	public List<Usuario> listarUsuarios();

	public Optional<Usuario> create(Usuario user);

	public Optional<Usuario> signIn(Usuario user);
}
