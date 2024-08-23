package org.cibertec.edu.pe.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.cibertec.edu.pe.model.Herramienta;
import org.cibertec.edu.pe.model.Adquisicion;
import org.cibertec.edu.pe.model.Categoria;
import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.repository.IHerramientaRepository;
import org.cibertec.edu.pe.services.IAdquisicionService;
import org.cibertec.edu.pe.services.IHerramientasService;
import org.cibertec.edu.pe.services.IVentasSeervice;
import org.cibertec.edu.pe.repository.ICategoriaRepository;
import org.cibertec.edu.pe.repository.IDetalleRepository;
import org.cibertec.edu.pe.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"carrito", "total"})
public class HerramientaController {

    @Autowired
    private IVentaRepository ventaRepository;
    @Autowired
    private IDetalleRepository detalleRepository;
    @Autowired
    private IHerramientasService herramientasService;
    @Autowired
    private IVentasSeervice ventasSeervice;

    ////////listado categorias//////////
    @GetMapping("/constructora")
    public String listado(Model model) {
        List<Categoria> lista = new ArrayList<>();
        lista = categoriaRepository.findAll();
        model.addAttribute("categorias", lista);
        return "constructora";
    }


    //////////listado herramientas por categoria/////////////

    @Autowired
    private IHerramientaRepository herramientaRepository;

    @GetMapping("/herramientas/{idCategoria}")
    public String listadoHerramientas(@PathVariable("idCategoria") int idCategoria, Model model) {
        List<Herramienta> herramientas = herramientaRepository.findByCategoriaId(idCategoria);
        model.addAttribute("herramientas", herramientas);
        return "herramientas";
    }


    ////////////Add Carrito/////////
    @GetMapping("/agregar/{idHerramienta}")
    public String agregar(Model model, HttpSession session, @PathVariable(name = "idHerramienta", required = true) int idHerramienta) {
        // Obtener o inicializar el carrito de la sesión
        List<Detalle> carrito = (List<Detalle>) session.getAttribute("carrito");

        Optional<Categoria> resCategoria = Optional.ofNullable(herramientasService.addToCarShop(idHerramienta, carrito));

        Integer idCategoria = null;
        if (resCategoria.isPresent()) {
            idCategoria = resCategoria.get().getIdCategoria();
            // Agregar la categoría al modelo
            model.addAttribute("idCategoria", idCategoria);
        }
        // Redirigir a la página del carrito
        return "redirect:/herramientas/" + idCategoria;
    }


    @GetMapping("/carrito")
    public String carrito() {
        return "carrito";
    }


    /*******pagar******/

    @GetMapping("/pagar")
    public String pagar(HttpSession session, Model model) {
        // Obtener el carrito de la sesión
        List<Detalle> carrito = (List<Detalle>) session.getAttribute("carrito");

        ventasSeervice.pagar(carrito);

        session.removeAttribute("carrito");
        session.removeAttribute("total");

        model.addAttribute("mensaje", "¡Pago y registro de venta exitosos!");

        // Limpiar el modelo de carrito y total
        model.addAttribute("carrito", new ArrayList<Detalle>());
        model.addAttribute("total", 0.0);

        return "mensaje";
    }


    /*******actualizar carrito*******/


    @PostMapping("/actualizarCarrito")
    public String actualizarCarrito(Model model) {
        List<Detalle> carrito = (List<Detalle>) model.getAttribute("carrito");

        double total = herramientasService.updateCarShop(carrito);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        return "redirect:/carrito";
    }


    @ModelAttribute("carrito")
    public List<Detalle> getCarrito() {
        return new ArrayList<Detalle>();
    }

    @ModelAttribute("total")
    public double getTotal() {
        return 0.0;
    }


    @GetMapping("/eliminar/{idHerramienta}")
    public String eliminarDelCarrito(@PathVariable("idHerramienta") int idHerramienta, HttpSession session) {
        List<Detalle> carrito = (List<Detalle>) session.getAttribute("carrito");
        double total = herramientasService.deleteCarShop(carrito, idHerramienta);
        session.setAttribute("carrito", carrito);
        session.setAttribute("total", total);
        return "redirect:/carrito";
    }

    //////////////////categoria/////////////

    @Autowired
    private ICategoriaRepository categoriaRepository;


    @GetMapping("/categoria")
    public String listaCategorias(Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "categoria"; // Nombre del archivo HTML (categorias.html)
    }


    ///////////////////Adquisiciones//////////////////

    @Autowired
    private IAdquisicionService adquisicionService;

    @GetMapping("/adquisiciones")
    public String adquisiciones(Model model) {
        // Realizar la consulta SQL y obtener los datos
        List<Adquisicion> adquisiciones = adquisicionService.obtenerAdquisiciones();

        // Agregar los resultados al modelo
        model.addAttribute("adquisiciones", adquisiciones);

        // Devolver el nombre de la vista
        return "adquisiciones";
    }
}