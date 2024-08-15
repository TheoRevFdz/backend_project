package org.cibertec.edu.pe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "venta")
public class Venta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVenta;
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	private double montoTotal;
	
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<Detalle> detalles;
}