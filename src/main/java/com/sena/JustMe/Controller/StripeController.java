package com.sena.JustMe.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.JustMe.model.Pagos;
import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.service.IPagosService;
import com.sena.JustMe.service.IServiciosService;
import com.sena.JustMe.service.IUsuariosService;
import com.sena.JustMe.service.ICitasService;
import com.sena.JustMe.model.Citas;
import java.util.Date;
import com.sena.JustMe.service.EmailService;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Autowired
    private IPagosService pagosService;

    @Autowired
    private IServiciosService serviciosService;

    @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private ICitasService citasService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create-payment-intent")
    public Map<String, Object> createPaymentIntent(@RequestBody Map<String, Object> data) throws Exception {
        Long amount = Long.parseLong(data.get("amount").toString()); // valor en centavos

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("cop")
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("clientSecret", intent.getClientSecret());
        return responseData;
    }

    @PostMapping("/registrar-pago")
    public Map<String, Object> registrarPago(@RequestBody Map<String, Object> data, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Object servicioIdObj = data.get("servicioId");
        Object montoObj = data.get("amount");
        Object monedaObj = data.get("currency");
        Object paymentIntentIdObj = data.get("paymentIntentId");
        Object estadoObj = data.get("status");
        Object fechaHoraServicioObj = data.get("serviceDateTime");

        if (servicioIdObj == null || montoObj == null || monedaObj == null || paymentIntentIdObj == null
                || estadoObj == null || fechaHoraServicioObj == null) {
            response.put("success", false);
            response.put("message", "Datos incompletos para registrar el pago (falta información de fecha y hora)");
            return response;
        }

        Integer servicioId = Integer.parseInt(servicioIdObj.toString());
        Double monto = Double.parseDouble(montoObj.toString());
        String moneda = monedaObj.toString();
        String paymentIntentId = paymentIntentIdObj.toString();
        String estado = estadoObj.toString();
        String fechaHoraServicioStr = fechaHoraServicioObj.toString();

        // Parsear fecha y hora enviada desde el frontend (input datetime-local: yyyy-MM-dd'T'HH:mm)
        Date fechaHoraCita;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime ldt = LocalDateTime.parse(fechaHoraServicioStr, formatter);
            ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
            fechaHoraCita = Date.from(zdt.toInstant());
        } catch (DateTimeParseException ex) {
            response.put("success", false);
            response.put("message", "Formato de fecha y hora inválido para la cita");
            return response;
        }

        // Validar que la fecha/hora de la cita no sea en el pasado
        Date ahora = new Date();
        if (fechaHoraCita.before(ahora)) {
            response.put("success", false);
            response.put("message", "La fecha y hora de la cita no puede ser anterior al momento actual");
            return response;
        }

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        if (idUsuario == null) {
            response.put("success", false);
            response.put("message", "No hay usuario en sesión para asociar el pago");
            return response;
        }

        Usuarios usuario = usuariosService.findById(idUsuario).orElse(null);
        Servicios servicio = serviciosService.buscarPorId(servicioId).orElse(null);

        if (usuario == null || servicio == null) {
            response.put("success", false);
            response.put("message", "No se pudo encontrar el usuario o el servicio para registrar el pago");
            return response;
        }

        Pagos pago = new Pagos();
        pago.setUsuario(usuario);
        pago.setServicio(servicio);
        pago.setMonto(monto);
        pago.setMoneda(moneda);
        pago.setEstado(estado);
        pago.setPaymentIntentId(paymentIntentId);
        pago.setFechaCreacion(LocalDateTime.now());

        pagosService.guardar(pago);

        // 🔹 Crear Cita automáticamente (Nueva tabla)
        Citas cita = new Citas();
        cita.setUsuario(usuario);
        cita.setServicio(servicio);
        cita.setFechaHora(fechaHoraCita);
        // Estado inicial de la cita al pagar: En espera de confirmación
        cita.setEstado("En espera");
        cita.setPrecio(monto);
        cita.setDireccion(usuario.getDireccion());

        citasService.guardar(cita);

        // Notificar al usuario: confirmación de pago (ya existente)
        if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
            try {
                emailService.enviarCorreoConfirmacionPago(
                        usuario.getEmail(),
                        usuario.getNombre(),
                        servicio.getNombre_servicios(),
                        monto,
                        moneda,
                        estado);
            } catch (Exception e) {
                // no interrumpir el flujo por un error de correo
            }
        }

        // Notificar al profesional sobre la nueva cita
        if (servicio.getUsuario() != null && servicio.getUsuario().getEmail() != null
                && !servicio.getUsuario().getEmail().isEmpty()) {
            try {
                String nombreProfesional = servicio.getUsuario().getNombre() + " "
                        + servicio.getUsuario().getApellido();
                String nombreCliente = usuario.getNombre() + " " + usuario.getApellido();

                emailService.enviarCorreoNuevaCitaProfesional(
                        servicio.getUsuario().getEmail(),
                        nombreProfesional,
                        nombreCliente,
                        servicio.getNombre_servicios(),
                        fechaHoraCita,
                        cita.getDireccion());
            } catch (Exception e) {
                // no interrumpir el flujo por un error de correo
            }
        }

        // Notificar al usuario sobre la nueva cita creada
        if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
            try {
                String nombreProfesional = (servicio.getUsuario() != null)
                        ? servicio.getUsuario().getNombre() + " " + servicio.getUsuario().getApellido()
                        : "Profesional";

                emailService.enviarCorreoNuevaCitaUsuario(
                        usuario.getEmail(),
                        usuario.getNombre(),
                        nombreProfesional,
                        servicio.getNombre_servicios(),
                        fechaHoraCita,
                        cita.getDireccion());
            } catch (Exception e) {
                // no interrumpir el flujo por un error de correo
            }
        }

        response.put("success", true);
        return response;
    }
}
