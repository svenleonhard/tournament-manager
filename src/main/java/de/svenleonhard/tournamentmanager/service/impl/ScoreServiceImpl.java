package de.svenleonhard.tournamentmanager.service.impl;

import de.svenleonhard.tournamentmanager.domain.Score;
import de.svenleonhard.tournamentmanager.repository.ScoreRepository;
import de.svenleonhard.tournamentmanager.service.ScoreService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Score}.
 */
@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {
    private final Logger log = LoggerFactory.getLogger(ScoreServiceImpl.class);

    private final ScoreRepository scoreRepository;

    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    /**
     * Save a score.
     *
     * @param score the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Score save(Score score) {
        log.debug("Request to save Score : {}", score);
        return scoreRepository.save(score);
    }

    /**
     * Get all the scores.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Score> findAll() {
        log.debug("Request to get all Scores");
        return scoreRepository.findAll();
    }

    /**
     * Get one score by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Score> findOne(Long id) {
        log.debug("Request to get Score : {}", id);
        return scoreRepository.findById(id);
    }

    /**
     * Delete the score by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Score : {}", id);

        scoreRepository.deleteById(id);
    }
}
