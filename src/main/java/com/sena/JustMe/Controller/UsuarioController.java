package com.sena.JustMe.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    // =========================
    // Páginas públicas
    // =========================

    @GetMapping("/terminosycondiciones")
    public String terminos() {
        return "servicios/terminos";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "servicios/registrar";
    }

    // =========================
    // Registro de usuario
    // =========================
    @PostMapping("/save")
    public String save(Usuarios usuario) {
        LOGGER.info("Registrando usuario: {}", usuario);

        // Rol por defecto: CLIENTE (id=3)
        Optional<Rol> rolCliente = rolService.findById(3);
        if (rolCliente.isPresent()) {
            usuario.setRol(rolCliente.get());
        } else {
            LOGGER.error("No se encontró el rol CLIENTE con id=3. Revisa tu tabla roles.");
            return "redirect:/registro?error=rol";
        }

        // Foto de perfil por defecto
        if (usuario.getFotoperfil() == null || usuario.getFotoperfil().isEmpty()) {
            usuario.setFotoperfil("defaultuser.jpg");
        }

        usuarioService.save(usuario);
        return "redirect:/";
    }

    // =========================
    // Login y sesión
    // =========================
    @PostMapping("/acceder")
    public String acceder(Usuarios usuario, HttpSession session, Model model) {
        Optional<Usuarios> userEmail = usuarioService.findByEmail(usuario.getEmail());

        if (userEmail.isPresent()) {
            Usuarios user = userEmail.get();

            // Validar contraseña
            if (user.getContrasena() != null && user.getContrasena().equals(usuario.getContrasena())) {

                // Guardar datos en sesión
                session.setAttribute("idUsuario", user.getId());
                session.setAttribute("nombreUsuario", user.getNombre() + " " + user.getApellido());
                session.setAttribute("emailUsuario", user.getEmail());
                session.setAttribute("numeroUsuario", user.getNumero());
                session.setAttribute("direccionUsuario", user.getDireccion());
                session.setAttribute("biografiaUsuario", user.getBiografia());
                session.setAttribute("fotoperfil", 
                        (user.getFotoperfil() != null && !user.getFotoperfil().isEmpty()) 
                        ? user.getFotoperfil() 
                        : "defaultuser.jpg");

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

    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }



    // =========================
    // Perfil de usuario
    // =========================
    @GetMapping("/perfil_usuario")
    public String mostrarPerfil(HttpSession session, Model model) {
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        if (idUsuario != null) {
            Usuarios user = usuarioService.findById(idUsuario).orElse(null);
            if (user != null) {
                session.setAttribute("nombreUsuario", user.getNombre() + " " + user.getApellido());
                session.setAttribute("emailUsuario", user.getEmail());
                session.setAttribute("numeroUsuario", user.getNumero());
                session.setAttribute("direccionUsuario", user.getDireccion());
                session.setAttribute("biografiaUsuario", user.getBiografia());
                session.setAttribute("fotoperfil", user.getFotoperfil());
            }
        }
        return "perfilUsuario/perfil_usuario";
    }

    @PostMapping("/usuario/actualizar")
    public String actualizarUsuario(
            @RequestParam(value = "foto", required = false) MultipartFile archivo,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("numero") String numero,
            @RequestParam("direccion") String direccion,
            @RequestParam("biografia") String biografia,
            HttpSession session) {

        try {
            Integer idUsuario = (Integer) session.getAttribute("idUsuario");

            if (idUsuario != null) {
                Usuarios user = usuarioService.findById(idUsuario).orElse(null);

                if (user != null) {
                    // Actualizar nombre y apellido
                    String[] nombres = nombre.split(" ", 2);
                    user.setNombre(nombres[0]);
                    user.setApellido(nombres.length > 1 ? nombres[1] : "");

                    // Actualizar otros campos
                    user.setEmail(email);
                    user.setNumero(numero);
                    user.setDireccion(direccion);
                    user.setBiografia(biografia);

                    // Actualizar foto
                    if (archivo != null && !archivo.isEmpty()) {
                        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
                        Path ruta = Paths.get("uploads").resolve(nombreArchivo);
                        Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
                        user.setFotoperfil(nombreArchivo);
                    } else if (user.getFotoperfil() == null || user.getFotoperfil().isEmpty()) {
                        user.setFotoperfil("defaultuser.jpg");
                    }

                    usuarioService.save(user);

                    // Refrescar sesión
                    session.setAttribute("nombreUsuario", user.getNombre() + " " + user.getApellido());
                    session.setAttribute("emailUsuario", user.getEmail());
                    session.setAttribute("numeroUsuario", user.getNumero());
                    session.setAttribute("direccionUsuario", user.getDireccion());
                    session.setAttribute("biografiaUsuario", user.getBiografia());
                    session.setAttribute("fotoperfil", user.getFotoperfil());

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Error al actualizar la foto de perfil: {}", e.getMessage());
        }

        return "redirect:/perfil_usuario";
    }
}
