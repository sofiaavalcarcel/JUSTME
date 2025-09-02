package com.sena.JustMe.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sena.JustMe.repository.IServiciosRepository;



@Controller
public class IServiciosController {

    @Autowired
    private IServiciosRepository servicioRepository;

    @GetMapping("/")
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioRepository.findAll());
        return "index";
    }
}

