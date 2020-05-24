package de.svenleonhard.tournamentmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svenleonhard.tournamentmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class HallTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hall.class);
        Hall hall1 = new Hall();
        hall1.setId(1L);
        Hall hall2 = new Hall();
        hall2.setId(hall1.getId());
        assertThat(hall1).isEqualTo(hall2);
        hall2.setId(2L);
        assertThat(hall1).isNotEqualTo(hall2);
        hall1.setId(null);
        assertThat(hall1).isNotEqualTo(hall2);
    }
}
