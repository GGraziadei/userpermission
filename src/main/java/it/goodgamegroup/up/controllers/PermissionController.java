package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.dto.PermissionDTO;
import it.goodgamegroup.up.entities.Permission;
import it.goodgamegroup.up.entities.PermissionId;
import it.goodgamegroup.up.entities.Type;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.services.dao.PermissionDAO;
import it.goodgamegroup.up.services.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionDAO permissionService;

    @Autowired
    private UserDAO userService;


    @GetMapping
    public List<Permission> getAll(){
        return this.permissionService.getAll();
    }

    //Ritorna la lista dei permessi che sono stati effettuati da un utente
    @GetMapping("/{userId}")
    public List<Permission> getAllFromUser(@PathVariable(name = "userId") String userId){
        User user = this.userService.get(UUID.fromString(userId))
                .orElseThrow();
        return new ArrayList<>(user.getPermissions());
    }

    @PutMapping
    public Permission updatePermission(@RequestBody PermissionDTO permissionDTO ){
        return  this.permissionService.update(permissionDTO);
    }

    @PutMapping("/{userId}")
    public Permission addPermission(@RequestBody LocalDateTime localDateTime ,
                              @PathVariable(name = "userId") String userId){
        User user = this.userService.get(UUID.fromString(userId))
                .orElseThrow();
        PermissionId permissionId = PermissionId.builder()
                .userId(user.getId())
                .permissionId(UUID.randomUUID())
                .build();
        Permission permission = Permission.builder()
                .isValidated(false)
                .type(Type.NONE)
                .id(permissionId)
                .tsStart(localDateTime.toInstant(ZoneOffset.MIN))
                .user(user)
                .build();
        this.permissionService.put(permission);
        return permission;
    }


    @DeleteMapping
    public Permission deletePermission(@RequestBody PermissionId permissionId){
        Permission permission = this.permissionService.get(permissionId).orElseThrow();
        this.permissionService.deleteById(permissionId);
        return permission;
    }

    //I permessi non possono essere cancellati ma solamente modificati nelllo stoato per evitare che ci sia un problema di integrità referenziale

}
