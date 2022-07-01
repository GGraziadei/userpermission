package it.goodgamegroup.up;

import com.github.javafaker.Faker;
import it.goodgamegroup.up.controllers.PermissionController;
import it.goodgamegroup.up.controllers.UserController;
import it.goodgamegroup.up.entities.Permission;
import it.goodgamegroup.up.entities.PermissionId;
import it.goodgamegroup.up.entities.Type;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.exceptions.EntitiesException;
import it.goodgamegroup.up.services.dao.PermissionDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PermissionRestControllerTest {

    @Autowired
    private PermissionController permissionController;

    @Autowired
    private UserController userController;

    private Permission permission;

    private final Faker faker = new Faker(new Locale("it-IT"));

    private final User user =  User.builder()
            .firstName(this.faker.name().firstName())
            .lastName(this.faker.name().lastName())
            .fiscalCode(this.faker.idNumber().ssnValid())
            .email(this.faker.bothify("????##@gmail.com"))
            .phoneNumber(this.faker.phoneNumber().phoneNumber())
            .build();


    @LocalServerPort
    private int port;


    @BeforeEach
    void assertNotNull() throws EntitiesException {
       Assertions.assertNotNull(this.faker);
       Assertions.assertNotNull(this.userController);
       Assertions.assertNotNull(this.permissionController);
       Assertions.assertNotNull(this.user);

        //this.userController.put(user);
       // this.permission = this.permissionController.addPermission(LocalDateTime.now(), this.user.getId().toString());
        System.out.println(permission);
    }

    @Test
    public void getAllPermissions(){
        List<UUID> users =  this.permissionController.getAll()
                .stream()
                .map(Permission::getId)
                .map(PermissionId::getUserId)
                .collect(Collectors.toList());

        Assertions.assertTrue(users.contains(this.permission.getId().getUserId()));
    }

    
}
