package it.goodgamegroup.up.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    @RequestMapping("/test")
    public ModelAndView all() {
        return new ModelAndView("test");
    }

    @GetMapping("/encode")
    public Map<String , String > all(@RequestBody String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Map<String , String > body = new HashMap<>();
        body.put("password" , password);
        body.put("encoded-password" ,  encoder.encode(password));
        return body;
    }

    @GetMapping("/timestamp")
    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }

}
