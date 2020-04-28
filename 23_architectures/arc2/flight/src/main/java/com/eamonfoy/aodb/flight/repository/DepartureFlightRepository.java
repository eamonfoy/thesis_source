package com.eamonfoy.aodb.flight.repository;

import com.eamonfoy.aodb.flight.domain.DepartureFlight;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DepartureFlight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartureFlightRepository extends JpaRepository<DepartureFlight, Long> {

}
