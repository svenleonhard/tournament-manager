package de.svenleonhard.tournamentmanager.web.rest;

import static de.svenleonhard.tournamentmanager.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.svenleonhard.tournamentmanager.TournamentManagerApp;
import de.svenleonhard.tournamentmanager.domain.Game;
import de.svenleonhard.tournamentmanager.domain.enumeration.GameState;
import de.svenleonhard.tournamentmanager.domain.enumeration.GameType;
import de.svenleonhard.tournamentmanager.repository.GameRepository;
import de.svenleonhard.tournamentmanager.service.GameService;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link GameResource} REST controller.
 */
@SpringBootTest(classes = TournamentManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GameResourceIT {
    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final GameState DEFAULT_STATE = GameState.PLANNED;
    private static final GameState UPDATED_STATE = GameState.RUNNING;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TEAM_1 = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_2 = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_2 = "BBBBBBBBBB";

    private static final GameType DEFAULT_GAME_TYPE = GameType.GROUP;
    private static final GameType UPDATED_GAME_TYPE = GameType.SIXTEENTH;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameMockMvc;

    private Game game;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createEntity(EntityManager em) {
        Game game = new Game()
            .duration(DEFAULT_DURATION)
            .state(DEFAULT_STATE)
            .startTime(DEFAULT_START_TIME)
            .team1(DEFAULT_TEAM_1)
            .team2(DEFAULT_TEAM_2)
            .gameType(DEFAULT_GAME_TYPE);
        return game;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createUpdatedEntity(EntityManager em) {
        Game game = new Game()
            .duration(UPDATED_DURATION)
            .state(UPDATED_STATE)
            .startTime(UPDATED_START_TIME)
            .team1(UPDATED_TEAM_1)
            .team2(UPDATED_TEAM_2)
            .gameType(UPDATED_GAME_TYPE);
        return game;
    }

    @BeforeEach
    public void initTest() {
        game = createEntity(em);
    }

    @Test
    @Transactional
    public void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();
        // Create the Game
        restGameMockMvc
            .perform(post("/api/games").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testGame.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testGame.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testGame.getTeam1()).isEqualTo(DEFAULT_TEAM_1);
        assertThat(testGame.getTeam2()).isEqualTo(DEFAULT_TEAM_2);
        assertThat(testGame.getGameType()).isEqualTo(DEFAULT_GAME_TYPE);
    }

    @Test
    @Transactional
    public void createGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game with an existing ID
        game.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameMockMvc
            .perform(post("/api/games").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get all the gameList
        restGameMockMvc
            .perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId().intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].team1").value(hasItem(DEFAULT_TEAM_1)))
            .andExpect(jsonPath("$.[*].team2").value(hasItem(DEFAULT_TEAM_2)))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get the game
        restGameMockMvc
            .perform(get("/api/games/{id}", game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(game.getId().intValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.team1").value(DEFAULT_TEAM_1))
            .andExpect(jsonPath("$.team2").value(DEFAULT_TEAM_2))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGame() throws Exception {
        // Initialize the database
        gameService.save(game);

        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game
        Game updatedGame = gameRepository.findById(game.getId()).get();
        // Disconnect from session so that the updates on updatedGame are not directly saved in db
        em.detach(updatedGame);
        updatedGame
            .duration(UPDATED_DURATION)
            .state(UPDATED_STATE)
            .startTime(UPDATED_START_TIME)
            .team1(UPDATED_TEAM_1)
            .team2(UPDATED_TEAM_2)
            .gameType(UPDATED_GAME_TYPE);

        restGameMockMvc
            .perform(put("/api/games").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedGame)))
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testGame.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testGame.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testGame.getTeam1()).isEqualTo(UPDATED_TEAM_1);
        assertThat(testGame.getTeam2()).isEqualTo(UPDATED_TEAM_2);
        assertThat(testGame.getGameType()).isEqualTo(UPDATED_GAME_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(put("/api/games").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGame() throws Exception {
        // Initialize the database
        gameService.save(game);

        int databaseSizeBeforeDelete = gameRepository.findAll().size();

        // Delete the game
        restGameMockMvc
            .perform(delete("/api/games/{id}", game.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
