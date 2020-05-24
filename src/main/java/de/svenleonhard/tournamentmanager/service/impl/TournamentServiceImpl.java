package de.svenleonhard.tournamentmanager.service.impl;

import de.svenleonhard.tournamentmanager.domain.Tournament;
import de.svenleonhard.tournamentmanager.repository.TournamentRepository;
import de.svenleonhard.tournamentmanager.service.TournamentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tournament}.
 */
@Service
@Transactional
public class TournamentServiceImpl implements TournamentService {
    private final Logger log = LoggerFactory.getLogger(TournamentServiceImpl.class);

    private final TournamentRepository tournamentRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    /**
     * Save a tournament.
     *
     * @param tournament the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Tournament save(Tournament tournament) {
        log.debug("Request to save Tournament : {}", tournament);
        return tournamentRepository.save(tournament);
    }

    /**
     * Get all the tournaments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tournament> findAll() {
        log.debug("Request to get all Tournaments");
        return tournamentRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the tournaments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Tournament> findAllWithEagerRelationships(Pageable pageable) {
        return tournamentRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one tournament by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Tournament> findOne(Long id) {
        log.debug("Request to get Tournament : {}", id);
        return tournamentRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the tournament by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tournament : {}", id);

        tournamentRepository.deleteById(id);
    }
}
