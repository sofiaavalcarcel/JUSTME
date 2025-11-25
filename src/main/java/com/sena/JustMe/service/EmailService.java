package com.sena.JustMe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoBienvenida(String destinatario, String nombre) {
        if (!validarDestinatario(destinatario)) {
            logger.warn("Destinatario no válido: {}", destinatario);
            return;
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            
            helper.setTo(destinatario);
            helper.setSubject("🎉 ¡Bienvenido a JUSTME!");

            String saludoNombre = (nombre != null && !nombre.isEmpty()) ? nombre : "Estimado usuario";
            String cuerpoHtml = construirEmailBienvenida(saludoNombre);

            helper.setText(cuerpoHtml, true);
            mailSender.send(mensaje);
            
            logger.info("Correo de bienvenida enviado exitosamente a: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo de bienvenida a: {}", destinatario, e);
        }
    }

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
            helper.setSubject("✅ Confirmación de Pago - JUSTME");

            String nombreMostrado = (nombre != null && !nombre.isEmpty()) ? nombre : "Estimado cliente";
            String servicioMostrado = (nombreServicio != null && !nombreServicio.isEmpty()) ? nombreServicio : "Servicio contratado";
            String montoTexto = String.format("%.2f %s", (monto != null ? monto : 0.0), (moneda != null ? moneda : ""));
            String estadoTexto = (estado != null && !estado.isEmpty()) ? estado : "Procesado";

            String cuerpoHtml = construirEmailConfirmacionPago(nombreMostrado, servicioMostrado, montoTexto, estadoTexto);

            helper.setText(cuerpoHtml, true);
            mailSender.send(mensaje);
            
            logger.info("Correo de confirmación de pago enviado exitosamente a: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo de confirmación de pago a: {}", destinatario, e);
        }
    }

    private boolean validarDestinatario(String destinatario) {
        return destinatario != null && !destinatario.isEmpty() && destinatario.contains("@");
    }

    private String construirEmailBienvenida(String nombre) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("    <meta charset='UTF-8'>");
        html.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("    <title>Bienvenido a JUSTME</title>");
        html.append("    <style>");
        html.append("        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f4f4f4; }");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; }");
        html.append("        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 40px 30px; text-align: center; }");
        html.append("        .header h1 { margin: 0; font-size: 28px; font-weight: bold; }");
        html.append("        .header p { margin: 10px 0 0 0; opacity: 0.9; }");
        html.append("        .content { padding: 40px 30px; }");
        html.append("        .welcome-text { font-size: 16px; margin-bottom: 25px; }");
        html.append("        .features { background: #f8f9fa; padding: 25px; border-radius: 10px; margin: 25px 0; border: 1px solid #e9ecef; }");
        html.append("        .feature-item { display: flex; align-items: center; margin: 12px 0; }");
        html.append("        .feature-icon { background: #667eea; color: white; width: 24px; height: 24px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-right: 12px; font-size: 12px; }");
        html.append("        .footer { background: #2c3e50; color: white; padding: 30px; text-align: center; }");
        html.append("        .btn-primary { background: #667eea; color: white; padding: 14px 35px; text-decoration: none; border-radius: 6px; display: inline-block; margin: 20px 0; font-weight: bold; font-size: 16px; }");
        html.append("        .greeting { color: #667eea; font-size: 24px; margin-bottom: 20px; }");
        html.append("    </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("    <div class='container'>");
        html.append("        <div class='header'>");
        html.append("            <h1>🎉 ¡Bienvenido a JUSTME!</h1>");
        html.append("            <p>Tu plataforma de servicios confiable</p>");
        html.append("        </div>");
        html.append("        <div class='content'>");
        html.append("            <div class='greeting'>Hola ").append(nombre).append("!</div>");
        html.append("            <div class='welcome-text'>");
        html.append("                <p>Estamos emocionados de darte la bienvenida a nuestra comunidad. En <strong>JUSTME</strong> conectamos a personas como tú con profesionales verificados para hacer tu vida más fácil.</p>");
        html.append("            </div>");
        html.append("            <div class='features'>");
        html.append("                <h3 style='color: #667eea; margin-top: 0; text-align: center;'>¿Qué puedes hacer en JUSTME?</h3>");
        html.append("                <div class='feature-item'>");
        html.append("                    <div class='feature-icon'>✓</div>");
        html.append("                    <span><strong>Encontrar profesionales verificados</strong></span>");
        html.append("                </div>");
        html.append("                <div class='feature-item'>");
        html.append("                    <div class='feature-icon'>✓</div>");
        html.append("                    <span><strong>Agendar servicios fácilmente</strong></span>");
        html.append("                </div>");
        html.append("                <div class='feature-item'>");
        html.append("                    <div class='feature-icon'>✓</div>");
        html.append("                    <span><strong>Pagos seguros y transparentes</strong></span>");
        html.append("                </div>");
        html.append("                <div class='feature-item'>");
        html.append("                    <div class='feature-icon'>✓</div>");
        html.append("                    <span><strong>Seguimiento en tiempo real</strong></span>");
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

    private String construirEmailConfirmacionPago(String nombre, String servicio, String monto, String estado) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("    <meta charset='UTF-8'>");
        html.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("    <title>Confirmación de Pago</title>");
        html.append("    <style>");
        html.append("        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f4f4f4; }");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; }");
        html.append("        .header { background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%); color: white; padding: 40px 30px; text-align: center; }");
        html.append("        .header h1 { margin: 0; font-size: 28px; font-weight: bold; }");
        html.append("        .header p { margin: 10px 0 0 0; opacity: 0.9; }");
        html.append("        .content { padding: 40px 30px; }");
        html.append("        .details-card { background: #f8f9fa; padding: 25px; border-radius: 10px; margin: 25px 0; border-left: 5px solid #4CAF50; }");
        html.append("        .detail-row { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid #dee2e6; }");
        html.append("        .detail-label { font-weight: bold; color: #555; }");
        html.append("        .detail-value { color: #333; font-weight: 500; }");
        html.append("        .status-badge { background: #4CAF50; color: white; padding: 6px 16px; border-radius: 20px; font-size: 14px; font-weight: bold; }");
        html.append("        .next-steps { background: #e8f5e8; padding: 25px; border-radius: 10px; margin: 25px 0; border: 1px solid #d4edda; }");
        html.append("        .footer { background: #2c3e50; color: white; padding: 30px; text-align: center; }");
        html.append("        .greeting { color: #4CAF50; font-size: 24px; margin-bottom: 20px; }");
        html.append("        .amount { color: #4CAF50; font-size: 18px; font-weight: bold; }");
        html.append("    </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("    <div class='container'>");
        html.append("        <div class='header'>");
        html.append("            <h1>✅ Pago Confirmado</h1>");
        html.append("            <p>Tu servicio ha sido agendado exitosamente</p>");
        html.append("        </div>");
        html.append("        <div class='content'>");
        html.append("            <div class='greeting'>Hola ").append(nombre).append("!</div>");
        html.append("            <p>¡Gracias por confiar en <strong>JUSTME</strong>! Tu pago ha sido procesado exitosamente y tu servicio ha sido confirmado.</p>");
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
        html.append("                <h4 style='color: #4CAF50; margin-top: 0; text-align: center;'>Próximos Pasos</h4>");
        html.append("                <p>🔹 <strong>En breve</strong> uno de nuestros profesionales se pondrá en contacto contigo</p>");
        html.append("                <p>🔹 <strong>Coordinarán</strong> los detalles específicos del servicio</p>");
        html.append("                <p>🔹 <strong>Recibirás</strong> confirmación de la fecha y hora acordadas</p>");
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
}