package it.goodgamegroup.up.repositories;

import it.goodgamegroup.up.entities.Permission;
import it.goodgamegroup.up.entities.PermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, PermissionId> {

}
