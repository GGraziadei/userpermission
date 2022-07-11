package it.goodgamegroup.up.services.dao;

import it.goodgamegroup.up.dto.PermissionDTO;
import it.goodgamegroup.up.entities.Permission;
import it.goodgamegroup.up.entities.PermissionId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionDAO extends  DaoPattern<Permission , PermissionId> {
     Permission update(PermissionDTO permissionDTO);
     List<Permission> getValidPermission();
     List<Permission> getInvalidPermission();
}
