package org.cibertec.edu.pe.services.impl;

import org.cibertec.edu.pe.model.Adquisicion;
import org.cibertec.edu.pe.services.IAdquisicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdquisicionServiceImpl implements IAdquisicionService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdquisicionServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Adquisicion> obtenerAdquisiciones() {
    	String sql = "SELECT " +
                "    v.id_venta AS ID_VENTA, " +
                "    h.precio AS PRECIO_UNIDAD, " +
                "    c.descripcion AS CATEGORIA, " +
                "    h.descripcion AS NOMBRE_HERRAMIENTA, " +
                "    SUM(d.cantidad) AS CANTIDAD_TOTAL, " +
                "    h.imagen AS HERRAMIENTA, " +
                "    SUM(d.subtotal) AS SUBTOTAL_TOTAL, " +
                "    v.fecha_registro AS FECHA_VENTA " +
                "FROM " +
                "    detalle d " +
                "JOIN herramientas h ON d.id_herramienta = h.id_herramienta " +
                "JOIN categoria c ON h.id_categoria = c.id_categoria " +
                "JOIN venta v ON d.id_venta = v.id_venta " +
                "GROUP BY " +
                "    v.id_venta, h.precio, c.descripcion, h.descripcion, h.imagen, v.fecha_registro " +
                "ORDER BY " +
                "    v.id_venta ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Adquisicion adquisicion = new Adquisicion();
            adquisicion.setIdVenta(rs.getInt("ID_VENTA"));
            adquisicion.setCategoria(rs.getString("CATEGORIA"));
            adquisicion.setNombreHerramienta(rs.getString("NOMBRE_HERRAMIENTA"));
            adquisicion.setCantidad(rs.getInt("CANTIDAD_TOTAL"));
            adquisicion.setHerramienta(rs.getString("HERRAMIENTA"));
            adquisicion.setSubtotal(rs.getDouble("SUBTOTAL_TOTAL"));
            //adquisicion.setFechaVenta(rs.getString("FECHA_VENTA"));
            adquisicion.setPrecioUnidad(rs.getDouble("PRECIO_UNIDAD"));
            return adquisicion;
        });
    }
}