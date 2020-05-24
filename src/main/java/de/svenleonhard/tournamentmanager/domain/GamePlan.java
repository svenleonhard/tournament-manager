package de.svenleonhard.tournamentmanager.domain;

import de.svenleonhard.tournamentmanager.domain.enumeration.Mode;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GamePlan.
 */
@Entity
@Table(name = "game_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GamePlan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode")
    private Mode gameMode;

    @OneToMany(mappedBy = "gamePlan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Game> games = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mode getGameMode() {
        return gameMode;
    }

    public GamePlan gameMode(Mode gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public void setGameMode(Mode gameMode) {
        this.gameMode = gameMode;
    }

    public Set<Game> getGames() {
        return games;
    }

    public GamePlan games(Set<Game> games) {
        this.games = games;
        return this;
    }

    public GamePlan addGame(Game game) {
        this.games.add(game);
        game.setGamePlan(this);
        return this;
    }

    public GamePlan removeGame(Game game) {
        this.games.remove(game);
        game.setGamePlan(null);
        return this;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GamePlan)) {
            return false;
        }
        return id != null && id.equals(((GamePlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GamePlan{" +
            "id=" + getId() +
            ", gameMode='" + getGameMode() + "'" +
            "}";
    }
}
