package com.sena.JustMe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoBienvenida(String destinatario, String nombre) {
        if (destinatario == null || destinatario.isEmpty()) {
            return;
        }

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Bienvenido a JUSTME");

        String saludoNombre = (nombre != null && !nombre.isEmpty()) ? nombre : "";
        String cuerpo = "Hola " + saludoNombre + "\n\n" +
                "¡Bienvenido a JUSTME!\n" +
                "Gracias por registrarte en nuestra plataforma.\n\n" +
                "Saludos,\n" +
                "JUSTME";

        mensaje.setText(cuerpo);

        mailSender.send(mensaje);
    }

    public void enviarCorreoConfirmacionPago(String destinatario, String nombre, String nombreServicio,
            Double monto, String moneda, String estado) {
        if (destinatario == null || destinatario.isEmpty()) {
            return;
        }

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Gracias por agendar tu servicio - JUSTME");

        String nombreMostrado = (nombre != null && !nombre.isEmpty()) ? nombre : "";
        String servicioMostrado = (nombreServicio != null && !nombreServicio.isEmpty()) ? nombreServicio : "tu servicio";
        String montoTexto = (monto != null ? monto : 0.0) + " " + (moneda != null ? moneda : "");
        String estadoTexto = (estado != null && !estado.isEmpty()) ? estado : "procesado";

        String cuerpo = "Hola " + nombreMostrado + "\n\n" +
                "Gracias por agendar tu servicio en JUSTME.\n\n" +
                "Detalles del servicio:\n" +
                "- Servicio: " + servicioMostrado + "\n" +
                "- Monto pagado: " + montoTexto + "\n" +
                "- Estado del pago: " + estadoTexto + "\n\n" +
                "En breve uno de nuestros profesionales se pondrá en contacto contigo para coordinar los detalles.\n\n" +
                "Gracias por confiar en JUSTME.";

        mensaje.setText(cuerpo);

        mailSender.send(mensaje);
    }
}
