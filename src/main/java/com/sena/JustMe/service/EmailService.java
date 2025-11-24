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
}
