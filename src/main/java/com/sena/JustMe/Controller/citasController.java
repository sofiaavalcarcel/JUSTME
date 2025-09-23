package com.sena.JustMe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.JustMe.model.Citas_reservas;
import com.sena.JustMe.service.ICitas_reservasService;

public class citasController {
	@Controller
	@RequestMapping("/citas")
	public class CitasController {

	    @Autowired
	    private ICitas_reservasService citasService;

	    // Mostrar formulario de edición
	    @GetMapping("/editar/{id}")
	    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
	        Citas_reservas cita = citasService.buscarPorId(id)
	                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

	        model.addAttribute("cita", cita);
	        return "profesional/editar_cita"; // 👈 vista Thymeleaf del formulario
	    }

	    // Guardar cambios de edición
	    @PostMapping("/actualizar")
	    public String actualizarCita(@ModelAttribute("cita") Citas_reservas cita) {
	        citasService.guardar(cita);
	        return "redirect:/profesional"; // 👈 vuelve a la lista
	    }
	}

}
