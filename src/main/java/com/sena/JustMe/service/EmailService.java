package com.sena.JustMe.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio para el envío de correos electrónicos.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    // =========================
    // Correo de bienvenida
    // =========================

    /**
     * Envía un correo de bienvenida al usuario.
     *
     * @param destinatario Correo electrónico del destinatario.
     * @param nombre       Nombre del usuario.
     */
    public void enviarCorreoBienvenida(String destinatario, String nombre) {
        if (!validarDestinatario(destinatario)) {
            logger.warn("Destinatario no válido: {}", destinatario);
            return;
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("¡Bienvenido a JUSTME!");

            String saludoNombre = (nombre != null && !nombre.isEmpty()) ? nombre : "Estimado usuario";
            String cuerpoHtml = construirEmailBienvenida(saludoNombre);

            helper.setText(cuerpoHtml, true);
            mailSender.send(mensaje);

            logger.info("Correo de bienvenida enviado exitosamente a: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo de bienvenida a: {}", destinatario, e);
        }
    }

    // =========================
    // Correo confirmación de pago
    // =========================

    /**
     * Envía un correo de confirmación de pago al usuario.
     *
     * @param destinatario Correo electrónico del destinatario.
     * @param nombre       Nombre del usuario.
     * @param nombreServicio Nombre del servicio.
     * @param monto        Monto pagado.
     * @param moneda       Moneda utilizada.
     * @param estado       Estado del pago.
     */
    public void enviarCorreoConfirmacionPago(String destinatario, String nombre, String nombreServicio,
            Double monto, String moneda, String estado) {
        if (!validarDestinatario(destinatario)) {
            logger.warn("Destinatario no válido: {}", destinatario);
            return;
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("Confirmación de Pago - JUSTME");

            String nombreMostrado = (nombre != null && !nombre.isEmpty()) ? nombre : "Estimado cliente";
            String servicioMostrado = (nombreServicio != null && !nombreServicio.isEmpty()) ? nombreServicio
                    : "Servicio contratado";
            String montoTexto = String.format("%.2f %s", (monto != null ? monto : 0.0),
                    (moneda != null ? moneda : ""));
            String estadoTexto = (estado != null && !estado.isEmpty()) ? estado : "Procesado";

            String cuerpoHtml = construirEmailConfirmacionPago(nombreMostrado, servicioMostrado, montoTexto,
                    estadoTexto);

            helper.setText(cuerpoHtml, true);
            mailSender.send(mensaje);

            logger.info("Correo de confirmación de pago enviado exitosamente a: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo de confirmación de pago a: {}", destinatario, e);
        }
    }

    // =========================
    // Correos nueva cita
    // =========================

    /**
     * Envía un correo de nueva cita al profesional.
     *
     * @param destinatario     Correo electrónico del destinatario.
     * @param nombreProfesional Nombre del profesional.
     * @param nombreCliente    Nombre del cliente.
     * @param nombreServicio   Nombre del servicio.
     * @param fechaHora        Fecha y hora de la cita.
     * @param direccion        Dirección de la cita.
     */
    public void enviarCorreoNuevaCitaProfesional(String destinatario, String nombreProfesional, String nombreCliente,
            String nombreServicio, Date fechaHora, String direccion) {
        if (!validarDestinatario(destinatario)) {
            logger.warn("Destinatario no válido (profesional): {}", destinatario);
            return;
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("Nueva reserva en uno de tus servicios - JUSTME");

            String nombreProf = (nombreProfesional != null && !nombreProfesional.isEmpty()) ? nombreProfesional
                    : "Profesional";
            String cliente = (nombreCliente != null && !nombreCliente.isEmpty()) ? nombreCliente : "Un cliente";
            String servicioMostrado = (nombreServicio != null && !nombreServicio.isEmpty()) ? nombreServicio
                    : "Servicio";

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fechaTexto = (fechaHora != null) ? sdf.format(fechaHora) : "Por definir";
            String direccionTexto = (direccion != null && !direccion.isEmpty()) ? direccion
                    : "Dirección acordada con el cliente";

            String cuerpoHtml = construirEmailNuevaCitaProfesional(nombreProf, cliente, servicioMostrado, fechaTexto,
                    direccionTexto);

            helper.setText(cuerpoHtml, true);
            mailSender.send(mensaje);

            logger.info("Correo de nueva cita enviado al profesional: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo de nueva cita al profesional: {}", destinatario, e);
        }
    }

    /**
     * Envía un correo de nueva cita al usuario.
     *
     * @param destinatario     Correo electrónico del destinatario.
     * @param nombreUsuario    Nombre del usuario.
     * @param nombreProfesional Nombre del profesional.
     * @param nombreServicio   Nombre del servicio.
     * @param fechaHora        Fecha y hora de la cita.
     * @param direccion        Dirección de la cita.
     */
    public void enviarCorreoNuevaCitaUsuario(String destinatario, String nombreUsuario, String nombreProfesional,
            String nombreServicio, Date fechaHora, String direccion) {
        if (!validarDestinatario(destinatario)) {
            logger.warn("Destinatario no válido (usuario): {}", destinatario);
            return;
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("Has realizado una nueva reserva (en espera de confirmación) - JUSTME");

            String nombreCli = (nombreUsuario != null && !nombreUsuario.isEmpty()) ? nombreUsuario : "Cliente";
            String profesional = (nombreProfesional != null && !nombreProfesional.isEmpty()) ? nombreProfesional
                    : "tu profesional";
            String servicioMostrado = (nombreServicio != null && !nombreServicio.isEmpty()) ? nombreServicio
                    : "Servicio";

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fechaTexto = (fechaHora != null) ? sdf.format(fechaHora) : "Por definir";
            String direccionTexto = (direccion != null && !direccion.isEmpty()) ? direccion : "Dirección acordada";

            String cuerpoHtml = construirEmailNuevaCitaUsuario(nombreCli, profesional, servicioMostrado, fechaTexto,
                    direccionTexto);

            helper.setText(cuerpoHtml, true);
            mailSender.send(mensaje);

            logger.info("Correo de nueva cita enviado al usuario: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo de nueva cita al usuario: {}", destinatario, e);
        }
    }

    // Correo cuando la cita es confirmada por el profesional
    public void enviarCorreoCitaConfirmadaUsuario(String destinatario, String nombreUsuario, String nombreProfesional,
            String nombreServicio, Date fechaHora, String direccion) {
        if (!validarDestinatario(destinatario)) {
            logger.warn("Destinatario no válido (usuario, cita confirmada): {}", destinatario);
            return;
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("Tu cita ha sido confirmada - JUSTME");

            String nombreCli = (nombreUsuario != null && !nombreUsuario.isEmpty()) ? nombreUsuario : "Cliente";
            String profesional = (nombreProfesional != null && !nombreProfesional.isEmpty()) ? nombreProfesional
                    : "tu profesional";
            String servicioMostrado = (nombreServicio != null && !nombreServicio.isEmpty()) ? nombreServicio
                    : "Servicio";

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fechaTexto = (fechaHora != null) ? sdf.format(fechaHora) : "Por definir";
            String direccionTexto = (direccion != null && !direccion.isEmpty()) ? direccion : "Dirección acordada";

            String cuerpoHtml = construirEmailCitaConfirmadaUsuario(nombreCli, profesional, servicioMostrado,
                    fechaTexto, direccionTexto);

            helper.setText(cuerpoHtml, true);
            mailSender.send(mensaje);

            logger.info("Correo de cita confirmada enviado al usuario: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo de cita confirmada al usuario: {}", destinatario, e);
        }
    }

    // =========================
    // Utilidades internas
    // =========================

    /**
     * Valida si el destinatario es un correo electrónico válido.
     *
     * @param destinatario Correo electrónico del destinatario.
     * @return True si el destinatario es válido, false en caso contrario.
     */
    private boolean validarDestinatario(String destinatario) {
        return destinatario != null && !destinatario.isEmpty() && destinatario.contains("@");
    }

    public void enviarCorreoRecuperacionContrasena(String destinatario, String nombre, String codigo) {
        if (!validarDestinatario(destinatario)) {
            logger.warn("Destinatario no válido en recuperación de contraseña: {}", destinatario);
            return;
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("Recuperación de contraseña - JUSTME");

            String saludoNombre = (nombre != null && !nombre.isEmpty()) ? nombre : "Usuario";
            String cuerpoHtml = construirEmailRecuperacionContrasena(saludoNombre, codigo);

            helper.setText(cuerpoHtml, true);
            mailSender.send(mensaje);

            logger.info("Correo de recuperación de contraseña enviado a: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo de recuperación de contraseña a: {}", destinatario, e);
        }
    }

    private String construirEmailRecuperacionContrasena(String nombre, String codigo) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("    <meta charset='UTF-8'>");
        html.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("    <title>Recuperación de contraseña</title>");
        html.append("    <style>");
        html.append("        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f4f4f4; }");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; }");
        html.append("        .header { background: linear-gradient(135deg, #f97316 0%, #ea580c 100%); color: white; padding: 40px 30px; text-align: center; }");
        html.append("        .header h1 { margin: 0; font-size: 26px; font-weight: bold; }");
        html.append("        .header p { margin: 10px 0 0 0; opacity: 0.9; }");
        html.append("        .content { padding: 40px 30px; }");
        html.append("        .greeting { color: #ea580c; font-size: 22px; margin-bottom: 18px; }");
        html.append("        .code-box { background: #fff7ed; border-radius: 10px; padding: 20px; text-align: center; border: 1px solid #fed7aa; margin: 20px 0; }");
        html.append("        .code { font-size: 32px; letter-spacing: 6px; font-weight: bold; color: #c2410c; }");
        html.append("        .footer { background: #1f2937; color: white; padding: 24px; text-align: center; font-size: 13px; }");
        html.append("    </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("    <div class='container'>");
        html.append("        <div class='header'>");
        html.append("            <h1>Recuperación de contraseña</h1>");
        html.append("            <p>Hemos recibido una solicitud para restablecer tu contraseña</p>");
        html.append("        </div>");
        html.append("        <div class='content'>");
        html.append("            <div class='greeting'>Hola ").append(nombre).append("!</div>");
        html.append("            <p>Utiliza el siguiente código para continuar con el proceso de recuperación de tu contraseña en JUSTME:</p>");
        html.append("            <div class='code-box'>");
        html.append("                <div class='code'>").append(codigo).append("</div>");
        html.append("            </div>");
        html.append("            <p>Si no solicitaste este cambio, puedes ignorar este correo. Tu contraseña actual seguirá siendo válida.</p>");
        html.append("        </div>");
        html.append("        <div class='footer'>");
        html.append("            <p>&copy; 2025 JUSTME. Todos los derechos reservados.</p>");
        html.append("            <p>Este es un correo automático, por favor no respondas a este mensaje.</p>");
        html.append("        </div>");
        html.append("    </div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * Construye el cuerpo del correo de bienvenida.
     *
     * @param nombre Nombre del usuario.
     * @return Cuerpo del correo de bienvenida.
     */
    private String construirEmailBienvenida(String nombre) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("    <meta charset='UTF-8'>");
        html.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("    <title>Bienvenido a JUSTME</title>");
        html.append("    <style>");
        html.append(
                "        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f4f4f4; }");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; }");
        html.append(
                "        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 40px 30px; text-align: center; }");
        html.append("        .header h1 { margin: 0; font-size: 28px; font-weight: bold; }");
        html.append("        .header p { margin: 10px 0 0 0; opacity: 0.9; }");
        html.append("        .content { padding: 40px 30px; }");
        html.append("        .welcome-text { font-size: 16px; margin-bottom: 25px; }");
        html.append(
                "        .features { background: #f8f9fa; padding: 25px; border-radius: 10px; margin: 25px 0; border: 1px solid #e9ecef; }");
        html.append("        .feature-item { display: flex; align-items: center; margin: 12px 0; }");
        html.append(
                "        .feature-icon { background: #667eea; color: white; width: 24px; height: 24px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-right: 12px; font-size: 12px; }");
        html.append("        .footer { background: #2c3e50; color: white; padding: 30px; text-align: center; }");
        html.append(
                "        .greeting { color: #667eea; font-size: 24px; margin-bottom: 20px; font-weight: bold; }");
        html.append("    </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("    <div class='container'>");
        html.append("        <div class='header'>");
        html.append("            <h1>¡Bienvenido a JUSTME!</h1>");
        html.append("            <p>Tu plataforma de servicios confiable</p>");
        html.append("        </div>");
        html.append("        <div class='content'>");
        html.append("            <div class='greeting'>Hola ").append(nombre).append("!</div>");
        html.append(
                "            <div class='welcome-text'>");
        html.append(
                "                <p>Estamos emocionados de darte la bienvenida a nuestra comunidad. En <strong>JUSTME</strong> conectamos a personas como tú con profesionales verificados para hacer tu vida más fácil.</p>");
        html.append("            </div>");
        html.append("            <div class='features'>");
        html.append(
                "                <h3 style='color: #667eea; margin-top: 0; text-align: center;'>¿Qué puedes hacer en JUSTME?</h3>");
        html.append("                <div class='feature-item'>");
        html.append("                    <div class='feature-icon'>✓</div>");
        html.append(
                "                    <span><strong>Encontrar profesionales verificados para tus necesidades</strong></span>");
        html.append("                </div>");
        html.append("                <div class='feature-item'>");
        html.append("                    <div class='feature-icon'>✓</div>");
        html.append("                    <span><strong>Agendar servicios de forma fácil y rápida</strong></span>");
        html.append("                </div>");
        html.append("                <div class='feature-item'>");
        html.append("                    <div class='feature-icon'>✓</div>");
        html.append("                    <span><strong>Pagos seguros y transparentes</strong></span>");
        html.append("                </div>");
        html.append("            </div>");
        html.append("            <div style='text-align: center;'>");
        html.append("                <p><strong>¡Estamos aquí para ayudarte en lo que necesites!</strong></p>");
        html.append("            </div>");
        html.append("        </div>");
        html.append("        <div class='footer'>");
        html.append("            <p>&copy; 2025 JUSTME. Todos los derechos reservados.</p>");
        html.append("            <p>Este es un correo automático, por favor no respondas a este mensaje.</p>");
        html.append("        </div>");
        html.append("    </div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * Construye el cuerpo del correo de confirmación de pago.
     *
     * @param nombre     Nombre del usuario.
     * @param servicio   Nombre del servicio.
     * @param monto      Monto pagado.
     * @param estado     Estado del pago.
     * @return Cuerpo del correo de confirmación de pago.
     */
    private String construirEmailConfirmacionPago(String nombre, String servicio, String monto, String estado) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("    <meta charset='UTF-8'>");
        html.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("    <title>Confirmación de Pago</title>");
        html.append("    <style>");
        html.append(
                "        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f4f4f4; }");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; }");
        html.append(
                "        .header { background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%); color: white; padding: 40px 30px; text-align: center; }");
        html.append("        .header h1 { margin: 0; font-size: 28px; font-weight: bold; }");
        html.append("        .header p { margin: 10px 0 0 0; opacity: 0.9; }");
        html.append("        .content { padding: 40px 30px; }");
        html.append(
                "        .details-card { background: #f8f9fa; padding: 25px; border-radius: 10px; margin: 25px 0; border-left: 5px solid #4CAF50; }");
        html.append(
                "        .detail-row { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid #dee2e6; }");
        html.append("        .detail-label { font-weight: bold; color: #555; }");
        html.append("        .detail-value { color: #333; font-weight: 500; }");
        html.append(
                "        .status-badge { background: #4CAF50; color: white; padding: 6px 16px; border-radius: 20px; font-size: 14px; font-weight: bold; }");
        html.append(
                "        .next-steps { background: #e8f5e8; padding: 25px; border-radius: 10px; margin: 25px 0; border: 1px solid #d4edda; }");
        html.append("        .footer { background: #2c3e50; color: white; padding: 30px; text-align: center; }");
        html.append("        .greeting { color: #4CAF50; font-size: 24px; margin-bottom: 20px; }");
        html.append("        .amount { color: #4CAF50; font-size: 18px; font-weight: bold; }");
        html.append("    </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("    <div class='container'>");
        html.append("        <div class='header'>");
        html.append("            <h1>Pago Confirmado</h1>");
        html.append("            <p>Tu servicio ha sido agendado exitosamente</p>");
        html.append("        </div>");
        html.append("        <div class='content'>");
        html.append("            <div class='greeting'>Hola ").append(nombre).append("!</div>");
        html.append(
                "            <p>¡Gracias por confiar en <strong>JUSTME</strong>! Tu pago ha sido procesado exitosamente y tu servicio ha sido confirmado.</p>");
        html.append("            <div class='details-card'>");
        html.append("                <h3 style='color: #4CAF50; margin-top: 0; text-align: center;'>Detalles del Servicio</h3>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Servicio:</span>");
        html.append("                    <span class='detail-value'>").append(servicio).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Monto pagado:</span>");
        html.append("                    <span class='amount'>").append(monto).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Estado del pago:</span>");
        html.append("                    <span class='status-badge'>").append(estado).append("</span>");
        html.append("                </div>");
        html.append("            </div>");
        html.append("            <div class='next-steps'>");
        html.append(
                "                <h4 style='color: #4CAF50; margin-top: 0; text-align: center;'>Próximos Pasos</h4>");
        html.append(
                "                <p>En breve uno de nuestros profesionales se pondrá en contacto contigo</p>");
        html.append(
                "                <p>Coordinarán los detalles específicos del servicio</p>");
        html.append(
                "                <p>Recibirás confirmación de la fecha y hora acordadas</p>");
        html.append("            </div>");
        html.append("            <div style='text-align: center; margin-top: 30px;'>");
        html.append("                <p><strong>¿Tienes preguntas? Estamos aquí para ayudarte.</strong></p>");
        html.append("                <p>Equipo <strong>JUSTME</strong></p>");
        html.append("            </div>");
        html.append("        </div>");
        html.append("        <div class='footer'>");
        html.append("            <p>&copy; 2025 JUSTME. Todos los derechos reservados.</p>");
        html.append("            <p>Este es un correo automático de confirmación.</p>");
        html.append("        </div>");
        html.append("    </div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * Construye el cuerpo del correo de nueva cita para el profesional.
     *
     * @param nombreProfesional Nombre del profesional.
     * @param nombreCliente    Nombre del cliente.
     * @param servicio         Nombre del servicio.
     * @param fecha            Fecha de la cita.
     * @param direccion        Dirección de la cita.
     * @return Cuerpo del correo de nueva cita para el profesional.
     */
    private String construirEmailNuevaCitaProfesional(String nombreProfesional, String nombreCliente,
            String servicio, String fecha, String direccion) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("    <meta charset='UTF-8'>");
        html.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("    <title>Nueva reserva en tu servicio</title>");
        html.append("    <style>");
        html.append(
                "        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f4f4f4; }");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; }");
        html.append(
                "        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 40px 30px; text-align: center; }");
        html.append("        .header h1 { margin: 0; font-size: 26px; font-weight: bold; }");
        html.append("        .header p { margin: 10px 0 0 0; opacity: 0.9; }");
        html.append("        .content { padding: 40px 30px; }");
        html.append("        .greeting { color: #667eea; font-size: 22px; margin-bottom: 18px; }");
        html.append(
                "        .details-card { background: #f8f9fa; padding: 22px; border-radius: 10px; margin: 20px 0; border-left: 5px solid #667eea; }");
        html.append(
                "        .detail-row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #dee2e6; font-size: 14px; }");
        html.append("        .detail-label { font-weight: bold; color: #555; }");
        html.append("        .detail-value { color: #333; font-weight: 500; }");
        html.append("        .footer { background: #2c3e50; color: white; padding: 24px; text-align: center; font-size: 13px; }");
        html.append("    </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("    <div class='container'>");
        html.append("        <div class='header'>");
        html.append("            <h1>Tienes una nueva reserva</h1>");
        html.append("            <p>Un cliente ha reservado uno de tus servicios en JUSTME</p>");
        html.append("        </div>");
        html.append("        <div class='content'>");
        html.append("            <div class='greeting'>Hola ").append(nombreProfesional).append("!</div>");
        html.append("            <p>Te informamos que <strong>").append(nombreCliente)
                .append("</strong> ha realizado una nueva reserva en uno de tus servicios.</p>");
        html.append("            <div class='details-card'>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Servicio:</span>");
        html.append("                    <span class='detail-value'>").append(servicio).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Cliente:</span>");
        html.append("                    <span class='detail-value'>").append(nombreCliente).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Fecha y hora:</span>");
        html.append("                    <span class='detail-value'>").append(fecha).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Dirección:</span>");
        html.append("                    <span class='detail-value'>").append(direccion).append("</span>");
        html.append("                </div>");
        html.append("            </div>");
        html.append(
                "            <p style='margin-top: 18px;'>Te recomendamos ponerte en contacto con el cliente para confirmar cualquier detalle adicional del servicio.</p>");
        html.append("        </div>");
        html.append("        <div class='footer'>");
        html.append("            <p>&copy; 2025 JUSTME. Todos los derechos reservados.</p>");
        html.append("            <p>Este es un correo automático, por favor no respondas a este mensaje.</p>");
        html.append("        </div>");
        html.append("    </div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * Construye el cuerpo del correo de nueva cita para el usuario.
     *
     * @param nombreUsuario    Nombre del usuario.
     * @param nombreProfesional Nombre del profesional.
     * @param servicio         Nombre del servicio.
     * @param fecha            Fecha de la cita.
     * @param direccion        Dirección de la cita.
     * @return Cuerpo del correo de nueva cita para el usuario.
     */
    private String construirEmailNuevaCitaUsuario(String nombreUsuario, String nombreProfesional, String servicio,
            String fecha, String direccion) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("    <meta charset='UTF-8'>");
        html.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("    <title>Nueva reserva realizada</title>");
        html.append("    <style>");
        html.append(
                "        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f4f4f4; }");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; }");
        html.append(
                "        .header { background: linear-gradient(135deg, #3b82f6 0%, #6366f1 100%); color: white; padding: 40px 30px; text-align: center; }");
        html.append("        .header h1 { margin: 0; font-size: 26px; font-weight: bold; }");
        html.append("        .header p { margin: 10px 0 0 0; opacity: 0.9; }");
        html.append("        .content { padding: 40px 30px; }");
        html.append("        .greeting { color: #3b82f6; font-size: 22px; margin-bottom: 18px; }");
        html.append(
                "        .details-card { background: #eff6ff; padding: 22px; border-radius: 10px; margin: 20px 0; border-left: 5px solid #3b82f6; }");
        html.append(
                "        .detail-row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #dbeafe; font-size: 14px; }");
        html.append("        .detail-label { font-weight: bold; color: #555; }");
        html.append("        .detail-value { color: #111827; font-weight: 500; }");
        html.append("        .footer { background: #1f2937; color: white; padding: 24px; text-align: center; font-size: 13px; }");
        html.append("    </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("    <div class='container'>");
        html.append("        <div class='header'>");
        html.append("            <h1>Has realizado una nueva reserva</h1>");
        html.append("            <p>Tu cita está en espera de confirmación por parte del profesional</p>");
        html.append("        </div>");
        html.append("        <div class='content'>");
        html.append("            <div class='greeting'>Hola ").append(nombreUsuario).append("!</div>");
        html.append(
                "            <p>Hemos registrado tu reserva y está pendiente de que el profesional la confirme. Aquí tienes los detalles de tu cita:</p>");
        html.append("            <div class='details-card'>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Servicio:</span>");
        html.append("                    <span class='detail-value'>").append(servicio).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Profesional:</span>");
        html.append("                    <span class='detail-value'>").append(nombreProfesional).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Fecha y hora:</span>");
        html.append("                    <span class='detail-value'>").append(fecha).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Dirección:</span>");
        html.append("                    <span class='detail-value'>").append(direccion).append("</span>");
        html.append("                </div>");
        html.append("            </div>");
        html.append(
                "            <p style='margin-top: 18px;'>Cuando tu profesional confirme la cita, recibirás un nuevo correo de confirmación.</p>");
        html.append("        </div>");
        html.append("        <div class='footer'>");
        html.append("            <p>&copy; 2025 JUSTME. Todos los derechos reservados.</p>");
        html.append("            <p>Este es un correo automático, por favor no respondas a este mensaje.</p>");
        html.append("        </div>");
        html.append("    </div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    // Cuerpo del correo cuando la cita está confirmada
    private String construirEmailCitaConfirmadaUsuario(String nombreUsuario, String nombreProfesional, String servicio,
            String fecha, String direccion) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("    <meta charset='UTF-8'>");
        html.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("    <title>Cita confirmada</title>");
        html.append("    <style>");
        html.append(
                "        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f4f4f4; }");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; }");
        html.append(
                "        .header { background: linear-gradient(135deg, #10b981 0%, #059669 100%); color: white; padding: 40px 30px; text-align: center; }");
        html.append("        .header h1 { margin: 0; font-size: 26px; font-weight: bold; }");
        html.append("        .header p { margin: 10px 0 0 0; opacity: 0.9; }");
        html.append("        .content { padding: 40px 30px; }");
        html.append("        .greeting { color: #10b981; font-size: 22px; margin-bottom: 18px; }");
        html.append(
                "        .details-card { background: #ecfdf5; padding: 22px; border-radius: 10px; margin: 20px 0; border-left: 5px solid #10b981; }");
        html.append(
                "        .detail-row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #d1fae5; font-size: 14px; }");
        html.append("        .detail-label { font-weight: bold; color: #047857; }");
        html.append("        .detail-value { color: #064e3b; font-weight: 500; }");
        html.append("        .footer { background: #064e3b; color: white; padding: 24px; text-align: center; font-size: 13px; }");
        html.append("    </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("    <div class='container'>");
        html.append("        <div class='header'>");
        html.append("            <h1>Tu cita ha sido confirmada</h1>");
        html.append("            <p>¡Todo está listo para tu servicio en JUSTME!</p>");
        html.append("        </div>");
        html.append("        <div class='content'>");
        html.append("            <div class='greeting'>Hola ").append(nombreUsuario).append("!</div>");
        html.append(
                "            <p>Tu profesional ha confirmado la cita. Revisa los detalles a continuación para que no se te escape nada:</p>");
        html.append("            <div class='details-card'>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Servicio:</span>");
        html.append("                    <span class='detail-value'>").append(servicio).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Profesional:</span>");
        html.append("                    <span class='detail-value'>").append(nombreProfesional).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Fecha y hora:</span>");
        html.append("                    <span class='detail-value'>").append(fecha).append("</span>");
        html.append("                </div>");
        html.append("                <div class='detail-row'>");
        html.append("                    <span class='detail-label'>Dirección:</span>");
        html.append("                    <span class='detail-value'>").append(direccion).append("</span>");
        html.append("                </div>");
        html.append("            </div>");
        html.append(
                "            <p style='margin-top: 18px;'>Te recomendamos estar listo unos minutos antes de la hora programada para aprovechar al máximo tu servicio.</p>");
        html.append("        </div>");
        html.append("        <div class='footer'>");
        html.append("            <p>&copy; 2025 JUSTME. Todos los derechos reservados.</p>");
        html.append("            <p>Este es un correo automático, por favor no respondas a este mensaje.</p>");
        html.append("        </div>");
        html.append("    </div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
}