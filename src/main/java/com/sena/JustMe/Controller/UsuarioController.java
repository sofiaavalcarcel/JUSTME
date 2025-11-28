package com.sena.JustMe.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.sena.JustMe.security.CustomUserDetails;
import com.sena.JustMe.service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuariosService usuarioService;

    @Autowired
    private IRolService rolService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        // Validar si ya existe un usuario con el mismo correo
        if (usuarioService.findByEmail(usuario.getEmail()).isPresent()) {
            return "redirect:/registro?error=email";
        }

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

        Usuarios creado = usuarioService.save(usuario);

        emailService.enviarCorreoBienvenida(creado.getEmail(), creado.getNombre());
        return "redirect:/";
    }
    
    

    // =========================
    // Login y sesión
    // =========================
    @PostMapping("/acceder")
    public String acceder(Usuarios usuario, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getContrasena()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Usuarios user = userDetails.getUsuario();

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

            switch (rolNombre.toUpperCase()) {
                case "ADMIN":
                    return "redirect:/administrador";
                case "PROFESIONAL":
                    return "redirect:/profesional";
                default:
                    return "redirect:/inicio";
            }

        } catch (AuthenticationException ex) {
            LOGGER.warn("Error de autenticación para el usuario {}", usuario.getEmail());
            return "redirect:/?error=true";
        }
    }

    @GetMapping("/recuperar-contrasena")
    public String mostrarFormularioRecuperarContrasena() {
        return "servicios/recuperar_contrasena";
    }

    @PostMapping("/recuperar-contrasena")
    public String enviarCodigoRecuperacion(@RequestParam("email") String email, HttpSession session) {
        Optional<Usuarios> optionalUsuario = usuarioService.findByEmail(email);

        if (optionalUsuario.isEmpty()) {
            return "redirect:/recuperar-contrasena?error=notfound";
        }

        Usuarios usuario = optionalUsuario.get();

        String codigo = String.format("%06d", new Random().nextInt(1_000_000));
        emailService.enviarCorreoRecuperacionContrasena(usuario.getEmail(), usuario.getNombre(), codigo);

        session.setAttribute("resetEmail", usuario.getEmail());
        session.setAttribute("resetCode", codigo);

        return "redirect:/restablecer-contrasena?success=true";
    }

    @GetMapping("/restablecer-contrasena")
    public String mostrarFormularioRestablecerContrasena(HttpSession session) {
        String email = (String) session.getAttribute("resetEmail");
        String codigo = (String) session.getAttribute("resetCode");

        if (email == null || codigo == null) {
            return "redirect:/recuperar-contrasena";
        }

        return "servicios/restablecer_contrasena";
    }

    @PostMapping("/restablecer-contrasena")
    public String procesarRestablecerContrasena(
            @RequestParam("codigo") String codigoIngresado,
            @RequestParam("nuevaContrasena") String nuevaContrasena,
            @RequestParam("confirmarContrasena") String confirmarContrasena,
            HttpSession session) {

        String email = (String) session.getAttribute("resetEmail");
        String codigoGuardado = (String) session.getAttribute("resetCode");

        if (email == null || codigoGuardado == null) {
            return "redirect:/recuperar-contrasena";
        }

        if (!codigoGuardado.equals(codigoIngresado)) {
            return "redirect:/restablecer-contrasena?error=codigo";
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            return "redirect:/restablecer-contrasena?error=contrasena";
        }

        Optional<Usuarios> optionalUsuario = usuarioService.findByEmail(email);

        if (optionalUsuario.isEmpty()) {
            return "redirect:/recuperar-contrasena";
        }

        Usuarios usuario = optionalUsuario.get();
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioService.save(usuario);

        session.removeAttribute("resetEmail");
        session.removeAttribute("resetCode");

        return "redirect:/?resetSuccess=true";
    }

    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session) {
        SecurityContextHolder.clearContext();
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

    // =========================
    // Cambio a cuenta profesional
    // =========================
    @PostMapping("/profesional/cambio")
    public String cambioAProfesional(
            @RequestParam("biografia") String biografia,
            @RequestParam("availability") String disponibilidad,
            @RequestParam(value = "portafolio", required = false) MultipartFile[] portafolioFiles,
            HttpSession session,
            Model model) {

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            model.addAttribute("error", "No hay sesión activa.");
            return "redirect:/";
        }

        Optional<Usuarios> optionalUsuario = usuarioService.findById(idUsuario);
        if (optionalUsuario.isEmpty()) {
            model.addAttribute("error", "Usuario no encontrado.");
            return "redirect:/";
        }

        try {
            Usuarios usuario = optionalUsuario.get();
            usuario.setBiografia(biografia);
            usuario.setDisponibilidad(disponibilidad);

            if (portafolioFiles != null && portafolioFiles.length > 0) {
                StringBuilder sb = new StringBuilder();
                Path uploadPath = Paths.get("uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile file : portafolioFiles) {
                    if (!file.isEmpty()) {
                        String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                        Path destino = uploadPath.resolve(nombreArchivo);
                        Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                        sb.append(nombreArchivo).append(",");
                    }
                }

                usuario.setPortafolio(sb.toString());
            }

            Optional<Rol> rolProfesional = rolService.findById(2);
            rolProfesional.ifPresent(usuario::setRol);

            usuario.setEstado("Activo");
            usuarioService.save(usuario);

            session.setAttribute("biografiaUsuario", usuario.getBiografia());
            session.setAttribute("rolUsuario", "PROFESIONAL");
            session.setAttribute("portafolioUsuario", usuario.getPortafolio());

            return "redirect:/profesional";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al procesar los archivos: " + e.getMessage());
            return "redirect:/perfil_usuario";
        }
    }

    // =========================
    // Cambio de contraseña
    // =========================
    @PostMapping("/usuario/cambiar-contrasena")
    public String cambiarContrasena(
            @RequestParam("contrasenaActual") String contrasenaActual,
            @RequestParam("nuevaContrasena") String nuevaContrasena,
            @RequestParam("confirmarContrasena") String confirmarContrasena,
            HttpSession session) {

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        if (idUsuario == null) {
            return "redirect:/?error=true";
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            return "redirect:/perfil_usuario?error=contrasena_nueva";
        }

        try {
            usuarioService.changePassword(idUsuario, contrasenaActual, nuevaContrasena);
        } catch (RuntimeException ex) {
            return "redirect:/perfil_usuario?error=contrasena_actual";
        }

        return "redirect:/perfil_usuario?success=true";
    }
}
