package com.sena.JustMe.Controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.model.Rol;
import com.sena.JustMe.service.IUsuariosService;
import com.sena.JustMe.service.IRolService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuariosService usuarioService;

    @Autowired
    private IRolService rolService;

    // Página de Términos y condiciones
    @GetMapping("/terminosycondiciones")
    public String terminos() {
        return "terminos/terminosycondiciones";
    }

    // Formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "servicios/registrar"; 
    }

    // Guardar usuario en DB
    @PostMapping("/save")
    public String save(Usuarios usuario) {
        LOGGER.info("Registrando usuario: {}", usuario);

        // Rol por defecto: CLIENTE (id=2)
        Optional<Rol> rolCliente = rolService.findById(3);

        if (rolCliente.isPresent()) {
            usuario.setRol(rolCliente.get());
        } else {
            LOGGER.error("No se encontró el rol CLIENTE con id=2. Revisa tu tabla roles.");
            return "redirect:/registro?error=rol";
        }

        usuarioService.save(usuario);
        return "redirect:/";
    }

    // Vista de login
    @GetMapping("/login")
    public String login() {
        return "redirect:/login";
    }

 // Procesar login
    @PostMapping("/acceder")
    public String acceder(Usuarios usuario, HttpSession session, Model model) {
        Optional<Usuarios> userEmail = usuarioService.findByEmail(usuario.getEmail());

        if (userEmail.isPresent()) {
            Usuarios user = userEmail.get();

            // Validar contraseña (si existe en la DB)
            if (user.getContrasena() != null && user.getContrasena().equals(usuario.getContrasena())) {

                // Guardar usuario en sesión
                session.setAttribute("idUsuario", user.getId());

                // 👇 Guardar el nombre completo del usuario en sesión
                session.setAttribute("nombreUsuario", user.getNombre() + " " + user.getApellido());

                // Guardar rol en sesión (si no tiene rol -> CLIENTE por defecto)
                String rolNombre = (user.getRol() != null) ? user.getRol().getNombre() : "CLIENTE";
                session.setAttribute("rolUsuario", rolNombre);

                // Redirigir según rol
                switch (rolNombre.toUpperCase()) {
                    case "ADMIN":
                        return "redirect:/administrador";
                    case "PROFESIONAL":
                        return "redirect:/profesional";
                    case "CLIENTE":
                    default:
                        return "redirect:/inicio";
                }

            } else {
                LOGGER.warn("Contraseña incorrecta para usuario {}", usuario.getEmail());
                model.addAttribute("error", "Contraseña incorrecta");
                return "redirect:/";
            }

        } else {
            LOGGER.warn("Usuario con email {} no existe en la DB", usuario.getEmail());
            model.addAttribute("error", "Usuario no encontrado");
            return "redirect:/";
        }
    }


    // Cerrar sesión
    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Listar todos los usuarios (solo admin)
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
