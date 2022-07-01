package it.goodgamegroup.up.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@DynamicUpdate
@Table(name = "up_user_authentication")
public class UserAuthentication {
    @Id
    @Column(name = "USER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private String password;

    @Column(name = "ROLES", nullable = false)
    private String roles;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "TS_CREATE", nullable = false)
    @JsonIgnore
    private Instant tsCreate;

    @Column(name = "TS_DELETE")
    @JsonIgnore
    private Instant tsDelete;

    @Column(name = "TS_UPDATE", nullable = false)
    @JsonIgnore
    private Instant tsUpdate;

}