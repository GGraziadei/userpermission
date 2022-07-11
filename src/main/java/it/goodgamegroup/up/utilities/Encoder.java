package it.goodgamegroup.up.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {

    public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

}
