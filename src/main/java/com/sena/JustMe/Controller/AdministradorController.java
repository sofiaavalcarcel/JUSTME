package com.sena.JustMe.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.JustMe.model.Citas;
import com.sena.JustMe.model.Pagos;
import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.service.ICitasService;
import com.sena.JustMe.service.IPagosService;
import com.sena.JustMe.service.IUsuariosService;
import com.sena.JustMe.service.IServiciosService;
import com.sena.JustMe.service.IRolService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private IServiciosService productoservice;

	@Autowired
	private IUsuariosService usuariosService;

	@Autowired
	private ICitasService citasService;

	@Autowired
	private IPagosService pagosService;

	@Autowired
	private IRolService rolService;

	@GetMapping("")
	public String home(Model model) {
		// Listados principales
		List<Servicios> servicios = productoservice.listarServicios();
		List<Usuarios> usuarios = usuariosService.findAll();
		List<Citas> citas = citasService.listarCitas();
		List<Pagos> pagos = pagosService.listarTodos();

		model.addAttribute("servicios", servicios);
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("citas", citas);
		model.addAttribute("pagos", pagos);

		// Estadísticas simples para tarjetas
		model.addAttribute("totalServicios", servicios.size());
		model.addAttribute("totalUsuarios", usuarios.size());
		model.addAttribute("totalCitas", citas.size());
		model.addAttribute("totalIngresos", pagosService.obtenerTotalIngresos());

		// Servicios por categoría
		Map<String, Long> serviciosPorCategoria = new HashMap<>();
		for (Servicios s : servicios) {
			String categoria = s.getCategoria() != null ? s.getCategoria() : "Sin categoría";
			serviciosPorCategoria.put(categoria, serviciosPorCategoria.getOrDefault(categoria, 0L) + 1L);
		}
		List<String> labelsServiciosCat = new ArrayList<>(serviciosPorCategoria.keySet());
		Collections.sort(labelsServiciosCat);
		List<Long> dataServiciosCat = new ArrayList<>();
		for (String c : labelsServiciosCat) {
			dataServiciosCat.add(serviciosPorCategoria.get(c));
		}
		model.addAttribute("labelsServiciosCat", labelsServiciosCat);
		model.addAttribute("dataServiciosCat", dataServiciosCat);

		// Citas por estado
		Map<String, Long> citasPorEstado = new HashMap<>();
		for (Citas c : citas) {
			String estado = c.getEstado() != null ? c.getEstado() : "SIN_ESTADO";
			citasPorEstado.put(estado, citasPorEstado.getOrDefault(estado, 0L) + 1L);
		}
		List<String> labelsCitasEstado = new ArrayList<>(citasPorEstado.keySet());
		Collections.sort(labelsCitasEstado);
		List<Long> dataCitasEstado = new ArrayList<>();
		for (String e : labelsCitasEstado) {
			dataCitasEstado.add(citasPorEstado.get(e));
		}
		model.addAttribute("labelsCitasEstado", labelsCitasEstado);
		model.addAttribute("dataCitasEstado", dataCitasEstado);

		// Ingresos por profesional (top 5)
		Map<String, Double> ingresosPorProfesional = new HashMap<>();
		for (Pagos p : pagos) {
			if (p.getServicio() != null && p.getServicio().getUsuario() != null) {
				String nombreProf = p.getServicio().getUsuario().getNombre();
				String apellidoProf = p.getServicio().getUsuario().getApellido();
				String clave = (nombreProf != null ? nombreProf : "").concat(" ")
						.concat(apellidoProf != null ? apellidoProf : "").trim();
				if (clave.isEmpty()) {
					clave = "Profesional";
				}
				double monto = p.getMonto() != null ? p.getMonto() : 0.0;
				ingresosPorProfesional.put(clave, ingresosPorProfesional.getOrDefault(clave, 0.0) + monto);
			}
		}
		List<Map.Entry<String, Double>> listaProfesionales = new ArrayList<>(ingresosPorProfesional.entrySet());
		listaProfesionales.sort(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).reversed());
		int limite = Math.min(5, listaProfesionales.size());
		List<String> labelsTopProfesionales = new ArrayList<>();
		List<Double> dataTopProfesionales = new ArrayList<>();
		for (int i = 0; i < limite; i++) {
			labelsTopProfesionales.add(listaProfesionales.get(i).getKey());
			dataTopProfesionales.add(listaProfesionales.get(i).getValue());
		}
		model.addAttribute("labelsTopProfesionales", labelsTopProfesionales);
		model.addAttribute("dataTopProfesionales", dataTopProfesionales);

		// Ingresos de la aplicación (1% total acumulado)
		double totalIngresosApp1 = 0.0;
		for (Pagos p : pagos) {
			double monto = p.getMonto() != null ? p.getMonto() : 0.0;
			totalIngresosApp1 += monto * 0.01;
		}
		model.addAttribute("totalIngresosApp1", totalIngresosApp1);

		// Datos de la barra derecha: por ahora se generan en memoria
		List<String> notificaciones = new ArrayList<>();
		notificaciones.add("Nuevos usuarios registrados: " + usuarios.size());
		notificaciones.add("Citas agendadas: " + citas.size());
		notificaciones.add("Servicios activos: " + servicios.size());
		model.addAttribute("notificaciones", notificaciones);

		List<String> actividades = new ArrayList<>();
		actividades.add("Actualización de catálogo de servicios");
		actividades.add("Revisión de pagos recientes");
		actividades.add("Gestión de usuarios activos");
		model.addAttribute("actividades", actividades);

		List<String> managers = new ArrayList<>();
		managers.add("Admin Principal");
		managers.add("Soporte");
		model.addAttribute("managers", managers);

		return "administrador/home";
	}
	
    // =========================
    // Listar usuarios (solo admin) - vista detallada
    // =========================
    @GetMapping("/listar")
    public String listarUsuarios(Model model, HttpSession session) {
        Object rol = session.getAttribute("rolUsuario");
        if (rol != null && rol.toString().equalsIgnoreCase("ADMIN")) {
            List<Usuarios> usuarios = usuariosService.findAll();
            model.addAttribute("usuarios", usuarios);
            return "usuario/listar";
        } else {
            return "redirect:/login?unauthorized=true";
        }
    }

    // =========================
    // Editar usuario (formulario)
    // =========================
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable("id") Integer id, Model model, HttpSession session) {
        Object rol = session.getAttribute("rolUsuario");
        if (rol == null || !rol.toString().equalsIgnoreCase("ADMIN")) {
            return "redirect:/login?unauthorized=true";
        }

        Usuarios usuario = usuariosService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.findAll());
        return "administrador/editar_usuario";
    }

    // =========================
    // Guardar usuario editado
    // =========================
    @PostMapping("/usuarios/guardar")
    public String guardarUsuarioEditado(Usuarios usuario,
            @RequestParam(name = "rolId", required = false) Integer rolId,
            @RequestParam(name = "nuevaContrasena", required = false) String nuevaContrasena,
            HttpSession session) {
        Object rol = session.getAttribute("rolUsuario");
        if (rol == null || !rol.toString().equalsIgnoreCase("ADMIN")) {
            return "redirect:/login?unauthorized=true";
        }

        // Asignar rol si se envió rolId
        if (rolId != null) {
            rolService.findById(rolId).ifPresent(usuario::setRol);
        }

        // Si el admin escribió una nueva contraseña, se actualizará y el service la cifrará
        if (nuevaContrasena != null && !nuevaContrasena.isBlank()) {
            usuario.setContrasena(nuevaContrasena);
        }

        usuariosService.save(usuario);
        return "redirect:/administrador";
    }

    // =========================
    // Secciones individuales
    // =========================
    @GetMapping("/servicios")
    public String seccionServicios(Model model) {
        List<Servicios> servicios = productoservice.listarServicios();
        model.addAttribute("servicios", servicios);
        return "administrador/servicios";
    }

    @GetMapping("/usuarios")
    public String seccionUsuarios(Model model) {
        List<Usuarios> usuarios = usuariosService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "administrador/usuarios";
    }

    @GetMapping("/citas")
    public String seccionCitas(Model model) {
        List<Citas> citas = citasService.listarCitas();
        model.addAttribute("citas", citas);
        return "administrador/citas";
    }

    @GetMapping("/pagos")
    public String seccionPagos(Model model) {
        List<Pagos> pagos = pagosService.listarTodos();
        model.addAttribute("pagos", pagos);
        return "administrador/pagos";
    }

    // =========================
    // Editar servicio (formulario)
    // =========================
    @GetMapping("/servicios/editar/{id}")
    public String editarServicio(@PathVariable("id") Integer id, Model model) {
        Servicios servicio = productoservice.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        model.addAttribute("servicio", servicio);
        return "administrador/editar_servicio";
    }

    // =========================
    // Guardar servicio editado
    // =========================
    @PostMapping("/servicios/guardar")
    public String guardarServicioEditado(Servicios servicio) {
        Servicios existente = productoservice.buscarPorId(servicio.getIdservicios())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        existente.setNombre_servicios(servicio.getNombre_servicios());
        existente.setCategoria(servicio.getCategoria());
        existente.setPrecio_base(servicio.getPrecio_base());
        existente.setEstado(servicio.getEstado());
        existente.setDescripcion(servicio.getDescripcion());
        productoservice.guardar(existente);
        return "redirect:/administrador/servicios";
    }
}