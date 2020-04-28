package com.eamonfoy.aodb.flight.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eamonfoy.aodb.flight.web.rest.TestUtil;

public class ArrivalFlightTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArrivalFlight.class);
        ArrivalFlight arrivalFlight1 = new ArrivalFlight();
        arrivalFlight1.setId(1L);
        ArrivalFlight arrivalFlight2 = new ArrivalFlight();
        arrivalFlight2.setId(arrivalFlight1.getId());
        assertThat(arrivalFlight1).isEqualTo(arrivalFlight2);
        arrivalFlight2.setId(2L);
        assertThat(arrivalFlight1).isNotEqualTo(arrivalFlight2);
        arrivalFlight1.setId(null);
        assertThat(arrivalFlight1).isNotEqualTo(arrivalFlight2);
    }
}
