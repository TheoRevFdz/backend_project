package org.cibertec.edu.pe.services.impl;

import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Venta;
import org.cibertec.edu.pe.repository.IDetalleRepository;
import org.cibertec.edu.pe.repository.IVentaRepository;
import org.cibertec.edu.pe.services.IVentasSeervice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class VentasServiceImpl implements IVentasSeervice {

    @Autowired
    private IVentaRepository ventaRepository;
    @Autowired
    private IDetalleRepository detalleRepository;

    @Override
    public Venta pagar(List<Detalle> carrito) {
        // Crear una nueva venta y guardarla en la base de datos
        Venta venta = new Venta();
        venta.setFechaRegistro(new Date(System.currentTimeMillis()));
        venta.setMontoTotal(calcularTotal(carrito));
        venta = ventaRepository.save(venta);

        // Guardar los detalles de la venta en la base de datos
        for (Detalle detalle : carrito) {
            detalle.setVenta(venta);
            detalleRepository.save(detalle);
        }
        return venta;
    }

    private double calcularTotal(List<Detalle> carrito) {
        double total = 0.0;
        for (Detalle detalle : carrito) {
            total += detalle.getSubtotal();
        }
        return total;
    }
}
