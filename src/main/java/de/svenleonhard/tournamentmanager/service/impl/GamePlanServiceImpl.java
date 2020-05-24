package de.svenleonhard.tournamentmanager.service.impl;

import de.svenleonhard.tournamentmanager.domain.GamePlan;
import de.svenleonhard.tournamentmanager.repository.GamePlanRepository;
import de.svenleonhard.tournamentmanager.service.GamePlanService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GamePlan}.
 */
@Service
@Transactional
public class GamePlanServiceImpl implements GamePlanService {
    private final Logger log = LoggerFactory.getLogger(GamePlanServiceImpl.class);

    private final GamePlanRepository gamePlanRepository;

    public GamePlanServiceImpl(GamePlanRepository gamePlanRepository) {
        this.gamePlanRepository = gamePlanRepository;
    }

    /**
     * Save a gamePlan.
     *
     * @param gamePlan the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GamePlan save(GamePlan gamePlan) {
        log.debug("Request to save GamePlan : {}", gamePlan);
        return gamePlanRepository.save(gamePlan);
    }

    /**
     * Get all the gamePlans.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GamePlan> findAll() {
        log.debug("Request to get all GamePlans");
        return gamePlanRepository.findAll();
    }

    /**
     * Get one gamePlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GamePlan> findOne(Long id) {
        log.debug("Request to get GamePlan : {}", id);
        return gamePlanRepository.findById(id);
    }

    /**
     * Delete the gamePlan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GamePlan : {}", id);

        gamePlanRepository.deleteById(id);
    }
}
