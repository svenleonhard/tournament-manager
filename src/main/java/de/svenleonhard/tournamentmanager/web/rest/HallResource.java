package de.svenleonhard.tournamentmanager.web.rest;

import de.svenleonhard.tournamentmanager.domain.Hall;
import de.svenleonhard.tournamentmanager.service.HallService;
import de.svenleonhard.tournamentmanager.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link de.svenleonhard.tournamentmanager.domain.Hall}.
 */
@RestController
@RequestMapping("/api")
public class HallResource {
    private final Logger log = LoggerFactory.getLogger(HallResource.class);

    private static final String ENTITY_NAME = "hall";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HallService hallService;

    public HallResource(HallService hallService) {
        this.hallService = hallService;
    }

    /**
     * {@code POST  /halls} : Create a new hall.
     *
     * @param hall the hall to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hall, or with status {@code 400 (Bad Request)} if the hall has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/halls")
    public ResponseEntity<Hall> createHall(@Valid @RequestBody Hall hall) throws URISyntaxException {
        log.debug("REST request to save Hall : {}", hall);
        if (hall.getId() != null) {
            throw new BadRequestAlertException("A new hall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hall result = hallService.save(hall);
        return ResponseEntity
            .created(new URI("/api/halls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /halls} : Updates an existing hall.
     *
     * @param hall the hall to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hall,
     * or with status {@code 400 (Bad Request)} if the hall is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hall couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/halls")
    public ResponseEntity<Hall> updateHall(@Valid @RequestBody Hall hall) throws URISyntaxException {
        log.debug("REST request to update Hall : {}", hall);
        if (hall.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hall result = hallService.save(hall);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hall.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /halls} : get all the halls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of halls in body.
     */
    @GetMapping("/halls")
    public List<Hall> getAllHalls() {
        log.debug("REST request to get all Halls");
        return hallService.findAll();
    }

    /**
     * {@code GET  /halls/:id} : get the "id" hall.
     *
     * @param id the id of the hall to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hall, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/halls/{id}")
    public ResponseEntity<Hall> getHall(@PathVariable Long id) {
        log.debug("REST request to get Hall : {}", id);
        Optional<Hall> hall = hallService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hall);
    }

    /**
     * {@code DELETE  /halls/:id} : delete the "id" hall.
     *
     * @param id the id of the hall to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/halls/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id) {
        log.debug("REST request to delete Hall : {}", id);

        hallService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
