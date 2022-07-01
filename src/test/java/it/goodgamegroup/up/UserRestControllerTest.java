package it.goodgamegroup.up;

import com.github.javafaker.Faker;
import it.goodgamegroup.up.controllers.UserController;
import it.goodgamegroup.up.dto.UserDTO;
import it.goodgamegroup.up.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {

    @Autowired
    private UserController userController;

    private final Faker faker = new Faker(new Locale("it-IT"));

    private User user;

    @LocalServerPort
    private int port;

    @BeforeEach
    void assertNotNull(){
       Assertions.assertNotNull(this.faker);
       Assertions.assertNotNull(this.userController);

       user = User.builder()
                .firstName(this.faker.name().firstName())
                .lastName(this.faker.name().lastName())
                .fiscalCode(this.faker.idNumber().ssnValid())
                .email(this.faker.bothify("????##@gmail.com"))
                .phoneNumber(this.faker.phoneNumber().phoneNumber())
                .build();
       //this.userController.put(user);
       System.out.println("User added: {} " + user);
    }

    @Test
    public void getAllUser(){
        List<User> users =  this.userController.getAll();
        Map<String, User> userMap = users.stream()
                        .collect(Collectors.toMap(
                                u -> u.getId().toString(),
                                u -> u
                        ));
        Assertions.assertTrue(userMap.containsKey(this.user.getId().toString()));
    }

    @Test
    public void getUser(){
        User user1 =  this.userController.getUser(this.user.getId().toString()).get();
        Assertions.assertNotNull(user1);
    }


}
