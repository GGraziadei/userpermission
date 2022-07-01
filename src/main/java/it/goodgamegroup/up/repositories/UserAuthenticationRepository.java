package it.goodgamegroup.up.repositories;

import it.goodgamegroup.up.entities.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, UUID> {
    Optional<UserAuthentication> findByUserName(String userName);
}
