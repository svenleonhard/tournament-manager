package de.svenleonhard.tournamentmanager.service;

import de.svenleonhard.tournamentmanager.domain.Score;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Score}.
 */
public interface ScoreService {
    /**
     * Save a score.
     *
     * @param score the entity to save.
     * @return the persisted entity.
     */
    Score save(Score score);

    /**
     * Get all the scores.
     *
     * @return the list of entities.
     */
    List<Score> findAll();

    /**
     * Get the "id" score.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Score> findOne(Long id);

    /**
     * Delete the "id" score.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
