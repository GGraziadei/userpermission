package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.entities.VerificationToken;
import it.goodgamegroup.up.repositories.UserAuthenticationRepository;
import it.goodgamegroup.up.repositories.UserRepository;
import it.goodgamegroup.up.repositories.VerificationTokenRepository;
import it.goodgamegroup.up.services.dao.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user-confirm")
@Slf4j
public class UserVerificationController {

    public static final String USER_ID = "userId";
    public static final String CONFIRMATION_CODE = "confirmationCode";

    @Autowired
    private UserDAO userService;

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @GetMapping
    public User confirmUser(@RequestParam Map<String,String> allParams){
        String userId = allParams.get(USER_ID);
        String confirmationCode = allParams.get(CONFIRMATION_CODE);
        assert userId != null;
        assert confirmationCode != null;
        User user = this.userService.get(UUID.fromString(userId)).orElseThrow();
        UserAuthentication userAuthentication = user.getUserAuthentication();
        VerificationToken verificationToken = userAuthentication.getVerificationTokenSet()
                .stream()
                .filter(t -> t.getToken().equals(confirmationCode) )
                .findFirst().orElseThrow();
        if(verificationToken.getExpiryDate().after(new Date())) {
            userAuthentication.setActive(true);
            userAuthenticationRepository.save(userAuthentication);
        }
        return  user;
    }
}
