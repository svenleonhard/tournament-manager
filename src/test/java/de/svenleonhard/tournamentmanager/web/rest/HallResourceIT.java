package de.svenleonhard.tournamentmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.svenleonhard.tournamentmanager.TournamentManagerApp;
import de.svenleonhard.tournamentmanager.domain.Hall;
import de.svenleonhard.tournamentmanager.repository.HallRepository;
import de.svenleonhard.tournamentmanager.service.HallService;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HallResource} REST controller.
 */
@SpringBootTest(classes = TournamentManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HallResourceIT {
    private static final String DEFAULT_HALL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HALL_NAME = "BBBBBBBBBB";

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private HallService hallService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHallMockMvc;

    private Hall hall;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hall createEntity(EntityManager em) {
        Hall hall = new Hall().hallName(DEFAULT_HALL_NAME);
        return hall;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hall createUpdatedEntity(EntityManager em) {
        Hall hall = new Hall().hallName(UPDATED_HALL_NAME);
        return hall;
    }

    @BeforeEach
    public void initTest() {
        hall = createEntity(em);
    }

    @Test
    @Transactional
    public void createHall() throws Exception {
        int databaseSizeBeforeCreate = hallRepository.findAll().size();
        // Create the Hall
        restHallMockMvc
            .perform(post("/api/halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hall)))
            .andExpect(status().isCreated());

        // Validate the Hall in the database
        List<Hall> hallList = hallRepository.findAll();
        assertThat(hallList).hasSize(databaseSizeBeforeCreate + 1);
        Hall testHall = hallList.get(hallList.size() - 1);
        assertThat(testHall.getHallName()).isEqualTo(DEFAULT_HALL_NAME);
    }

    @Test
    @Transactional
    public void createHallWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hallRepository.findAll().size();

        // Create the Hall with an existing ID
        hall.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHallMockMvc
            .perform(post("/api/halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hall)))
            .andExpect(status().isBadRequest());

        // Validate the Hall in the database
        List<Hall> hallList = hallRepository.findAll();
        assertThat(hallList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHalls() throws Exception {
        // Initialize the database
        hallRepository.saveAndFlush(hall);

        // Get all the hallList
        restHallMockMvc
            .perform(get("/api/halls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hall.getId().intValue())))
            .andExpect(jsonPath("$.[*].hallName").value(hasItem(DEFAULT_HALL_NAME)));
    }

    @Test
    @Transactional
    public void getHall() throws Exception {
        // Initialize the database
        hallRepository.saveAndFlush(hall);

        // Get the hall
        restHallMockMvc
            .perform(get("/api/halls/{id}", hall.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hall.getId().intValue()))
            .andExpect(jsonPath("$.hallName").value(DEFAULT_HALL_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingHall() throws Exception {
        // Get the hall
        restHallMockMvc.perform(get("/api/halls/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHall() throws Exception {
        // Initialize the database
        hallService.save(hall);

        int databaseSizeBeforeUpdate = hallRepository.findAll().size();

        // Update the hall
        Hall updatedHall = hallRepository.findById(hall.getId()).get();
        // Disconnect from session so that the updates on updatedHall are not directly saved in db
        em.detach(updatedHall);
        updatedHall.hallName(UPDATED_HALL_NAME);

        restHallMockMvc
            .perform(put("/api/halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedHall)))
            .andExpect(status().isOk());

        // Validate the Hall in the database
        List<Hall> hallList = hallRepository.findAll();
        assertThat(hallList).hasSize(databaseSizeBeforeUpdate);
        Hall testHall = hallList.get(hallList.size() - 1);
        assertThat(testHall.getHallName()).isEqualTo(UPDATED_HALL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingHall() throws Exception {
        int databaseSizeBeforeUpdate = hallRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHallMockMvc
            .perform(put("/api/halls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hall)))
            .andExpect(status().isBadRequest());

        // Validate the Hall in the database
        List<Hall> hallList = hallRepository.findAll();
        assertThat(hallList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHall() throws Exception {
        // Initialize the database
        hallService.save(hall);

        int databaseSizeBeforeDelete = hallRepository.findAll().size();

        // Delete the hall
        restHallMockMvc
            .perform(delete("/api/halls/{id}", hall.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hall> hallList = hallRepository.findAll();
        assertThat(hallList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
