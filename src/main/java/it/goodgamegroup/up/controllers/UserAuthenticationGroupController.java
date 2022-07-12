package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.dto.UserAuthenticationGroupDTO;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.entities.UserAuthenticationGroup;
import it.goodgamegroup.up.exceptions.EntitiesException;
import it.goodgamegroup.up.services.dao.UserAuthenticationGroupDAO;
import it.goodgamegroup.up.services.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/groups")
public class UserAuthenticationGroupController {

    @Autowired
    private UserAuthenticationGroupDAO userAuthenticationGroupService;


    @Autowired
    private UserDAO userService;

    @GetMapping
    public List<UserAuthenticationGroup> getAll(){
        return this.userAuthenticationGroupService.getAll();
    }

    @GetMapping("/{userId}")
    public List<UserAuthenticationGroup> getAllByUser(@PathVariable(name = "userId") String userId){
        User user = this.userService.get(UUID.fromString(userId)).orElseThrow();
        UserAuthentication userAuthentication = user.getUserAuthentication();
        return new ArrayList<>(userAuthentication.getUserAuthenticationGroups());
    }

    @PutMapping
    public  UserAuthenticationGroup putGroup(@RequestBody UserAuthenticationGroupDTO dto){
        return this.userAuthenticationGroupService.put(dto);
    }

    @PostMapping("/{userId}")
    public User addUserToGroup(@PathVariable(name = "userId") String userId , @RequestBody UserAuthenticationGroupDTO dto) throws EntitiesException {
        if ( dto.getCode() == null){
            throw new EntitiesException("insert group code ");
        }
        User user = this.userService.get(UUID.fromString(userId)).orElseThrow();
        UserAuthentication userAuthentication = user.getUserAuthentication();
        UserAuthenticationGroup userAuthenticationGroup = this.userAuthenticationGroupService.getByCode(dto.getCode());
        userAuthentication.getUserAuthenticationGroups().add(userAuthenticationGroup);
        return this.userService.put(user);
    }

    @PutMapping("/{userId}")
    public User removeUserFromGroup(@PathVariable(name = "userId") String userId , @RequestBody UserAuthenticationGroupDTO dto) throws EntitiesException {
        if ( dto.getCode() == null){
            throw new EntitiesException("insert group code ");
        }
        User user = this.userService.get(UUID.fromString(userId)).orElseThrow();
        UserAuthentication userAuthentication = user.getUserAuthentication();
        UserAuthenticationGroup userAuthenticationGroup = this.userAuthenticationGroupService.getByCode(dto.getCode());
        if(! userAuthentication.getUserAuthenticationGroups().contains(userAuthenticationGroup)){
            throw new EntitiesException("user doesn't join  @ group code ");
        }
        userAuthentication.getUserAuthenticationGroups().remove(userAuthenticationGroup);
        return this.userService.put(user);
    }

}
