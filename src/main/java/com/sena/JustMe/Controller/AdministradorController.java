package com.sena.JustMe.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.repository.IUsuarioRepository;
import com.sena.JustMe.service.IServiciosService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private IServiciosService productoservice;
	
	@Autowired IUsuarioRepository usuarioService;

	@GetMapping("")
	public String home(Model model) {
		List<Servicios> servicios = productoservice.listarServicios();
		model.addAttribute("servicios", servicios);
		return "administrador/home";
	}
	
    // =========================
    // Listar usuarios (solo admin)
    // =========================
    @GetMapping("/listar")
    public String listarUsuarios(Model model, HttpSession session) {
        Object rol = session.getAttribute("rolUsuario");
        if (rol != null && rol.toString().equalsIgnoreCase("ADMIN")) {
            List<Usuarios> usuarios = usuarioService.findAll();
            model.addAttribute("usuarios", usuarios);
            return "usuario/listar";
        } else {
            return "redirect:/login?unauthorized=true";
        }
    }
}