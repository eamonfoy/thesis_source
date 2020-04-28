package com.eamonfoy.aodb.weather.repository;

import com.eamonfoy.aodb.weather.domain.Weather;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Weather entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
