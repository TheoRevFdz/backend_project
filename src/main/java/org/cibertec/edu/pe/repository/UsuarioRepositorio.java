package org.cibertec.edu.pe.repository;

import org.cibertec.edu.pe.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

	Usuario findByEmail(String email);

	Optional<Usuario> findByUsername(String username);
	
}
