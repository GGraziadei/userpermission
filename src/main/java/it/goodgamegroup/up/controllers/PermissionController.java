package it.goodgamegroup.up.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import it.goodgamegroup.up.dto.PermissionDTO;
import it.goodgamegroup.up.entities.*;
import it.goodgamegroup.up.services.dao.PermissionDAO;
import it.goodgamegroup.up.services.dao.PermissionTypeDAO;
import it.goodgamegroup.up.services.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME = "time";
    public static final String PERMISSION_TYPE_ID = "permissionTypeId";
    @Autowired
    private PermissionDAO permissionService;

    @Autowired
    private UserDAO userService;

    @Autowired
    private PermissionTypeDAO permissionTypService;


    @GetMapping
    public List<Permission> getAll(){
        return this.permissionService.getAll();
    }

    @GetMapping("valid-permissions")
    public List<Permission> getValidPermission(){
        return this.permissionService.getValidPermission();
    }

    @GetMapping("invalid-permissions")
    public List<Permission> getInvalidPermission(){
        return this.permissionService.getInvalidPermission();
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
    public Permission addPermission(@RequestBody ObjectNode json,
                                    @PathVariable(name = "userId") String userId){
        User user = this.userService.get(UUID.fromString(userId))
                .orElseThrow();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        LocalDateTime localDateTime = LocalDateTime.parse(json.get(TIME).asText(), formatter);

        Long permissionTypeId = json.get(PERMISSION_TYPE_ID).asLong();
        PermissionType permissionType = this.permissionTypService.get(permissionTypeId).orElseThrow();

        PermissionId permissionId = PermissionId.builder()
                .userId(user.getId())
                .permissionId(UUID.randomUUID())
                .build();
        Permission permission = Permission.builder()
                .isValidated(false)
                .permissionType(permissionType)
                .id(permissionId)
                .tsStart(localDateTime.toInstant(ZoneOffset.MIN))
                .user(user)
                .build();
        return this.permissionService.put(permission);
    }

    @DeleteMapping
    public Permission deletePermission(@RequestBody PermissionId permissionId){
        Permission permission = this.permissionService.get(permissionId).orElseThrow();
        this.permissionService.deleteById(permissionId);
        return permission;
    }

}
