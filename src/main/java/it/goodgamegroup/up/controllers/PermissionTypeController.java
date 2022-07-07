package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.dto.PermissionTypeDTO;
import it.goodgamegroup.up.entities.PermissionType;
import it.goodgamegroup.up.services.dao.PermissionTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission-type")
public class PermissionTypeController {

    @Autowired
    private PermissionTypeDAO permissionTypService;

    @GetMapping
    public List<PermissionType> getAll(){
        return this.permissionTypService.getAll();
    }

    @GetMapping("/{permissionTypeId}")
    public PermissionType getById(@PathVariable(name = "permissionTypeId") Long id){
        return this.permissionTypService.get(id).orElseThrow();
    }

    @PutMapping
    public PermissionType putPermissionType(@RequestBody PermissionTypeDTO permissionTypeDTO){
        return this.permissionTypService.update(permissionTypeDTO);
    }

    @DeleteMapping
    public PermissionType deletePermissionType(@RequestBody Long id){
        PermissionType permissionType = this.permissionTypService.get(id).orElseThrow();
        this.permissionTypService.delete(permissionType);
        return permissionType;
    }
}
