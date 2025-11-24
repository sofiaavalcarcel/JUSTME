package com.sena.JustMe.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.model.Citas_reservas;
import com.sena.JustMe.service.IUsuariosService;
import com.sena.JustMe.service.IServiciosService;
import com.sena.JustMe.service.ICitas_reservasService;
import com.sena.JustMe.service.EmailService;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IServiciosService serviciosService;

    @Autowired
    private ICitas_reservasService citasService;

    // =========================
    // USUARIOS
    // =========================

    @GetMapping("/usuarios")
    public List<Usuarios> listarUsuarios() {
        return usuariosService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> obtenerUsuario(@PathVariable Integer id) {
        Optional<Usuarios> usuario = usuariosService.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuarios> crearUsuario(@RequestBody Usuarios usuario) {
        Usuarios creado = usuariosService.save(usuario);

        emailService.enviarCorreoBienvenida(creado.getEmail(), creado.getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuarios datos) {
        Optional<Usuarios> existenteOpt = usuariosService.findById(id);
        if (existenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuarios existente = existenteOpt.get();
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setEmail(datos.getEmail());
        existente.setNumero(datos.getNumero());
        existente.setContrasena(datos.getContrasena());
        existente.setDireccion(datos.getDireccion());
        existente.setBiografia(datos.getBiografia());
        existente.setDocumentos(datos.getDocumentos());
        existente.setPortafolio(datos.getPortafolio());
        existente.setEstado(datos.getEstado());
        existente.setDisponibilidad(datos.getDisponibilidad());
        existente.setRol(datos.getRol());
        existente.setFotoperfil(datos.getFotoperfil());

        Usuarios actualizado = usuariosService.save(existente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        Optional<Usuarios> existente = usuariosService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuariosService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // SERVICIOS
    // =========================

    @GetMapping("/servicios")
    public List<Servicios> listarServicios() {
        return serviciosService.listarServicios();
    }

    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicios> obtenerServicio(@PathVariable Integer id) {
        Optional<Servicios> servicio = serviciosService.buscarPorId(id);
        return servicio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/servicios")
    public ResponseEntity<Servicios> crearServicio(@RequestBody Servicios servicio) {
        Servicios creado = serviciosService.guardar(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/servicios/{id}")
    public ResponseEntity<Servicios> actualizarServicio(@PathVariable Integer id, @RequestBody Servicios datos) {
        Optional<Servicios> existenteOpt = serviciosService.buscarPorId(id);
        if (existenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Servicios existente = existenteOpt.get();
        existente.setNombre_servicios(datos.getNombre_servicios());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio_base(datos.getPrecio_base());
        existente.setCategoria(datos.getCategoria());
        existente.setEstado(datos.getEstado());
        existente.setImagen(datos.getImagen());
        existente.setUsuario(datos.getUsuario());

        Servicios actualizado = serviciosService.guardar(existente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Integer id) {
        Optional<Servicios> existente = serviciosService.buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        serviciosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // CITAS
    // =========================

    @GetMapping("/citas")
    public List<Citas_reservas> listarCitas() {
        return citasService.listarcitas();
    }

    @GetMapping("/citas/{id}")
    public ResponseEntity<Citas_reservas> obtenerCita(@PathVariable Integer id) {
        Optional<Citas_reservas> cita = citasService.buscarPorId(id);
        return cita.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/citas")
    public ResponseEntity<Citas_reservas> crearCita(@RequestBody Citas_reservas cita) {
        Citas_reservas creada = citasService.guardar(cita);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/citas/{id}")
    public ResponseEntity<Citas_reservas> actualizarCita(@PathVariable Integer id, @RequestBody Citas_reservas datos) {
        Optional<Citas_reservas> existenteOpt = citasService.buscarPorId(id);
        if (existenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Citas_reservas existente = existenteOpt.get();
        existente.setFecha_hora(datos.getFecha_hora());
        existente.setDireccion_servicio(datos.getDireccion_servicio());
        existente.setEstado_cita(datos.getEstado_cita());
        existente.setPrecio(datos.getPrecio());
        existente.setObservacionesCl(datos.getObservacionesCl());
        existente.setObservacionesLb(datos.getObservacionesLb());
        existente.setFechaEdicion(datos.getFechaEdicion());
        existente.setServicio(datos.getServicio());

        Citas_reservas actualizada = citasService.guardar(existente);
        return ResponseEntity.ok(actualizada);
    }

    // Nota: ICitas_reservasService no define eliminar(), por eso no se implementa DELETE aquí.
}
