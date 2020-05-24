package de.svenleonhard.tournamentmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svenleonhard.tournamentmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TournamentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tournament.class);
        Tournament tournament1 = new Tournament();
        tournament1.setId(1L);
        Tournament tournament2 = new Tournament();
        tournament2.setId(tournament1.getId());
        assertThat(tournament1).isEqualTo(tournament2);
        tournament2.setId(2L);
        assertThat(tournament1).isNotEqualTo(tournament2);
        tournament1.setId(null);
        assertThat(tournament1).isNotEqualTo(tournament2);
    }
}
