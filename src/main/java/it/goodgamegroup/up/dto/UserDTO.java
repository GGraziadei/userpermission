package it.goodgamegroup.up.dto;

import it.goodgamegroup.up.entities.Permission;
import it.goodgamegroup.up.entities.UserAuthentication;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String fiscalCode;
    private String email;
    private String phoneNumber;
    private List<Permission> permissions;
    private UserAuthentication userAuthentication;
}
