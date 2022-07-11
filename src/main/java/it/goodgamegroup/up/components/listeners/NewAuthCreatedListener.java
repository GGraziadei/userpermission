package it.goodgamegroup.up.components.listeners;

import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.events.NewAuthCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NewAuthCreatedListener implements ApplicationListener<NewAuthCreated> {

    @Autowired
    private JavaMailSender emailSender;

    private UserAuthentication userAuthentication;
    private User user;

    @Override
    public void onApplicationEvent(NewAuthCreated event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(NewAuthCreated event) {
        this.userAuthentication = event.getUserAuthentication();
        this.user = this.userAuthentication.getUser();
        String token = UUID.randomUUID().toString();
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String message = "Codice di verifica registrazione accout: ";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setFrom("noreply@userpermission.io");
        email.setText(message + "\r\n" + "http://localhost:8080" );
        emailSender.send(email);
    }
}
