package com.eamonfoy.aodb.flight.repository;

import com.eamonfoy.aodb.flight.domain.ArrivalFlight;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArrivalFlight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArrivalFlightRepository extends JpaRepository<ArrivalFlight, Long> {

}
