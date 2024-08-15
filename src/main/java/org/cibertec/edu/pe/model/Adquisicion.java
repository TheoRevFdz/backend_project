package org.cibertec.edu.pe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "adquisicion")
public class Adquisicion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoria;
    private String nombreHerramienta;
    private int cantidad;
    private String herramienta;
    private double subtotal;
    private LocalDate fechaVenta;
    private int idVenta;
    private double precioUnidad;
}