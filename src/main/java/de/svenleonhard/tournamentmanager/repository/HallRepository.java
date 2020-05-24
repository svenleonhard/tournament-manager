package de.svenleonhard.tournamentmanager.repository;

import de.svenleonhard.tournamentmanager.domain.Hall;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Hall entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {}
