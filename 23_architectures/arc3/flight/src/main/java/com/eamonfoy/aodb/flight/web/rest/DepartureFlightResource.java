package com.eamonfoy.aodb.flight.web.rest;

import com.eamonfoy.aodb.flight.domain.DepartureFlight;
import com.eamonfoy.aodb.flight.service.DepartureFlightService;
import com.eamonfoy.aodb.flight.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.eamonfoy.aodb.flight.domain.DepartureFlight}.
 */
@RestController
@RequestMapping("/api")
public class DepartureFlightResource {

    private final Logger log = LoggerFactory.getLogger(DepartureFlightResource.class);

    private static final String ENTITY_NAME = "flightDepartureFlight";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartureFlightService departureFlightService;

    public DepartureFlightResource(DepartureFlightService departureFlightService) {
        this.departureFlightService = departureFlightService;
    }

    /**
     * {@code POST  /departure-flights} : Create a new departureFlight.
     *
     * @param departureFlight the departureFlight to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departureFlight, or with status {@code 400 (Bad Request)} if the departureFlight has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/departure-flights")
    public ResponseEntity<DepartureFlight> createDepartureFlight(@Valid @RequestBody DepartureFlight departureFlight) throws URISyntaxException {
        log.debug("REST request to save DepartureFlight : {}", departureFlight);
        if (departureFlight.getId() != null) {
            throw new BadRequestAlertException("A new departureFlight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartureFlight result = departureFlightService.save(departureFlight);
        return ResponseEntity.created(new URI("/api/departure-flights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /departure-flights} : Updates an existing departureFlight.
     *
     * @param departureFlight the departureFlight to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departureFlight,
     * or with status {@code 400 (Bad Request)} if the departureFlight is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departureFlight couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/departure-flights")
    public ResponseEntity<DepartureFlight> updateDepartureFlight(@Valid @RequestBody DepartureFlight departureFlight) throws URISyntaxException {
        log.debug("REST request to update DepartureFlight : {}", departureFlight);
        if (departureFlight.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartureFlight result = departureFlightService.save(departureFlight);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departureFlight.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /departure-flights} : get all the departureFlights.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departureFlights in body.
     */
    @GetMapping("/departure-flights")
    public ResponseEntity<List<DepartureFlight>> getAllDepartureFlights(Pageable pageable) {
        log.debug("REST request to get a page of DepartureFlights");
        Page<DepartureFlight> page = departureFlightService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departure-flights/:id} : get the "id" departureFlight.
     *
     * @param id the id of the departureFlight to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departureFlight, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/departure-flights/{id}")
    public ResponseEntity<DepartureFlight> getDepartureFlight(@PathVariable Long id) {
        log.debug("REST request to get DepartureFlight : {}", id);
        Optional<DepartureFlight> departureFlight = departureFlightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departureFlight);
    }

    /**
     * {@code DELETE  /departure-flights/:id} : delete the "id" departureFlight.
     *
     * @param id the id of the departureFlight to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/departure-flights/{id}")
    public ResponseEntity<Void> deleteDepartureFlight(@PathVariable Long id) {
        log.debug("REST request to delete DepartureFlight : {}", id);
        departureFlightService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
