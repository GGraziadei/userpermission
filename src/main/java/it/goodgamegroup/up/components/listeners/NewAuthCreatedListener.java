package it.goodgamegroup.up.components.listeners;

import it.goodgamegroup.up.controllers.UserVerificationController;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.entities.VerificationToken;
import it.goodgamegroup.up.events.NewAuthCreated;
import it.goodgamegroup.up.services.UserDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class NewAuthCreatedListener implements ApplicationListener<NewAuthCreated> {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserDefaultService userService;


    @Override
    public void onApplicationEvent(NewAuthCreated event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(NewAuthCreated event) {
        UserAuthentication userAuthentication = event.getUserAuthentication();
        User user = userAuthentication.getUser();
        VerificationToken verificationToken = userAuthentication.getVerificationTokenSet().stream()
                .findFirst().orElseThrow();

        String recipientAddress = user.getEmail();
        String subject = "Account registrato con successo";
        String message = "Nuovo account creato " +
                "\r\nnome " + user.getFirstName() +
                "\r\ncognome " + user.getLastName() +
                "\r\ncodice fiscale " + user.getFiscalCode() +
                "\r\nCodice di verifica registrazione accout: " + verificationToken.getToken();
        String url = "http://localhost:8080/user-confirm/?" + UserVerificationController.USER_ID + "=" +user.getId()+ "&"
                + UserVerificationController.CONFIRMATION_CODE + "=" +verificationToken.getToken();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setFrom("noreply@userpermission.io");
        email.setText(message + "\r\n" + url );
        emailSender.send(email);
    }
}
