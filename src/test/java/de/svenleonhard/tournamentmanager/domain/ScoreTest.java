package de.svenleonhard.tournamentmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svenleonhard.tournamentmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Score.class);
        Score score1 = new Score();
        score1.setId(1L);
        Score score2 = new Score();
        score2.setId(score1.getId());
        assertThat(score1).isEqualTo(score2);
        score2.setId(2L);
        assertThat(score1).isNotEqualTo(score2);
        score1.setId(null);
        assertThat(score1).isNotEqualTo(score2);
    }
}
