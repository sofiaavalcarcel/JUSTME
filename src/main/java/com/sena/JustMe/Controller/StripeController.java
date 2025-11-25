package com.sena.JustMe.Controller;

import java.time.LocalDateTime;
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

        if (servicioIdObj == null || montoObj == null || monedaObj == null || paymentIntentIdObj == null
                || estadoObj == null) {
            response.put("success", false);
            response.put("message", "Datos incompletos para registrar el pago");
            return response;
        }

        Integer servicioId = Integer.parseInt(servicioIdObj.toString());
        Double monto = Double.parseDouble(montoObj.toString());
        String moneda = monedaObj.toString();
        String paymentIntentId = paymentIntentIdObj.toString();
        String estado = estadoObj.toString();

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

        response.put("success", true);
        return response;
    }
}

