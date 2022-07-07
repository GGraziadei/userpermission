package it.goodgamegroup.up.dto;

import it.goodgamegroup.up.entities.Permission;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class PermissionTypeDTO {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    private Instant tsCreate;
    private Instant tsDelete;
    private Instant tsUpdate;
    private Set<Permission> permissions;
}
