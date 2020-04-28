package com.eamonfoy.aodb.flight.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eamonfoy.aodb.flight.web.rest.TestUtil;

public class DepartureFlightTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartureFlight.class);
        DepartureFlight departureFlight1 = new DepartureFlight();
        departureFlight1.setId(1L);
        DepartureFlight departureFlight2 = new DepartureFlight();
        departureFlight2.setId(departureFlight1.getId());
        assertThat(departureFlight1).isEqualTo(departureFlight2);
        departureFlight2.setId(2L);
        assertThat(departureFlight1).isNotEqualTo(departureFlight2);
        departureFlight1.setId(null);
        assertThat(departureFlight1).isNotEqualTo(departureFlight2);
    }
}
