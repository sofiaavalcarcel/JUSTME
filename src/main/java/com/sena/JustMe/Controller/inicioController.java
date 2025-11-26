package com.sena.JustMe.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.service.IServiciosService;
import com.sena.JustMe.service.ICitasService;
import jakarta.servlet.http.HttpSession;

@Controller
public class inicioController {

	@Autowired
	private IServiciosService serviciosService; // 👈 interfaz, no la clase directamente

	@Autowired
	private ICitasService citasService;

	@GetMapping("/inicio")
	public String mostrarServiciosInicio(Model model, HttpSession session) {
		List<Servicios> lista = serviciosService.listarServicios();
		model.addAttribute("servicios", lista);

		// 🔹 Cargar citas del usuario si está logueado
		Integer idUsuario = (Integer) session.getAttribute("idUsuario");
		if (idUsuario != null) {
			model.addAttribute("citas", citasService.listarCitasPorCliente(idUsuario));
		}

		return "servicios/inicio"; // -> templates/servicios/inicio.html
	}

	@GetMapping("/pasareladepagos")
	public String pasareladepagos(@RequestParam("id") Integer id, Model model) {
		// Buscar el servicio en la BD
		Servicios servicio = serviciosService.buscarPorId(id)
				.orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado: " + id));

		// Pasar el servicio a la vista
		model.addAttribute("servicio", servicio);

		return "servicios/pasareladepagos";
	}

	@GetMapping("/perfilUsuario")
	public String perfilUsuario() {
		// Busca src/main/resources/templates/terminosycondiciones.html
		return "perfilUsuario/perfil_usuario";
	}

	@GetMapping("/perfilProfesional")
	public String perfilProfesional() {
		// Busca src/main/resources/templates/terminosycondiciones.html
		return "perfilProfesional/home";
	}

	@GetMapping("/cambioProfesional")
	public String cambioProfesional() {
		return "perfilProfesional/cambioProfesional";
	}

	@GetMapping("/contactanos")
	public String contacto() {
		return "servicios/contactanos";
	}

	@GetMapping("/servicios/buscar")
	public String buscarServicios(
			@RequestParam(required = false) String categoria,
			@RequestParam(required = false) String nombre_servicios,
			Model model, HttpSession session) {

		List<Servicios> servicios = serviciosService.buscarPorCategoriaYNombre(categoria, nombre_servicios);
		model.addAttribute("servicios", servicios);
		model.addAttribute("categoriaSeleccionada", categoria); // 👈 mantiene seleccionada la opción
		model.addAttribute("servicios", servicios);
		model.addAttribute("categoriaSeleccionada", categoria); // 👈 mantiene seleccionada la opción

		// 🔹 Cargar citas del usuario si está logueado (también en búsqueda)
		Integer idUsuario = (Integer) session.getAttribute("idUsuario");
		if (idUsuario != null) {
			model.addAttribute("citas", citasService.listarCitasPorCliente(idUsuario));
		}

		return "servicios/inicio"; // tu vista principal
	}

}
