package com.livros_livres.Server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public RetornoApi sendMail(String msg, String subject, String address) {
        DebugService.log("Iniciando envio de email.");

        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setTo(address);
            helper.setSubject(subject);
            helper.setText(msg, true);

            DebugService.log("Email preparado, indo enviar (HTML).");
            this.mailSender.send(message);
}
        catch (MessagingException | MailException ex) {
            DebugService.log("Erro ao enviar o email: " + ex.getMessage());
            return RetornoApi.error(500, "Ocorreu um erro no sistema ao enviar email.");
}
        DebugService.log("Email enviado com sucesso!");
        return RetornoApi.sucess("Tentativa de envio de email efetuada com sucesso!");
    }
}