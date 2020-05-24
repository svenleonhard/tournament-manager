package de.svenleonhard.tournamentmanager.service;

import de.svenleonhard.tournamentmanager.domain.Community;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Community}.
 */
public interface CommunityService {
    /**
     * Save a community.
     *
     * @param community the entity to save.
     * @return the persisted entity.
     */
    Community save(Community community);

    /**
     * Get all the communities.
     *
     * @return the list of entities.
     */
    List<Community> findAll();

    /**
     * Get the "id" community.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Community> findOne(Long id);

    /**
     * Delete the "id" community.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
