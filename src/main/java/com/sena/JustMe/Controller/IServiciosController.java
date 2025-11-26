package com.sena.JustMe.Controller;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.service.ExcelServiciosService;
import com.sena.JustMe.service.ServiciosServiceImplement;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/servicios")
public class IServiciosController {

    private final ServiciosServiceImplement serviciosService;
    private final ExcelServiciosService excelServiciosService;

    // Constructor claro y limpio
    public IServiciosController(ServiciosServiceImplement serviciosService,
                                ExcelServiciosService excelServiciosService) {
        this.serviciosService = serviciosService;
        this.excelServiciosService = excelServiciosService;
    }

    // ============================================================
    // 1️⃣ LISTAR SERVICIOS
    // ============================================================
    @GetMapping
    public String listarServicios(Model model) {
        model.addAttribute("servicios", serviciosService.listarServicios());
        return "servicios/lista";
    }

    // ============================================================
    // 2️⃣ FORMULARIO NUEVO SERVICIO
    // ============================================================
    @GetMapping("/profesional/servicios")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("servicio", new Servicios());
        return "profesional/servicios";
    }

    // ============================================================
    // 3️⃣ GUARDAR SERVICIO INDIVIDUAL
    // ============================================================
    @PostMapping("/guardar")
    public String guardarServicio(
            @ModelAttribute Servicios servicio,
            @RequestParam("imagenFile") MultipartFile file,
            HttpSession session
    ) {

        try {
            // Asignar usuario desde sesión
            Integer idUsuario = (Integer) session.getAttribute("idUsuario");
            if (idUsuario != null) {
                var usuario = serviciosService.getUsuarioById(idUsuario);
                if (usuario != null) servicio.setUsuario(usuario);
            }

            // Guardar imagen
            if (!file.isEmpty()) {
                File uploadFolder = new File("uploads");
                if (!uploadFolder.exists()) uploadFolder.mkdirs();

                String extension = file.getOriginalFilename()
                        .substring(file.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID() + extension;

                Path filePath = Paths.get(uploadFolder.getAbsolutePath(), fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                servicio.setImagen(fileName);
            } else {
                servicio.setImagen("default.jpg");
            }

            serviciosService.guardar(servicio);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/profesional";
    }

 // 4️⃣ SUBIDA MASIVA DESDE EXCEL  ✔ FIX ENDPOINT
    @PostMapping("/subir-excel")
    public String subirExcelServicios(
            @RequestParam("file") MultipartFile file,
            HttpSession session,
            Model model
    ) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Debes seleccionar un archivo Excel.");
            model.addAttribute("servicio", new Servicios());
            return "profesional/servicios";
        }

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        if (idUsuario == null) {
            model.addAttribute("error", "No se encontró un usuario en sesión. Inicia sesión nuevamente.");
            model.addAttribute("servicio", new Servicios());
            return "profesional/servicios";
        }

        try {
            int registros = excelServiciosService.leerExcelServicios(file, idUsuario);
            model.addAttribute("success", "Se cargaron " + registros + " servicios correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al procesar el archivo: " + e.getMessage());
        }

        // Volver al formulario para que el JS muestre SweetAlert y luego redirija a /profesional
        model.addAttribute("servicio", new Servicios());
        return "profesional/servicios";
    }


    // ============================================================
    // 5️⃣ DESCARGAR PLANTILLA EXCEL
    // ============================================================
    @GetMapping("/descargar-plantilla")
    public ResponseEntity<Resource> descargarPlantilla() {

        try {
            ClassPathResource plantillaResource = new ClassPathResource("plantillas/plantilla_servicios.xlsx");

            if (!plantillaResource.exists()) {
                return ResponseEntity.notFound().build();
            }

            InputStreamResource resource = new InputStreamResource(plantillaResource.getInputStream());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=plantilla_servicios.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .contentLength(plantillaResource.contentLength())
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ============================================================
    // 6️⃣ FORMULARIO DE EDICIÓN
    // ============================================================
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
        Servicios servicio = serviciosService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        model.addAttribute("servicio", servicio);
        return "profesional/editar";
    }

    // ============================================================
    // 7️⃣ EDITAR SERVICIO
    // ============================================================
    @PostMapping("/editar/{id}")
    public String editarServicio(
            @PathVariable Integer id,
            @ModelAttribute Servicios servicio,
            @RequestParam("imagenFile") MultipartFile file
    ) {

        try {
            Servicios existente = serviciosService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

            existente.setNombre_servicios(servicio.getNombre_servicios());
            existente.setCategoria(servicio.getCategoria());
            existente.setPrecio_base(servicio.getPrecio_base());
            existente.setEstado(servicio.getEstado());
            existente.setDescripcion(servicio.getDescripcion());

            if (!file.isEmpty()) {
                File folder = new File("uploads");
                if (!folder.exists()) folder.mkdirs();

                String extension = file.getOriginalFilename()
                        .substring(file.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID() + extension;

                Path filePath = Paths.get(folder.getAbsolutePath(), fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                existente.setImagen(fileName);
            }

            serviciosService.guardar(existente);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/profesional";
    }

    // ============================================================
    // 8️⃣ ELIMINAR SERVICIO
    // ============================================================
    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Integer id) {
        serviciosService.eliminar(id);
        return "redirect:/profesional";
    }

    // ============================================================
    // 9️⃣ DETALLE DEL SERVICIO
    // ============================================================
    @GetMapping("/detalle/{id}")
    public String detalleServicio(@PathVariable Integer id, Model model) {
        Optional<Servicios> servicio = serviciosService.buscarPorId(id);

        if (servicio.isPresent()) {
            model.addAttribute("servicio", servicio.get());
            return "usuario/detalleservicio";
        }

        return "redirect:/servicios";
    }
}
