package com.eamonfoy.aodb.flight.web.rest;

import com.eamonfoy.aodb.flight.domain.ArrivalFlight;
import com.eamonfoy.aodb.flight.service.ArrivalFlightService;
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
 * REST controller for managing {@link com.eamonfoy.aodb.flight.domain.ArrivalFlight}.
 */
@RestController
@RequestMapping("/api")
public class ArrivalFlightResource {

    private final Logger log = LoggerFactory.getLogger(ArrivalFlightResource.class);

    private static final String ENTITY_NAME = "flightArrivalFlight";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArrivalFlightService arrivalFlightService;

    public ArrivalFlightResource(ArrivalFlightService arrivalFlightService) {
        this.arrivalFlightService = arrivalFlightService;
    }

    /**
     * {@code POST  /arrival-flights} : Create a new arrivalFlight.
     *
     * @param arrivalFlight the arrivalFlight to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arrivalFlight, or with status {@code 400 (Bad Request)} if the arrivalFlight has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arrival-flights")
    public ResponseEntity<ArrivalFlight> createArrivalFlight(@Valid @RequestBody ArrivalFlight arrivalFlight) throws URISyntaxException {
        log.debug("REST request to save ArrivalFlight : {}", arrivalFlight);
        if (arrivalFlight.getId() != null) {
            throw new BadRequestAlertException("A new arrivalFlight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArrivalFlight result = arrivalFlightService.save(arrivalFlight);
        return ResponseEntity.created(new URI("/api/arrival-flights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arrival-flights} : Updates an existing arrivalFlight.
     *
     * @param arrivalFlight the arrivalFlight to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrivalFlight,
     * or with status {@code 400 (Bad Request)} if the arrivalFlight is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arrivalFlight couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arrival-flights")
    public ResponseEntity<ArrivalFlight> updateArrivalFlight(@Valid @RequestBody ArrivalFlight arrivalFlight) throws URISyntaxException {
        log.debug("REST request to update ArrivalFlight : {}", arrivalFlight);
        if (arrivalFlight.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArrivalFlight result = arrivalFlightService.save(arrivalFlight);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arrivalFlight.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arrival-flights} : get all the arrivalFlights.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arrivalFlights in body.
     */
    @GetMapping("/arrival-flights")
    public ResponseEntity<List<ArrivalFlight>> getAllArrivalFlights(Pageable pageable) {
        log.debug("REST request to get a page of ArrivalFlights");
        Page<ArrivalFlight> page = arrivalFlightService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arrival-flights/:id} : get the "id" arrivalFlight.
     *
     * @param id the id of the arrivalFlight to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arrivalFlight, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arrival-flights/{id}")
    public ResponseEntity<ArrivalFlight> getArrivalFlight(@PathVariable Long id) {
        log.debug("REST request to get ArrivalFlight : {}", id);
        Optional<ArrivalFlight> arrivalFlight = arrivalFlightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arrivalFlight);
    }

    /**
     * {@code DELETE  /arrival-flights/:id} : delete the "id" arrivalFlight.
     *
     * @param id the id of the arrivalFlight to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arrival-flights/{id}")
    public ResponseEntity<Void> deleteArrivalFlight(@PathVariable Long id) {
        log.debug("REST request to delete ArrivalFlight : {}", id);
        arrivalFlightService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
