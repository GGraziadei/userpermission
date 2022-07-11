package it.goodgamegroup.up.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.goodgamegroup.up.configurations.Constant;
import it.goodgamegroup.up.utilities.Encoder;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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
    @Column(name = "AUTH_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false , cascade = CascadeType.ALL )
    @JoinColumn(name = "USER_ID", nullable = false , referencedColumnName = "USER_ID")
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
    @CreationTimestamp
    private Instant tsCreate;

    @Column(name = "TS_DELETE")
    @JsonIgnore
    @UpdateTimestamp
    private Instant tsDelete;

    @Column(name = "TS_UPDATE", nullable = false)
    @JsonIgnore
    @UpdateTimestamp
    private Instant tsUpdate;

    @OneToMany(targetEntity = VerificationToken.class , fetch = FetchType.EAGER
            , mappedBy = "userAuthentication", cascade = CascadeType.ALL)
    private Set<VerificationToken> verificationTokenSet;

    public UserAuthentication(User user) {
        this.user = user;
        this.userName = user.getEmail();
        this.password = Encoder.passwordEncoder.encode(user.getFiscalCode());
        this.setActive(false);
        this.setRoles(Constant.USER);
        this.verificationTokenSet = new HashSet<>();
        VerificationToken verificationToken = new VerificationToken(this);
        this.verificationTokenSet.add(verificationToken);
    }
}