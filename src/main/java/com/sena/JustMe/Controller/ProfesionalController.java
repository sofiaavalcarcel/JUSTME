package com.sena.JustMe.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.service.ICitasService;
import com.sena.JustMe.service.IServiciosService;
import com.sena.JustMe.service.IUsuariosService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {

	@Autowired
	private IServiciosService productoservice;

	@Autowired
	private ICitasService citasService;

	@Autowired
	private IUsuariosService usuarioService;

	@GetMapping("")
	public String home(HttpSession session, Model model) {
		Integer idUsuario = (Integer) session.getAttribute("idUsuario"); // 👈 id guardado en sesión al iniciar
		if (idUsuario == null) {
			return "redirect:/login"; // o donde manejes el login
		}

		// ✅ Filtramos servicios por el usuario logueado
		List<Servicios> servicios = productoservice.listarPorUsuario(idUsuario.longValue());

		model.addAttribute("servicios", servicios);
		model.addAttribute("citas", citasService.listarCitasPorProfesional(idUsuario)); // si luego quieres filtrar
																						// también las citas
		return "profesional/home"; // tu vista actual
	}

	// =========================
	// Perfil de profesional
	// =========================
	@GetMapping("/perfil_profesional")
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
				session.setAttribute("estadoUsuario", user.getEstado());
				session.setAttribute("disponibilidadUsuario", user.getDisponibilidad()); // 👈 nuevo
				session.setAttribute("portafolioUsuario", user.getPortafolio());

			}
		}
		return "perfilProfesional/home";
	}

	@PostMapping("/actualizar")
	public String actualizarUsuario(
			@RequestParam(value = "foto", required = false) MultipartFile archivo,
			@RequestParam("nombre") String nombre,
			@RequestParam("email") String email,
			@RequestParam("numero") String numero,
			@RequestParam("direccion") String direccion,
			@RequestParam("biografia") String biografia,
			@RequestParam("estado") String estado,
			@RequestParam(value = "disponibilidad", required = false, defaultValue = "Disponible") String disponibilidad,

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
					user.setEstado(estado);
					user.setDisponibilidad(disponibilidad); // 👈 nuevo

					// Foto
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
					session.setAttribute("estadoUsuario", user.getEstado());
					session.setAttribute("disponibilidadUsuario", user.getDisponibilidad()); //
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Redirige al home del profesional
		return "redirect:/perfilProfesional";
	}

	@PostMapping("/cambiar-contrasena")
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
			return "redirect:/perfilProfesional?error=contrasenasNoCoinciden";
		}

		try {
			usuarioService.changePassword(idUsuario, contrasenaActual, nuevaContrasena);
		} catch (RuntimeException ex) {
			return "redirect:/perfilProfesional?error=contrasenaActualIncorrecta";
		}

		return "redirect:/perfilProfesional";
	}
}
