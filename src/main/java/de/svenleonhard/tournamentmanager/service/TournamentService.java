package de.svenleonhard.tournamentmanager.service;

import de.svenleonhard.tournamentmanager.domain.Tournament;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Tournament}.
 */
public interface TournamentService {
    /**
     * Save a tournament.
     *
     * @param tournament the entity to save.
     * @return the persisted entity.
     */
    Tournament save(Tournament tournament);

    /**
     * Get all the tournaments.
     *
     * @return the list of entities.
     */
    List<Tournament> findAll();

    /**
     * Get all the tournaments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Tournament> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tournament.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tournament> findOne(Long id);

    /**
     * Delete the "id" tournament.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
