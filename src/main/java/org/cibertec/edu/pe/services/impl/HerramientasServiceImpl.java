package org.cibertec.edu.pe.services.impl;

import org.cibertec.edu.pe.model.Categoria;
import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Herramienta;
import org.cibertec.edu.pe.model.Venta;
import org.cibertec.edu.pe.repository.IHerramientaRepository;
import org.cibertec.edu.pe.services.IHerramientasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HerramientasServiceImpl implements IHerramientasService {

    @Autowired
    private IHerramientaRepository herramientaRepository;

    @Override
    public List<Herramienta> findAll() {
        return herramientaRepository.findAll();
    }

    @Override
    public List<Herramienta> findByCategory(int idCategory) {
        return herramientaRepository.findByCategoriaId(idCategory);
    }

    @Override
    public Categoria addToCarShop(int idHerramienta, List<Detalle> carrito) {
        Optional<Herramienta> resHerramienta = herramientaRepository.findById(idHerramienta);
        Categoria categoria = null;
        if (resHerramienta.isPresent()) {

            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            // Verificar si la herramienta ya está en el carrito
            boolean herramientaExistente = carrito.stream()
                    .anyMatch(detalle -> detalle.getHerramienta().getIdHerramienta() == idHerramienta);

            if (herramientaExistente) {
                // Si la herramienta ya está en el carrito, aumentar la cantidad
                carrito.stream()
                        .filter(detalle -> detalle.getHerramienta().getIdHerramienta() == idHerramienta)
                        .findFirst()
                        .ifPresent(detalle -> {
                            detalle.setCantidad(detalle.getCantidad() + 1);
                            detalle.setSubtotal(detalle.getHerramienta().getPrecio() * detalle.getCantidad());
                        });
            } else {
                // Si la herramienta no está en el carrito, agregarla con cantidad 1
                Detalle detalle = new Detalle();
                detalle.setHerramienta(resHerramienta.get());
                detalle.setCantidad(1);
                detalle.setSubtotal(detalle.getHerramienta().getPrecio());
                carrito.add(detalle);
            }
            // Obtener la categoría de la herramienta
            categoria = resHerramienta.get().getCategoria();
        }
        return categoria;
    }

    @Override
    public double updateCarShop(List<Detalle> carrito) {
        double total = 0.0;
        for (Detalle detalle : carrito) {
            double subtotal = detalle.getHerramienta().getPrecio() * detalle.getCantidad();
            detalle.setSubtotal(subtotal);
            total += subtotal;
        }
        return total;
    }

    @Override
    public double deleteCarShop(List<Detalle> carrito, int idHerramienta) {
        double total = 0.0;
        if (carrito != null) {
            // Eliminar el producto con el ID especificado
            carrito.removeIf(detalle -> detalle.getHerramienta().getIdHerramienta() == idHerramienta);

            // Recalcular el total del carrito
            total = carrito.stream().mapToDouble(Detalle::getSubtotal).sum();
        }
        return total;
    }
}
