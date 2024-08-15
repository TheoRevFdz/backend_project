package org.cibertec.edu.pe.services;

import org.cibertec.edu.pe.model.Categoria;
import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Herramienta;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IHerramientasService {
    List<Herramienta> findAll();

    List<Herramienta> findByCategory(int idCategory);

    Categoria addToCarShop(int idHerramienta, List<Detalle> carrito);

    double updateCarShop(List<Detalle> carrito);

    double deleteCarShop(List<Detalle> carrito, int idHerramienta);
}
