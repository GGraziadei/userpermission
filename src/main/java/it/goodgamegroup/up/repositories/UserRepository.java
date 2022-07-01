package it.goodgamegroup.up.repositories;

import it.goodgamegroup.up.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
