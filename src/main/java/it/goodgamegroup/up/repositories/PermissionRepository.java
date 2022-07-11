package it.goodgamegroup.up.repositories;

import it.goodgamegroup.up.entities.Permission;
import it.goodgamegroup.up.entities.PermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, PermissionId> {
    List<Permission> findByIsValidatedIs(boolean isValidated);
}
