package com.eamonfoy.aodb.flight.service;

import com.eamonfoy.aodb.flight.domain.ArrivalFlight;
import com.eamonfoy.aodb.flight.repository.ArrivalFlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArrivalFlight}.
 */
@Service
@Transactional
public class ArrivalFlightService {

    private final Logger log = LoggerFactory.getLogger(ArrivalFlightService.class);

    private final ArrivalFlightRepository arrivalFlightRepository;

    public ArrivalFlightService(ArrivalFlightRepository arrivalFlightRepository) {
        this.arrivalFlightRepository = arrivalFlightRepository;
    }

    /**
     * Save a arrivalFlight.
     *
     * @param arrivalFlight the entity to save.
     * @return the persisted entity.
     */
    public ArrivalFlight save(ArrivalFlight arrivalFlight) {
        log.debug("Request to save ArrivalFlight : {}", arrivalFlight);
        return arrivalFlightRepository.save(arrivalFlight);
    }

    /**
     * Get all the arrivalFlights.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArrivalFlight> findAll(Pageable pageable) {
        log.debug("Request to get all ArrivalFlights");
        return arrivalFlightRepository.findAll(pageable);
    }

    /**
     * Get one arrivalFlight by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArrivalFlight> findOne(Long id) {
        log.debug("Request to get ArrivalFlight : {}", id);
        return arrivalFlightRepository.findById(id);
    }

    /**
     * Delete the arrivalFlight by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArrivalFlight : {}", id);
        arrivalFlightRepository.deleteById(id);
    }
}
