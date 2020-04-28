package com.eamonfoy.aodb.weather.service;

import com.eamonfoy.aodb.weather.domain.Weather;
import com.eamonfoy.aodb.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Weather}.
 */
@Service
@Transactional
public class WeatherService {

    private final Logger log = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * Save a weather.
     *
     * @param weather the entity to save.
     * @return the persisted entity.
     */
    public Weather save(Weather weather) {
        log.debug("Request to save Weather : {}", weather);
        return weatherRepository.save(weather);
    }

    /**
     * Get all the weathers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Weather> findAll(Pageable pageable) {
        log.debug("Request to get all Weathers");
        return weatherRepository.findAll(pageable);
    }

    /**
     * Get one weather by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Weather> findOne(Long id) {
        log.debug("Request to get Weather : {}", id);
        return weatherRepository.findById(id);
    }

    /**
     * Delete the weather by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Weather : {}", id);
        weatherRepository.deleteById(id);
    }
}
