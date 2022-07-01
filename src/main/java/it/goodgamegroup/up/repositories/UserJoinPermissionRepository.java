package it.goodgamegroup.up.repositories;

import it.goodgamegroup.up.entities.reports.UserJoinPermission;
import it.goodgamegroup.up.entities.reports.UserJoinPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserJoinPermissionRepository extends JpaRepository<UserJoinPermission , UserJoinPermissionId> {

    List<UserJoinPermission> findByUserId(UUID userId);

    List<UserJoinPermission> findByPermissionId(UUID PermissionId);

}
