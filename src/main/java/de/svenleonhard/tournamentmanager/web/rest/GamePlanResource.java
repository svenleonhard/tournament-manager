package de.svenleonhard.tournamentmanager.web.rest;

import de.svenleonhard.tournamentmanager.domain.GamePlan;
import de.svenleonhard.tournamentmanager.service.GamePlanService;
import de.svenleonhard.tournamentmanager.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link de.svenleonhard.tournamentmanager.domain.GamePlan}.
 */
@RestController
@RequestMapping("/api")
public class GamePlanResource {
    private final Logger log = LoggerFactory.getLogger(GamePlanResource.class);

    private static final String ENTITY_NAME = "gamePlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GamePlanService gamePlanService;

    public GamePlanResource(GamePlanService gamePlanService) {
        this.gamePlanService = gamePlanService;
    }

    /**
     * {@code POST  /game-plans} : Create a new gamePlan.
     *
     * @param gamePlan the gamePlan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gamePlan, or with status {@code 400 (Bad Request)} if the gamePlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-plans")
    public ResponseEntity<GamePlan> createGamePlan(@RequestBody GamePlan gamePlan) throws URISyntaxException {
        log.debug("REST request to save GamePlan : {}", gamePlan);
        if (gamePlan.getId() != null) {
            throw new BadRequestAlertException("A new gamePlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GamePlan result = gamePlanService.save(gamePlan);
        return ResponseEntity
            .created(new URI("/api/game-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-plans} : Updates an existing gamePlan.
     *
     * @param gamePlan the gamePlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gamePlan,
     * or with status {@code 400 (Bad Request)} if the gamePlan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gamePlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-plans")
    public ResponseEntity<GamePlan> updateGamePlan(@RequestBody GamePlan gamePlan) throws URISyntaxException {
        log.debug("REST request to update GamePlan : {}", gamePlan);
        if (gamePlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GamePlan result = gamePlanService.save(gamePlan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gamePlan.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-plans} : get all the gamePlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gamePlans in body.
     */
    @GetMapping("/game-plans")
    public List<GamePlan> getAllGamePlans() {
        log.debug("REST request to get all GamePlans");
        return gamePlanService.findAll();
    }

    /**
     * {@code GET  /game-plans/:id} : get the "id" gamePlan.
     *
     * @param id the id of the gamePlan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gamePlan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-plans/{id}")
    public ResponseEntity<GamePlan> getGamePlan(@PathVariable Long id) {
        log.debug("REST request to get GamePlan : {}", id);
        Optional<GamePlan> gamePlan = gamePlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gamePlan);
    }

    /**
     * {@code DELETE  /game-plans/:id} : delete the "id" gamePlan.
     *
     * @param id the id of the gamePlan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-plans/{id}")
    public ResponseEntity<Void> deleteGamePlan(@PathVariable Long id) {
        log.debug("REST request to delete GamePlan : {}", id);

        gamePlanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
