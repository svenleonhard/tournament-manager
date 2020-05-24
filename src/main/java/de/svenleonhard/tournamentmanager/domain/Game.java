package de.svenleonhard.tournamentmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.svenleonhard.tournamentmanager.domain.enumeration.GameState;
import de.svenleonhard.tournamentmanager.domain.enumeration.GameType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duration")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private GameState state;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "team_1")
    private String team1;

    @Column(name = "team_2")
    private String team2;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type")
    private GameType gameType;

    @OneToOne
    @JoinColumn(unique = true)
    private Hall hall;

    @OneToOne
    @JoinColumn(unique = true)
    private Score score;

    @ManyToOne
    @JsonIgnoreProperties(value = "games", allowSetters = true)
    private GamePlan gamePlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public Game duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public GameState getState() {
        return state;
    }

    public Game state(GameState state) {
        this.state = state;
        return this;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Game startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public String getTeam1() {
        return team1;
    }

    public Game team1(String team1) {
        this.team1 = team1;
        return this;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public Game team2(String team2) {
        this.team2 = team2;
        return this;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public GameType getGameType() {
        return gameType;
    }

    public Game gameType(GameType gameType) {
        this.gameType = gameType;
        return this;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Hall getHall() {
        return hall;
    }

    public Game hall(Hall hall) {
        this.hall = hall;
        return this;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Score getScore() {
        return score;
    }

    public Game score(Score score) {
        this.score = score;
        return this;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public GamePlan getGamePlan() {
        return gamePlan;
    }

    public Game gamePlan(GamePlan gamePlan) {
        this.gamePlan = gamePlan;
        return this;
    }

    public void setGamePlan(GamePlan gamePlan) {
        this.gamePlan = gamePlan;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        return id != null && id.equals(((Game) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", duration=" + getDuration() +
            ", state='" + getState() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", team1='" + getTeam1() + "'" +
            ", team2='" + getTeam2() + "'" +
            ", gameType='" + getGameType() + "'" +
            "}";
    }
}
