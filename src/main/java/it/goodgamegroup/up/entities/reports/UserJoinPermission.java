package it.goodgamegroup.up.entities.reports;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Immutable
@IdClass(UserJoinPermissionId.class)
@Table(name = "user_join_permission")
public class UserJoinPermission {

    @Id
    @Column(name = "PERMISSION_ID", nullable = false, length = 16)
    private UUID permissionId;

    @Id
    @Column(name = "USER_ID", nullable = false, length = 16)
    private UUID userId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "FISCAL_CODE", nullable = false)
    private String fiscalCode;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "TS_START")
    private Instant tsStart;

    @Column(name = "TS_END")
    private Instant tsEnd;

}