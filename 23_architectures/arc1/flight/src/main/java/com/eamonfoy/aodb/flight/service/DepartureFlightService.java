package com.eamonfoy.aodb.flight.service;

import com.eamonfoy.aodb.flight.domain.DepartureFlight;
import com.eamonfoy.aodb.flight.repository.DepartureFlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DepartureFlight}.
 */
@Service
@Transactional
public class DepartureFlightService {

    private final Logger log = LoggerFactory.getLogger(DepartureFlightService.class);

    private final DepartureFlightRepository departureFlightRepository;

    public DepartureFlightService(DepartureFlightRepository departureFlightRepository) {
        this.departureFlightRepository = departureFlightRepository;
    }

    /**
     * Save a departureFlight.
     *
     * @param departureFlight the entity to save.
     * @return the persisted entity.
     */
    public DepartureFlight save(DepartureFlight departureFlight) {
        log.debug("Request to save DepartureFlight : {}", departureFlight);
        return departureFlightRepository.save(departureFlight);
    }

    /**
     * Get all the departureFlights.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartureFlight> findAll(Pageable pageable) {
        log.debug("Request to get all DepartureFlights");
        return departureFlightRepository.findAll(pageable);
    }

    /**
     * Get one departureFlight by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartureFlight> findOne(Long id) {
        log.debug("Request to get DepartureFlight : {}", id);
        return departureFlightRepository.findById(id);
    }

    /**
     * Delete the departureFlight by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepartureFlight : {}", id);
        departureFlightRepository.deleteById(id);
    }
}
