package com.sena.JustMe.Controller;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.service.ServiciosServiceImplement;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/servicios")
public class IServiciosController {

	private final ServiciosServiceImplement serviciosService;

	public IServiciosController(ServiciosServiceImplement serviciosService) {
		this.serviciosService = serviciosService;
	}

	// ============================================================
	// 1️⃣ LISTAR SERVICIOS (Vista general de todos los servicios)
	// ============================================================
	@GetMapping
	public String listarServicios(Model model) {
		List<Servicios> listaServicios = serviciosService.listarServicios();
		model.addAttribute("servicios", listaServicios);
		return "servicios/lista"; // 👈 Vista donde se muestran los servicios
	}

	// ============================================================
	// 2️⃣ MOSTRAR FORMULARIO PARA CREAR UN NUEVO SERVICIO
	// ============================================================
	@GetMapping("/profesional/servicios")
	public String mostrarFormularioNuevo(Model model) {
		model.addAttribute("servicio", new Servicios()); // 👈 Se envía un objeto vacío al formulario
		return "profesional/servicios"; // 👈 Vista del formulario de creación
	}

	// ============================================================
	// 3️⃣ GUARDAR NUEVO SERVICIO
	// ============================================================
	// ============================================================
	// 3️⃣ GUARDAR NUEVO SERVICIO (con usuario logueado)
	// ============================================================
	@PostMapping("/guardar")
	public String guardarServicio(
	        @ModelAttribute Servicios servicio,
	        @RequestParam("imagenFile") MultipartFile file,
	        HttpSession session) { // 👈 Se usa la sesión para identificar al usuario logueado

	    try {
	        // 🔹 1. Obtener el ID del usuario desde la sesión (OJO: nombre correcto del atributo)
	        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
	        System.out.println("🟢 ID del usuario en sesión: " + idUsuario);

	        // 🔹 2. Verificar que sí haya usuario en sesión
	        if (idUsuario != null) {
	            // Buscar el usuario en la base de datos
	            com.sena.JustMe.model.Usuarios usuario = serviciosService.getUsuarioById(idUsuario);

	            if (usuario != null) {
	                // Asignar el usuario logueado al nuevo servicio
	                servicio.setUsuario(usuario);
	                System.out.println("✅ Usuario asignado al servicio: " + usuario.getNombre());
	            } else {
	                System.out.println("⚠️ No se encontró el usuario con ID " + idUsuario);
	            }
	        } else {
	            System.out.println("⚠️ No hay usuario guardado en la sesión (idUsuario es null)");
	        }

	        // 🔹 3. Manejar la imagen del servicio
	        if (!file.isEmpty()) {
	            File uploadFolder = new File("uploads");
	            if (!uploadFolder.exists()) {
	                uploadFolder.mkdirs(); // Crear carpeta si no existe
	            }

	            // Crear un nombre único para el archivo
	            String extension = file.getOriginalFilename()
	                    .substring(file.getOriginalFilename().lastIndexOf("."));
	            String fileName = UUID.randomUUID().toString() + extension;

	            // Guardar el archivo físicamente
	            Path filePath = Paths.get(uploadFolder.getAbsolutePath(), fileName);
	            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	            // Guardar el nombre en la BD
	            servicio.setImagen(fileName);
	        } else {
	            servicio.setImagen("default.jpg"); // Imagen por defecto
	        }

	        // 🔹 4. Guardar el servicio en la BD con el usuario asociado
	        serviciosService.guardar(servicio);
	        System.out.println("💾 Servicio guardado correctamente con usuario ID: " + idUsuario);

	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("❌ Error al guardar el servicio o la imagen.");
	    }

	    // 🔹 5. Redirigir al panel del profesional
	    return "redirect:/profesional";
	}


	// ============================================================
	// 4️⃣ MOSTRAR FORMULARIO DE EDICIÓN
	// ============================================================
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
		Servicios servicio = serviciosService.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));

		model.addAttribute("servicio", servicio);
		return "profesional/editar"; // 👈 Vista del formulario de edición
	}

	// ============================================================
	// 5️⃣ GUARDAR CAMBIOS AL EDITAR UN SERVICIO
	// ============================================================
	@PostMapping("/editar/{id}")
	public String editarServicio(
			@PathVariable Integer id,
			@ModelAttribute Servicios servicio,
			@RequestParam("imagenFile") MultipartFile file) {

		try {
			// Buscar el servicio existente
			Servicios existente = serviciosService.buscarPorId(id)
					.orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));

			// Actualizar los campos editables
			existente.setNombre_servicios(servicio.getNombre_servicios());
			existente.setCategoria(servicio.getCategoria());
			existente.setPrecio_base(servicio.getPrecio_base());
			existente.setEstado(servicio.getEstado());
			existente.setDescripcion(servicio.getDescripcion());

			// Manejar nueva imagen si se sube una
			if (!file.isEmpty()) {
				File uploadFolder = new File("uploads");
				if (!uploadFolder.exists()) {
					uploadFolder.mkdirs();
				}

				String extension = file.getOriginalFilename()
						.substring(file.getOriginalFilename().lastIndexOf("."));
				String fileName = UUID.randomUUID().toString() + extension;

				Path filePath = Paths.get(uploadFolder.getAbsolutePath(), fileName);
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				existente.setImagen(fileName);
			}

			serviciosService.guardar(existente);
			System.out.println("✏️ Servicio editado correctamente.");

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("❌ Error al editar el servicio.");
		}

		return "redirect:/profesional";
	}

	// ============================================================
	// 6️⃣ ELIMINAR SERVICIO
	// ============================================================
	@PostMapping("/eliminar/{id}")
	public String eliminarServicio(@PathVariable Integer id) {
		serviciosService.eliminar(id);
		System.out.println("🗑️ Servicio eliminado con ID: " + id);
		return "redirect:/profesional";
	}

	// ============================================================
	// 7️⃣ DETALLE DE UN SERVICIO ESPECÍFICO
	// ============================================================
	@GetMapping("/detalle/{id}")
	public String detalleServicio(@PathVariable Integer id, Model model) {
		Optional<Servicios> servicio = serviciosService.buscarPorId(id);

		if (servicio.isPresent()) {
			model.addAttribute("servicio", servicio.get());
			return "usuario/detalleservicio"; // 👈 Vista del detalle del servicio
		} else {
			System.out.println("⚠️ Servicio no encontrado con ID: " + id);
			return "redirect:/servicios";
		}
	}
}
