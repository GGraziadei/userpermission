package it.goodgamegroup.up.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "up_user_authentication_group")
public class UserAuthenticationGroup{
    @Id
    @Column(name = "GROUP_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false , name = "CODE")
    private String code;
    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "userAuthenticationGroups")
    private Set<UserAuthentication> userAuthentications;
}
