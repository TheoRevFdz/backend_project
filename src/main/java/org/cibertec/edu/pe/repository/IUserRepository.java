package org.cibertec.edu.pe.repository;

import org.cibertec.edu.pe.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Usuario, Long> {
}
