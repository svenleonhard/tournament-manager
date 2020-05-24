package de.svenleonhard.tournamentmanager.repository;

import de.svenleonhard.tournamentmanager.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
