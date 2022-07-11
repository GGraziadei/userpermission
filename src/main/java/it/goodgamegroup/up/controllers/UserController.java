package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.configurations.Constant;
import it.goodgamegroup.up.dto.UserDTO;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.events.NewAuthCreated;
import it.goodgamegroup.up.mappers.UserMapper;
import it.goodgamegroup.up.services.UserDefaultService;
import it.goodgamegroup.up.services.dao.UserDAO;
import it.goodgamegroup.up.services.task.AddUser;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserDefaultService userService;

    @Autowired
    private AddUser addUser;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public List<User> getAll(){
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable(name = "id") String stringId){
        return this.userService.get(UUID.fromString(stringId));
    }

    @PutMapping
    public User put(@RequestBody UserDTO userDTO){
        User user = new User();
        UserAuthentication userAuthentication = new UserAuthentication();
        userMapper.updateUserFromDTO(userDTO, user);
        userAuthentication.setUserName(user.getEmail());
        userAuthentication.setPassword(passwordEncoder.encode(user.getFiscalCode()));
        userAuthentication.setActive(false);
        userAuthentication.setRoles(Constant.USER);
        userAuthentication.setUser(user);
        user.setUserAuthentication(userAuthentication);
        this.userService.put(user);
        NewAuthCreated event = new NewAuthCreated(userAuthentication);
        this.eventPublisher.publishEvent(event);
        return user;
    }
    
    @PutMapping("/{id}")
    public User update(@RequestBody UserDTO user , @PathVariable String id){
        assert  id.equals(user.getId().toString());
        return  this.userService.update(user);
    }


    @DeleteMapping
    public User deleteUser(@RequestBody UserDTO userDTO){
        User user = this.userService.get(userDTO.getId()).orElseThrow();
        this.userService.delete(user);
        return user;
    }


}
