package com.eamonfoy.aodb.flight.web.rest;

import com.eamonfoy.aodb.flight.FlightApp;
import com.eamonfoy.aodb.flight.domain.DepartureFlight;
import com.eamonfoy.aodb.flight.repository.DepartureFlightRepository;
import com.eamonfoy.aodb.flight.service.DepartureFlightService;
import com.eamonfoy.aodb.flight.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.eamonfoy.aodb.flight.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DepartureFlightResource} REST controller.
 */
@SpringBootTest(classes = FlightApp.class)
public class DepartureFlightResourceIT {

    private static final Instant DEFAULT_ACTUAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTUAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ESTIMATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SCHEDULED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SCHEDULED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_AIRCRAFT = "AAAAAAAAAA";
    private static final String UPDATED_AIRCRAFT = "BBBBBBBBBB";

    private static final String DEFAULT_TERMINAL = "AAAAAAAAAA";
    private static final String UPDATED_TERMINAL = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_TAIL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAIL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AIRPORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AIRPORT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_AIRLINE = "AAAAAAAAAA";
    private static final String UPDATED_AIRLINE = "BBBBBBBBBB";

    private static final String DEFAULT_FLIGHT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GATE = "AAAAAAAAAA";
    private static final String UPDATED_GATE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_TEXT = "BBBBBBBBBB";

    @Autowired
    private DepartureFlightRepository departureFlightRepository;

    @Autowired
    private DepartureFlightService departureFlightService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDepartureFlightMockMvc;

