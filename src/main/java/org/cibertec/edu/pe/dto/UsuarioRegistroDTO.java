package org.cibertec.edu.pe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioRegistroDTO {

	private Long id;
	private String nombre;
	private String apellido;
	private String email;
	private String password;
}
