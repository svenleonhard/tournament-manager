package de.svenleonhard.tournamentmanager.service;

import de.svenleonhard.tournamentmanager.domain.GamePlan;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GamePlan}.
 */
public interface GamePlanService {
    /**
     * Save a gamePlan.
     *
     * @param gamePlan the entity to save.
     * @return the persisted entity.
     */
    GamePlan save(GamePlan gamePlan);

    /**
     * Get all the gamePlans.
     *
     * @return the list of entities.
     */
    List<GamePlan> findAll();

    /**
     * Get the "id" gamePlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GamePlan> findOne(Long id);

    /**
     * Delete the "id" gamePlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
