package com.sena.JustMe.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class UsuarioController {


	@GetMapping("/terminosycondiciones")
	public String terminos() {
		// Busca src/main/resources/templates/terminosycondiciones.html
		return "terminos/terminosycondiciones";
	}
}
