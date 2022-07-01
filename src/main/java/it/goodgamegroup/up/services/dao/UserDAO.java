package it.goodgamegroup.up.services.dao;

import it.goodgamegroup.up.dto.UserDTO;
import it.goodgamegroup.up.entities.User;
import org.springframework.context.annotation.Primary;
import java.util.UUID;

@Primary
public interface UserDAO extends DaoPattern<User, UUID> {
    User update(UserDTO userDTO);
}
