package co.edu.unbosque.proyectoia.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean enviarCorreo(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("programania694@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("Correo enviado correctamente a: " + to);
            return true;
        } catch (Exception e) {
            System.err.println("Error enviando correo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean enviarCorreoHTML(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("programania694@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            System.out.println("Correo enviado correctamente a: " + to);
            return true;
        } catch (Exception e) {
            System.err.println("Error enviando correo : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