    private DepartureFlight departureFlight;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartureFlightResource departureFlightResource = new DepartureFlightResource(departureFlightService);
        this.restDepartureFlightMockMvc = MockMvcBuilders.standaloneSetup(departureFlightResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartureFlight createEntity(EntityManager em) {
        DepartureFlight departureFlight = new DepartureFlight()
            .actual(DEFAULT_ACTUAL)
            .estimated(DEFAULT_ESTIMATED)
            .scheduled(DEFAULT_SCHEDULED)
            .city(DEFAULT_CITY)
            .aircraft(DEFAULT_AIRCRAFT)
            .terminal(DEFAULT_TERMINAL)
            .duration(DEFAULT_DURATION)
            .tailNumber(DEFAULT_TAIL_NUMBER)
            .airportCode(DEFAULT_AIRPORT_CODE)
            .airline(DEFAULT_AIRLINE)
            .flightNumber(DEFAULT_FLIGHT_NUMBER)
            .gate(DEFAULT_GATE)
            .status(DEFAULT_STATUS)
            .statusText(DEFAULT_STATUS_TEXT);
        return departureFlight;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartureFlight createUpdatedEntity(EntityManager em) {
        DepartureFlight departureFlight = new DepartureFlight()
            .actual(UPDATED_ACTUAL)
            .estimated(UPDATED_ESTIMATED)
            .scheduled(UPDATED_SCHEDULED)
            .city(UPDATED_CITY)
            .aircraft(UPDATED_AIRCRAFT)
            .terminal(UPDATED_TERMINAL)
            .duration(UPDATED_DURATION)
            .tailNumber(UPDATED_TAIL_NUMBER)
            .airportCode(UPDATED_AIRPORT_CODE)
            .airline(UPDATED_AIRLINE)
            .flightNumber(UPDATED_FLIGHT_NUMBER)
            .gate(UPDATED_GATE)
            .status(UPDATED_STATUS)
            .statusText(UPDATED_STATUS_TEXT);
        return departureFlight;
    }

    @BeforeEach
    public void initTest() {
        departureFlight = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartureFlight() throws Exception {
        int databaseSizeBeforeCreate = departureFlightRepository.findAll().size();

        // Create the DepartureFlight
        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isCreated());

        // Validate the DepartureFlight in the database
        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeCreate + 1);
        DepartureFlight testDepartureFlight = departureFlightList.get(departureFlightList.size() - 1);
        assertThat(testDepartureFlight.getActual()).isEqualTo(DEFAULT_ACTUAL);
        assertThat(testDepartureFlight.getEstimated()).isEqualTo(DEFAULT_ESTIMATED);
        assertThat(testDepartureFlight.getScheduled()).isEqualTo(DEFAULT_SCHEDULED);
        assertThat(testDepartureFlight.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDepartureFlight.getAircraft()).isEqualTo(DEFAULT_AIRCRAFT);
        assertThat(testDepartureFlight.getTerminal()).isEqualTo(DEFAULT_TERMINAL);
        assertThat(testDepartureFlight.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testDepartureFlight.getTailNumber()).isEqualTo(DEFAULT_TAIL_NUMBER);
        assertThat(testDepartureFlight.getAirportCode()).isEqualTo(DEFAULT_AIRPORT_CODE);
        assertThat(testDepartureFlight.getAirline()).isEqualTo(DEFAULT_AIRLINE);
        assertThat(testDepartureFlight.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testDepartureFlight.getGate()).isEqualTo(DEFAULT_GATE);
        assertThat(testDepartureFlight.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDepartureFlight.getStatusText()).isEqualTo(DEFAULT_STATUS_TEXT);
    }

    @Test
    @Transactional
    public void createDepartureFlightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departureFlightRepository.findAll().size();

        // Create the DepartureFlight with an existing ID
        departureFlight.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        // Validate the DepartureFlight in the database
        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkScheduledIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setScheduled(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setCity(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAircraftIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setAircraft(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTerminalIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setTerminal(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setDuration(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTailNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setTailNumber(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAirportCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setAirportCode(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAirlineIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setAirline(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlightNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setFlightNumber(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGateIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setGate(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setStatus(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = departureFlightRepository.findAll().size();
        // set the field null
        departureFlight.setStatusText(null);

        // Create the DepartureFlight, which fails.

        restDepartureFlightMockMvc.perform(post("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartureFlights() throws Exception {
        // Initialize the database
        departureFlightRepository.saveAndFlush(departureFlight);

        // Get all the departureFlightList
        restDepartureFlightMockMvc.perform(get("/api/departure-flights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departureFlight.getId().intValue())))
            .andExpect(jsonPath("$.[*].actual").value(hasItem(DEFAULT_ACTUAL.toString())))
            .andExpect(jsonPath("$.[*].estimated").value(hasItem(DEFAULT_ESTIMATED.toString())))
            .andExpect(jsonPath("$.[*].scheduled").value(hasItem(DEFAULT_SCHEDULED.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].aircraft").value(hasItem(DEFAULT_AIRCRAFT)))
            .andExpect(jsonPath("$.[*].terminal").value(hasItem(DEFAULT_TERMINAL)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].tailNumber").value(hasItem(DEFAULT_TAIL_NUMBER)))
            .andExpect(jsonPath("$.[*].airportCode").value(hasItem(DEFAULT_AIRPORT_CODE)))
            .andExpect(jsonPath("$.[*].airline").value(hasItem(DEFAULT_AIRLINE)))
            .andExpect(jsonPath("$.[*].flightNumber").value(hasItem(DEFAULT_FLIGHT_NUMBER)))
            .andExpect(jsonPath("$.[*].gate").value(hasItem(DEFAULT_GATE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusText").value(hasItem(DEFAULT_STATUS_TEXT)));
    }
    
    @Test
    @Transactional
    public void getDepartureFlight() throws Exception {
        // Initialize the database
        departureFlightRepository.saveAndFlush(departureFlight);

        // Get the departureFlight
        restDepartureFlightMockMvc.perform(get("/api/departure-flights/{id}", departureFlight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departureFlight.getId().intValue()))
            .andExpect(jsonPath("$.actual").value(DEFAULT_ACTUAL.toString()))
            .andExpect(jsonPath("$.estimated").value(DEFAULT_ESTIMATED.toString()))
            .andExpect(jsonPath("$.scheduled").value(DEFAULT_SCHEDULED.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.aircraft").value(DEFAULT_AIRCRAFT))
            .andExpect(jsonPath("$.terminal").value(DEFAULT_TERMINAL))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.tailNumber").value(DEFAULT_TAIL_NUMBER))
            .andExpect(jsonPath("$.airportCode").value(DEFAULT_AIRPORT_CODE))
            .andExpect(jsonPath("$.airline").value(DEFAULT_AIRLINE))
            .andExpect(jsonPath("$.flightNumber").value(DEFAULT_FLIGHT_NUMBER))
            .andExpect(jsonPath("$.gate").value(DEFAULT_GATE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusText").value(DEFAULT_STATUS_TEXT));
    }

    @Test
    @Transactional
    public void getNonExistingDepartureFlight() throws Exception {
        // Get the departureFlight
        restDepartureFlightMockMvc.perform(get("/api/departure-flights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartureFlight() throws Exception {
        // Initialize the database
        departureFlightService.save(departureFlight);

        int databaseSizeBeforeUpdate = departureFlightRepository.findAll().size();

        // Update the departureFlight
        DepartureFlight updatedDepartureFlight = departureFlightRepository.findById(departureFlight.getId()).get();
        // Disconnect from session so that the updates on updatedDepartureFlight are not directly saved in db
        em.detach(updatedDepartureFlight);
        updatedDepartureFlight
            .actual(UPDATED_ACTUAL)
            .estimated(UPDATED_ESTIMATED)
            .scheduled(UPDATED_SCHEDULED)
            .city(UPDATED_CITY)
            .aircraft(UPDATED_AIRCRAFT)
            .terminal(UPDATED_TERMINAL)
            .duration(UPDATED_DURATION)
            .tailNumber(UPDATED_TAIL_NUMBER)
            .airportCode(UPDATED_AIRPORT_CODE)
            .airline(UPDATED_AIRLINE)
            .flightNumber(UPDATED_FLIGHT_NUMBER)
            .gate(UPDATED_GATE)
            .status(UPDATED_STATUS)
            .statusText(UPDATED_STATUS_TEXT);

        restDepartureFlightMockMvc.perform(put("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepartureFlight)))
            .andExpect(status().isOk());

        // Validate the DepartureFlight in the database
        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeUpdate);
        DepartureFlight testDepartureFlight = departureFlightList.get(departureFlightList.size() - 1);
        assertThat(testDepartureFlight.getActual()).isEqualTo(UPDATED_ACTUAL);
        assertThat(testDepartureFlight.getEstimated()).isEqualTo(UPDATED_ESTIMATED);
        assertThat(testDepartureFlight.getScheduled()).isEqualTo(UPDATED_SCHEDULED);
        assertThat(testDepartureFlight.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDepartureFlight.getAircraft()).isEqualTo(UPDATED_AIRCRAFT);
        assertThat(testDepartureFlight.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testDepartureFlight.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testDepartureFlight.getTailNumber()).isEqualTo(UPDATED_TAIL_NUMBER);
        assertThat(testDepartureFlight.getAirportCode()).isEqualTo(UPDATED_AIRPORT_CODE);
        assertThat(testDepartureFlight.getAirline()).isEqualTo(UPDATED_AIRLINE);
        assertThat(testDepartureFlight.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testDepartureFlight.getGate()).isEqualTo(UPDATED_GATE);
        assertThat(testDepartureFlight.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDepartureFlight.getStatusText()).isEqualTo(UPDATED_STATUS_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartureFlight() throws Exception {
        int databaseSizeBeforeUpdate = departureFlightRepository.findAll().size();

        // Create the DepartureFlight

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartureFlightMockMvc.perform(put("/api/departure-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departureFlight)))
            .andExpect(status().isBadRequest());

        // Validate the DepartureFlight in the database
        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartureFlight() throws Exception {
        // Initialize the database
        departureFlightService.save(departureFlight);

        int databaseSizeBeforeDelete = departureFlightRepository.findAll().size();

        // Delete the departureFlight
        restDepartureFlightMockMvc.perform(delete("/api/departure-flights/{id}", departureFlight.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepartureFlight> departureFlightList = departureFlightRepository.findAll();
        assertThat(departureFlightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
