package com.sena.JustMe.Controller;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.service.ServiciosServiceImplement;
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

@Controller
@RequestMapping("/servicios")
public class IServiciosController {

	private final ServiciosServiceImplement serviciosService;

	public IServiciosController(ServiciosServiceImplement serviciosService) {
		this.serviciosService = serviciosService;
	}

	// 1. Listar servicios
	@GetMapping
	public String listarServicios(Model model) {
		List<Servicios> listaServicios = serviciosService.listarServicios();
		model.addAttribute("servicios", listaServicios);
		return "servicios/lista"; // <-- tu vista para listar servicios
	}

	// Mostrar formulario para crear un nuevo servicio
	@GetMapping("/profesional/servicios")
	public String mostrarFormularioNuevo(Model model) {
		model.addAttribute("servicio", new Servicios()); // objeto vacío
		return "profesional/servicios"; // <-- tu nueva plantilla nuevo.html
	}

	@PostMapping("/guardar")
	public String guardarServicio(@ModelAttribute Servicios servicio, @RequestParam("imagenFile") MultipartFile file) {

		try {
			if (!file.isEmpty()) {
				// Carpeta de destino real (la que sirve Spring en tiempo de ejecución)
				File uploadFolder = new File("target/classes/static/uploads");
				if (!uploadFolder.exists()) {
					uploadFolder.mkdirs();
				}

				// Nombre único para evitar colisiones
				String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				String fileName = UUID.randomUUID().toString() + extension;

				// Guardar archivo físico
				Path filePath = Paths.get(uploadFolder.getAbsolutePath(), fileName);
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				// Guardar nombre en BD
				servicio.setImagen(fileName);
			} else {
				// Imagen por defecto
				servicio.setImagen("default.jpg");
			}

			serviciosService.guardar(servicio);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/profesional";
	}

	// Mostrar formulario de edición
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
		Servicios servicio = serviciosService.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));

		model.addAttribute("servicio", servicio);
		return "profesional/editar"; // <- nombre del HTML
	}

	@PostMapping("/editar/{id}")
	public String editarServicio(@PathVariable Integer id, @ModelAttribute Servicios servicio,
			@RequestParam("imagenFile") MultipartFile file) {

		try {
			// Obtener servicio existente de la BD
			Servicios existente = serviciosService.buscarPorId(id)
					.orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));

			// Actualizar campos editables
			existente.setNombre_servicios(servicio.getNombre_servicios());
			existente.setCategoria(servicio.getCategoria());
			existente.setPrecio_base(servicio.getPrecio_base());
			existente.setEstado(servicio.getEstado());
			existente.setDescripcion(servicio.getDescripcion());

			// Si viene una nueva imagen, guardarla y reemplazar la anterior
			if (!file.isEmpty()) {
				File uploadFolder = new File("target/classes/static/uploads");
				if (!uploadFolder.exists()) {
					uploadFolder.mkdirs();
				}

				String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				String fileName = UUID.randomUUID().toString() + extension;

				Path filePath = Paths.get(uploadFolder.getAbsolutePath(), fileName);
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				existente.setImagen(fileName);
			}

			serviciosService.guardar(existente); // o editarServicio(id, existente)

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/profesional";
	}

	// 4. Eliminar servicio
	@PostMapping("/eliminar/{id}")
	public String eliminarServicio(@PathVariable Integer id) {
		serviciosService.eliminar(id);
		return "redirect:/profesional"; // 👈 vuelve a la vista de profesional
	}

}
