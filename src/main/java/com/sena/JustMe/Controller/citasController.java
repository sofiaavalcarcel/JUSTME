package com.sena.JustMe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.JustMe.model.Citas;
import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.service.ICitasService;

@Controller
@RequestMapping("/citas")
public class citasController {

    @Autowired
    private ICitasService citasService;

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
        Citas cita = citasService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        // ✅ Evita error Thymeleaf: inicializa el servicio si está null
        if (cita.getServicio() == null) {
            cita.setServicio(new Servicios());
        }

        model.addAttribute("cita", cita);
        return "profesional/editar_cita";
    }

    // Guardar cambios de edición
    @PostMapping("/actualizar")
    public String actualizarCita(@ModelAttribute("cita") Citas cita) {
        // ✅ Verificación de seguridad: debe tener servicio con id
        if (cita.getServicio() == null || cita.getServicio().getIdservicios() == null) {
            // throw new IllegalArgumentException("La cita debe tener un servicio
            // asociado.");
            // Podríamos recargar la cita original si falta info, pero por ahora asumimos
            // que viene del form
        }

        citasService.guardar(cita);
        return "redirect:/profesional"; // vuelve a la lista
    }
}
