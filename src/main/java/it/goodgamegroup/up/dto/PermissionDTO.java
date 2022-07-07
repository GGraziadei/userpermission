package it.goodgamegroup.up.dto;

import it.goodgamegroup.up.entities.PermissionId;
import it.goodgamegroup.up.entities.PermissionType;
import it.goodgamegroup.up.entities.User;
import lombok.Data;

import java.time.Instant;

@Data
public class PermissionDTO {
    private PermissionId id;
    private Instant tsStart;
    private Instant tsEnd;
    private String description;
    private PermissionType permissionType;
    private boolean isValidated;
    private User user;
}
