package it.goodgamegroup.up.services.dao;

import it.goodgamegroup.up.dto.UserAuthenticationGroupDTO;
import it.goodgamegroup.up.entities.UserAuthenticationGroup;

public interface UserAuthenticationGroupDAO extends DaoPattern<UserAuthenticationGroup , Long> {
    UserAuthenticationGroup getByName(String name);
    UserAuthenticationGroup getByCode(String code);
    UserAuthenticationGroup put(UserAuthenticationGroupDTO dto);
}
