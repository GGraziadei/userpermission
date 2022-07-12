package it.goodgamegroup.up.dto;

import it.goodgamegroup.up.entities.UserAuthentication;
import lombok.Data;

import java.util.Set;

@Data
public class UserAuthenticationGroupDTO {
    private Long id;
    private String code;
    private String name;
    private Set<UserAuthentication> userAuthentications;
}
