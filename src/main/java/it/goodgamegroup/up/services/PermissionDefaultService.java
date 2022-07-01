package it.goodgamegroup.up.services;

import it.goodgamegroup.up.dto.PermissionDTO;
import it.goodgamegroup.up.mappers.PermissionMapper;
import it.goodgamegroup.up.entities.Permission;
import it.goodgamegroup.up.entities.PermissionId;
import it.goodgamegroup.up.repositories.PermissionRepository;
import it.goodgamegroup.up.services.dao.PermissionDAO;
import it.goodgamegroup.up.services.dao.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
@Slf4j
public class PermissionDefaultService implements PermissionDAO {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserDAO userService;


    @Override
    public Optional<Permission> get(PermissionId id) {
        return this.permissionRepository.findById(id);
    }

    @Override
    public List<Permission> getAll() {
        return this.permissionRepository.findAll();
    }

    @Override
    public Permission put(Permission permission) {
        return this.permissionRepository.save(permission);
    }

    @Override
    public void update(Permission permission) {
        //
    }

    @Override
    public Permission update(PermissionDTO permissionDTO) {
        Permission permission = this.permissionRepository.getById(permissionDTO.getId());
        permissionMapper.updatePermissionFromDTO(permissionDTO, permission);
        return  this.permissionRepository.save(permission);
    }

    @Override
    public void delete(Permission permission) {
        this.permissionRepository.delete(permission);
    }

    @Override
    public void deleteById(PermissionId id) {
        this.permissionRepository.deleteById(id);
    }


}
