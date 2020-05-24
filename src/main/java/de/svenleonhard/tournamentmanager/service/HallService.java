package de.svenleonhard.tournamentmanager.service;

import de.svenleonhard.tournamentmanager.domain.Hall;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Hall}.
 */
public interface HallService {
    /**
     * Save a hall.
     *
     * @param hall the entity to save.
     * @return the persisted entity.
     */
    Hall save(Hall hall);

    /**
     * Get all the halls.
     *
     * @return the list of entities.
     */
    List<Hall> findAll();

    /**
     * Get the "id" hall.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Hall> findOne(Long id);

    /**
     * Delete the "id" hall.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
