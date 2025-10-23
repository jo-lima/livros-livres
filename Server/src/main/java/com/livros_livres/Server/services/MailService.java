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

        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setTo(address);
        mailMsg.setSubject(subject);
        mailMsg.setText(
            msg
        );
        try{
            this.mailSender.send(mailMsg);
        }
        catch(MailException ex) {
            System.err.println(ex.getMessage());
            return RetornoApi.errorInternal();
        }

        return RetornoApi.sucess("Sucesso!");
    }
}