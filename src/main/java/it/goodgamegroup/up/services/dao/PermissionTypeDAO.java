package it.goodgamegroup.up.services.dao;

import it.goodgamegroup.up.dto.PermissionTypeDTO;
import it.goodgamegroup.up.entities.PermissionType;

public interface PermissionTypeDAO extends DaoPattern<PermissionType , Long> {
    PermissionType update(PermissionTypeDTO permissionTypeDTO);
}
