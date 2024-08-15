package org.cibertec.edu.pe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "detalle")
public class DetalleHerramienta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDetalle;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idHerramienta")
	private Herramienta herramienta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idVenta")
	private Venta venta;
	
	private int cantidad;
	private double subtotal;
}