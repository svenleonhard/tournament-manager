package de.svenleonhard.tournamentmanager.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tournament.
 */
@Entity
@Table(name = "tournament")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tournament implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tournament_name", unique = true)
    private String tournamentName;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "location")
    private String location;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "tournament_community",
        joinColumns = @JoinColumn(name = "tournament_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id")
    )
    private Set<Community> communities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public Tournament tournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
        return this;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public LocalDate getDate() {
        return date;
    }

    public Tournament date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public Tournament location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public Tournament communities(Set<Community> communities) {
        this.communities = communities;
        return this;
    }

    public Tournament addCommunity(Community community) {
        this.communities.add(community);
        community.getTournaments().add(this);
        return this;
    }

    public Tournament removeCommunity(Community community) {
        this.communities.remove(community);
        community.getTournaments().remove(this);
        return this;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tournament)) {
            return false;
        }
        return id != null && id.equals(((Tournament) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + getId() +
            ", tournamentName='" + getTournamentName() + "'" +
            ", date='" + getDate() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
