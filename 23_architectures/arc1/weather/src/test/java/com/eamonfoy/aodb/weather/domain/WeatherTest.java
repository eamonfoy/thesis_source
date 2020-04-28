package com.eamonfoy.aodb.weather.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eamonfoy.aodb.weather.web.rest.TestUtil;

public class WeatherTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Weather.class);
        Weather weather1 = new Weather();
        weather1.setId(1L);
        Weather weather2 = new Weather();
        weather2.setId(weather1.getId());
        assertThat(weather1).isEqualTo(weather2);
        weather2.setId(2L);
        assertThat(weather1).isNotEqualTo(weather2);
        weather1.setId(null);
        assertThat(weather1).isNotEqualTo(weather2);
    }
}
