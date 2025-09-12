package com.sena.JustMe.Controller;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.service.ServiciosServiceImplement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/servicios")
public class IServiciosController {

    private final ServiciosServiceImplement serviciosService;

    public IServiciosController(ServiciosServiceImplement serviciosService) {
        this.serviciosService = serviciosService;
    }
    
    

    // 1. Listar servicios
    @GetMapping
    public String listarServicios(Model model) {
        List<Servicios> listaServicios = serviciosService.listarServicios();
        model.addAttribute("servicios", listaServicios);
        return "servicios/lista"; // <-- tu vista para listar servicios
    }
    
 // Mostrar formulario para crear un nuevo servicio
    @GetMapping("/profesional/servicios")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("servicio", new Servicios()); // objeto vacío
        return "profesional/servicios"; // <-- tu nueva plantilla nuevo.html
    }

    // Guardar el servicio creado
    @PostMapping("/guardar")
    public String guardarServicio(@ModelAttribute Servicios servicio) {
        serviciosService.guardar(servicio);
        return "redirect:/profesional"; // vuelve a tu panel
    }


    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
        Servicios servicio = serviciosService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));

        model.addAttribute("servicio", servicio);
        return "profesional/editar"; // <- nombre del HTML
    }

    // Guardar cambios
    @PostMapping("/editar/{id}")
    public String editarServicio(@PathVariable Integer id, @ModelAttribute Servicios servicio) {
        serviciosService.editarServicio(id, servicio);
        return "redirect:/profesional"; 
    }

    // 4. Eliminar servicio
    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Integer id) {
        serviciosService.eliminar(id);
        return "redirect:/profesional"; // 👈 vuelve a la vista de profesional
    }
}
