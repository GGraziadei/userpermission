package it.goodgamegroup.up.repositories;

import it.goodgamegroup.up.entities.UserAuthenticationGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthenticationGroupRepository extends JpaRepository<UserAuthenticationGroup  , Long> {
    UserAuthenticationGroup findByName(String name);
    UserAuthenticationGroup findByCode(String code);
}
