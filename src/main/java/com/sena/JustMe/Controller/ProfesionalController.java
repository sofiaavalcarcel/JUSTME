package com.sena.JustMe.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.service.IServiciosService;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {

	@Autowired
	private IServiciosService productoservice;

	@GetMapping("")
	public String home(Model model) {
		List<Servicios> servicios = productoservice.listarServicios();
		model.addAttribute("servicios", servicios);
		return "profesional/home";
	}
}
