package de.svenleonhard.tournamentmanager.repository;

import de.svenleonhard.tournamentmanager.domain.Tournament;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tournament entity.
 */
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    @Query(
        value = "select distinct tournament from Tournament tournament left join fetch tournament.communities",
        countQuery = "select count(distinct tournament) from Tournament tournament"
    )
    Page<Tournament> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct tournament from Tournament tournament left join fetch tournament.communities")
    List<Tournament> findAllWithEagerRelationships();

    @Query("select tournament from Tournament tournament left join fetch tournament.communities where tournament.id =:id")
    Optional<Tournament> findOneWithEagerRelationships(@Param("id") Long id);
}
