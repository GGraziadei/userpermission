package it.goodgamegroup.up.services.dao;

import it.goodgamegroup.up.dto.PermissionDTO;
import it.goodgamegroup.up.entities.Permission;
import it.goodgamegroup.up.entities.PermissionId;
import org.springframework.stereotype.Service;

@Service
public interface PermissionDAO extends  DaoPattern<Permission , PermissionId> {
     Permission update(PermissionDTO permissionDTO);
}
