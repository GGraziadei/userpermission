package it.goodgamegroup.up.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionId implements Serializable {
    private static final long serialVersionUID = -5669489552570106953L;

    @Column(name = "USER_ID", nullable = false, length = 16)
    private UUID userId;

    @Column(name = "PERMISSION_ID", nullable = false, length = 16)
    private UUID permissionId;
}