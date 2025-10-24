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
        System.out.println("Iniciando envio de email.");

        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setTo(address);
        mailMsg.setSubject(subject);
        mailMsg.setText(msg);
        try{
            System.out.println("Email preparado, indo enviar.");
            this.mailSender.send(mailMsg);
        }
        catch(MailException ex) {
            System.out.println("Erro ao enviar o email.");
            return RetornoApi.error(400, "Ocorreu um erro ao enviar email.");
        }

        System.out.println("Email enviado com sucesso!");
        return RetornoApi.sucess("Email enviado com sucesso!");
    }
}