package com.chilo_tech.demo.service.utils;

import com.chilo_tech.demo.entity.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void notification(Validation validation){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("no-reply@defoTech.com");
        simpleMailMessage.setTo(validation.getUtilisateur().getEmail());
        simpleMailMessage.setSubject("Votre code d'activation");

        String texte = String.format(
                "Bonjour %s, <br/> Votre code d'activation est %s. A bientot",
                validation.getUtilisateur().getNomComplet(),
                validation.getCode()
         );

        simpleMailMessage.setText(texte);

        javaMailSender.send(simpleMailMessage);

    }

}
