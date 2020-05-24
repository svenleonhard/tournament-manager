package de.svenleonhard.tournamentmanager.service.impl;

import de.svenleonhard.tournamentmanager.domain.Hall;
import de.svenleonhard.tournamentmanager.repository.HallRepository;
import de.svenleonhard.tournamentmanager.service.HallService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Hall}.
 */
@Service
@Transactional
public class HallServiceImpl implements HallService {
    private final Logger log = LoggerFactory.getLogger(HallServiceImpl.class);

    private final HallRepository hallRepository;

    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    /**
     * Save a hall.
     *
     * @param hall the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Hall save(Hall hall) {
        log.debug("Request to save Hall : {}", hall);
        return hallRepository.save(hall);
    }

    /**
     * Get all the halls.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Hall> findAll() {
        log.debug("Request to get all Halls");
        return hallRepository.findAll();
    }

    /**
     * Get one hall by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Hall> findOne(Long id) {
        log.debug("Request to get Hall : {}", id);
        return hallRepository.findById(id);
    }

    /**
     * Delete the hall by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hall : {}", id);

        hallRepository.deleteById(id);
    }
}
