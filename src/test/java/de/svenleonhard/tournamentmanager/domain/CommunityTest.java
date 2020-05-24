package de.svenleonhard.tournamentmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svenleonhard.tournamentmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CommunityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Community.class);
        Community community1 = new Community();
        community1.setId(1L);
        Community community2 = new Community();
        community2.setId(community1.getId());
        assertThat(community1).isEqualTo(community2);
        community2.setId(2L);
        assertThat(community1).isNotEqualTo(community2);
        community1.setId(null);
        assertThat(community1).isNotEqualTo(community2);
    }
}
