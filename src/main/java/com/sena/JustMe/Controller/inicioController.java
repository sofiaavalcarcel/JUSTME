package com.sena.JustMe.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class inicioController {

    @GetMapping("/inicio")
    public String mostrarInicio() {
        // Debe coincidir con la ruta en templates
        return "servicios/inicio";
    }
}
