package org.cibertec.edu.pe.services;

import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Venta;

import java.util.List;

public interface IVentasSeervice {
    Venta pagar(List<Detalle> carrito);
}
