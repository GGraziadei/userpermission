package it.goodgamegroup.up.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@Table(name = "up_permission")
public class Permission {
    @EmbeddedId
    private PermissionId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private User user;

    @Column(name = "TS_START", nullable = false)
    private Instant tsStart;

    @Column(name = "TS_END")
    private Instant tsEnd;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TYPE", nullable = false)
    private Type type;

    @Column(name = "VALIDATED" , nullable = false)
    private boolean isValidated;


}