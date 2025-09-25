package com.sena.JustMe.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.service.IServiciosService;

@Controller
public class inicioController {

	@Autowired
	private IServiciosService serviciosService; // 👈 interfaz, no la clase directamente

	@GetMapping("/inicio")
	public String mostrarServiciosInicio(Model model) {
		List<Servicios> lista = serviciosService.listarServicios();
		model.addAttribute("servicios", lista);
		return "servicios/inicio"; // -> templates/servicios/inicio.html
	}


	@GetMapping("/pasareladepagos")
	public String pasareladepagos() {
		// Busca src/main/resources/templates/terminosycondiciones.html
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
	


}
