package it.goodgamegroup.up.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "up_permission_type")
public class PermissionType {

    @Id
    @Column(name = "PERMISSION_TYPE_ID", nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 128 , unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTIVE", nullable = false )
    private boolean active;

    @Column(name = "TS_CREATE", nullable = false)
    @CreationTimestamp
    private Instant tsCreate;

    @Column(name = "TS_DELETE")
    private Instant tsDelete;

    @Column(name = "TS_UPDATE", nullable = false)
    @UpdateTimestamp
    private Instant tsUpdate;

    @OneToMany(mappedBy = "permissionType" , fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Permission> permissions = new LinkedHashSet<>();
}