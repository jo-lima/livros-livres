package com.livros_livres.Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class MailService {

    @Autowired
    private MailSender mailSender;

    public RetornoApi sendMail(String msg, String subject, String address) {
        DebugService.log("Iniciando envio de email.");

        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setTo(address);
        mailMsg.setSubject(subject);
        mailMsg.setText(msg);
        try{
            DebugService.log("Email preparado, indo enviar.");
            this.mailSender.send(mailMsg);
        }
        catch(MailException ex) {
            DebugService.log("Erro ao enviar o email.");
            return RetornoApi.error(500, "Ocorreu um erro no sistema ao enviar email.");
        }

        DebugService.log("Email enviado com sucesso!");
        return RetornoApi.sucess("Tentativa de envio de email efetuada com sucesso!");
    }
}