package it.goodgamegroup.up.entities.reports;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserJoinPermissionId implements Serializable {

    private UUID permissionId;

    private UUID userId;

    public UserJoinPermissionId(UUID permissionId, UUID userId) {
        this.permissionId = permissionId;
        this.userId = userId;
    }

    public UserJoinPermissionId() {
    }

    public UUID getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(UUID permissionId) {
        this.permissionId = permissionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJoinPermissionId that = (UserJoinPermissionId) o;
        return Objects.equals(permissionId, that.permissionId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, userId);
    }
}
