package com.eamonfoy.aodb.weather.web.rest;

import com.eamonfoy.aodb.weather.domain.Weather;
import com.eamonfoy.aodb.weather.service.WeatherService;
import com.eamonfoy.aodb.weather.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.eamonfoy.aodb.weather.domain.Weather}.
 */
@RestController
@RequestMapping("/api")
public class WeatherResource {

    private final Logger log = LoggerFactory.getLogger(WeatherResource.class);

    private static final String ENTITY_NAME = "weatherWeather";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeatherService weatherService;

    public WeatherResource(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * {@code POST  /weathers} : Create a new weather.
     *
     * @param weather the weather to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weather, or with status {@code 400 (Bad Request)} if the weather has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/weathers")
    public ResponseEntity<Weather> createWeather(@Valid @RequestBody Weather weather) throws URISyntaxException {
        log.debug("REST request to save Weather : {}", weather);
        if (weather.getId() != null) {
            throw new BadRequestAlertException("A new weather cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Weather result = weatherService.save(weather);
        return ResponseEntity.created(new URI("/api/weathers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weathers} : Updates an existing weather.
     *
     * @param weather the weather to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weather,
     * or with status {@code 400 (Bad Request)} if the weather is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weather couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/weathers")
    public ResponseEntity<Weather> updateWeather(@Valid @RequestBody Weather weather) throws URISyntaxException {
        log.debug("REST request to update Weather : {}", weather);
        if (weather.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Weather result = weatherService.save(weather);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weather.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /weathers} : get all the weathers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weathers in body.
     */
    @GetMapping("/weathers")
    public ResponseEntity<List<Weather>> getAllWeathers(Pageable pageable) {
        log.debug("REST request to get a page of Weathers");
        Page<Weather> page = weatherService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /weathers/:id} : get the "id" weather.
     *
     * @param id the id of the weather to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weather, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/weathers/{id}")
    public ResponseEntity<Weather> getWeather(@PathVariable Long id) {
        log.debug("REST request to get Weather : {}", id);
        Optional<Weather> weather = weatherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weather);
    }

    /**
     * {@code DELETE  /weathers/:id} : delete the "id" weather.
     *
     * @param id the id of the weather to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/weathers/{id}")
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id) {
        log.debug("REST request to delete Weather : {}", id);
        weatherService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
