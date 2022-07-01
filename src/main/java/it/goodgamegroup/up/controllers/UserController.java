package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.dto.UserDTO;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.services.UserDefaultService;
import it.goodgamegroup.up.services.dao.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<User> getAll(){
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable(name = "id") String stringId){
        return this.userService.get(UUID.fromString(stringId));
    }

    //Aggiunta di un nuovo utente
    @PutMapping
    public User put(@RequestBody UserDTO userDTO){
        return this.userService.put(userDTO);
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
