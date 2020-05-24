package de.svenleonhard.tournamentmanager.repository;

import de.svenleonhard.tournamentmanager.domain.GamePlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GamePlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamePlanRepository extends JpaRepository<GamePlan, Long> {}
