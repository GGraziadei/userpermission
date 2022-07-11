package it.goodgamegroup.up.repositories;

import it.goodgamegroup.up.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken , Long> {

}
