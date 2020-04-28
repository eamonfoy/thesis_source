package com.eamonfoy.aodb.flight.web.rest;

import com.eamonfoy.aodb.flight.FlightApp;
import com.eamonfoy.aodb.flight.domain.ArrivalFlight;
import com.eamonfoy.aodb.flight.repository.ArrivalFlightRepository;
import com.eamonfoy.aodb.flight.service.ArrivalFlightService;
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
 * Integration tests for the {@link ArrivalFlightResource} REST controller.
 */
@SpringBootTest(classes = FlightApp.class)
public class ArrivalFlightResourceIT {

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

    private static final String DEFAULT_CLAIM = "AAAAAAAAAA";
    private static final String UPDATED_CLAIM = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_TEXT = "BBBBBBBBBB";

    @Autowired
    private ArrivalFlightRepository arrivalFlightRepository;

    @Autowired
    private ArrivalFlightService arrivalFlightService;

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

    private MockMvc restArrivalFlightMockMvc;

    private ArrivalFlight arrivalFlight;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArrivalFlightResource arrivalFlightResource = new ArrivalFlightResource(arrivalFlightService);
        this.restArrivalFlightMockMvc = MockMvcBuilders.standaloneSetup(arrivalFlightResource)
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
    public static ArrivalFlight createEntity(EntityManager em) {
        ArrivalFlight arrivalFlight = new ArrivalFlight()
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
            .claim(DEFAULT_CLAIM)
            .status(DEFAULT_STATUS)
            .statusText(DEFAULT_STATUS_TEXT);
        return arrivalFlight;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArrivalFlight createUpdatedEntity(EntityManager em) {
        ArrivalFlight arrivalFlight = new ArrivalFlight()
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
            .claim(UPDATED_CLAIM)
            .status(UPDATED_STATUS)
            .statusText(UPDATED_STATUS_TEXT);
        return arrivalFlight;
    }

    @BeforeEach
    public void initTest() {
        arrivalFlight = createEntity(em);
    }

    @Test
    @Transactional
    public void createArrivalFlight() throws Exception {
        int databaseSizeBeforeCreate = arrivalFlightRepository.findAll().size();

        // Create the ArrivalFlight
        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isCreated());

        // Validate the ArrivalFlight in the database
        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeCreate + 1);
        ArrivalFlight testArrivalFlight = arrivalFlightList.get(arrivalFlightList.size() - 1);
        assertThat(testArrivalFlight.getActual()).isEqualTo(DEFAULT_ACTUAL);
        assertThat(testArrivalFlight.getEstimated()).isEqualTo(DEFAULT_ESTIMATED);
        assertThat(testArrivalFlight.getScheduled()).isEqualTo(DEFAULT_SCHEDULED);
        assertThat(testArrivalFlight.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testArrivalFlight.getAircraft()).isEqualTo(DEFAULT_AIRCRAFT);
        assertThat(testArrivalFlight.getTerminal()).isEqualTo(DEFAULT_TERMINAL);
        assertThat(testArrivalFlight.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testArrivalFlight.getTailNumber()).isEqualTo(DEFAULT_TAIL_NUMBER);
        assertThat(testArrivalFlight.getAirportCode()).isEqualTo(DEFAULT_AIRPORT_CODE);
        assertThat(testArrivalFlight.getAirline()).isEqualTo(DEFAULT_AIRLINE);
        assertThat(testArrivalFlight.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testArrivalFlight.getClaim()).isEqualTo(DEFAULT_CLAIM);
        assertThat(testArrivalFlight.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testArrivalFlight.getStatusText()).isEqualTo(DEFAULT_STATUS_TEXT);
    }

    @Test
    @Transactional
    public void createArrivalFlightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arrivalFlightRepository.findAll().size();

        // Create the ArrivalFlight with an existing ID
        arrivalFlight.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        // Validate the ArrivalFlight in the database
        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkScheduledIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setScheduled(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setCity(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAircraftIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setAircraft(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTerminalIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setTerminal(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setDuration(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTailNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setTailNumber(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAirportCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setAirportCode(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAirlineIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setAirline(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlightNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setFlightNumber(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setStatus(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = arrivalFlightRepository.findAll().size();
        // set the field null
        arrivalFlight.setStatusText(null);

        // Create the ArrivalFlight, which fails.

        restArrivalFlightMockMvc.perform(post("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArrivalFlights() throws Exception {
        // Initialize the database
        arrivalFlightRepository.saveAndFlush(arrivalFlight);

        // Get all the arrivalFlightList
        restArrivalFlightMockMvc.perform(get("/api/arrival-flights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arrivalFlight.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].claim").value(hasItem(DEFAULT_CLAIM)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusText").value(hasItem(DEFAULT_STATUS_TEXT)));
    }
    
    @Test
    @Transactional
    public void getArrivalFlight() throws Exception {
        // Initialize the database
        arrivalFlightRepository.saveAndFlush(arrivalFlight);

        // Get the arrivalFlight
        restArrivalFlightMockMvc.perform(get("/api/arrival-flights/{id}", arrivalFlight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arrivalFlight.getId().intValue()))
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
            .andExpect(jsonPath("$.claim").value(DEFAULT_CLAIM))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusText").value(DEFAULT_STATUS_TEXT));
    }

    @Test
    @Transactional
    public void getNonExistingArrivalFlight() throws Exception {
        // Get the arrivalFlight
        restArrivalFlightMockMvc.perform(get("/api/arrival-flights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArrivalFlight() throws Exception {
        // Initialize the database
        arrivalFlightService.save(arrivalFlight);

        int databaseSizeBeforeUpdate = arrivalFlightRepository.findAll().size();

        // Update the arrivalFlight
        ArrivalFlight updatedArrivalFlight = arrivalFlightRepository.findById(arrivalFlight.getId()).get();
        // Disconnect from session so that the updates on updatedArrivalFlight are not directly saved in db
        em.detach(updatedArrivalFlight);
        updatedArrivalFlight
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
            .claim(UPDATED_CLAIM)
            .status(UPDATED_STATUS)
            .statusText(UPDATED_STATUS_TEXT);

        restArrivalFlightMockMvc.perform(put("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArrivalFlight)))
            .andExpect(status().isOk());

        // Validate the ArrivalFlight in the database
        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeUpdate);
        ArrivalFlight testArrivalFlight = arrivalFlightList.get(arrivalFlightList.size() - 1);
        assertThat(testArrivalFlight.getActual()).isEqualTo(UPDATED_ACTUAL);
        assertThat(testArrivalFlight.getEstimated()).isEqualTo(UPDATED_ESTIMATED);
        assertThat(testArrivalFlight.getScheduled()).isEqualTo(UPDATED_SCHEDULED);
        assertThat(testArrivalFlight.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testArrivalFlight.getAircraft()).isEqualTo(UPDATED_AIRCRAFT);
        assertThat(testArrivalFlight.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testArrivalFlight.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testArrivalFlight.getTailNumber()).isEqualTo(UPDATED_TAIL_NUMBER);
        assertThat(testArrivalFlight.getAirportCode()).isEqualTo(UPDATED_AIRPORT_CODE);
        assertThat(testArrivalFlight.getAirline()).isEqualTo(UPDATED_AIRLINE);
        assertThat(testArrivalFlight.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testArrivalFlight.getClaim()).isEqualTo(UPDATED_CLAIM);
        assertThat(testArrivalFlight.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testArrivalFlight.getStatusText()).isEqualTo(UPDATED_STATUS_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingArrivalFlight() throws Exception {
        int databaseSizeBeforeUpdate = arrivalFlightRepository.findAll().size();

        // Create the ArrivalFlight

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArrivalFlightMockMvc.perform(put("/api/arrival-flights")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrivalFlight)))
            .andExpect(status().isBadRequest());

        // Validate the ArrivalFlight in the database
        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArrivalFlight() throws Exception {
        // Initialize the database
        arrivalFlightService.save(arrivalFlight);

        int databaseSizeBeforeDelete = arrivalFlightRepository.findAll().size();

        // Delete the arrivalFlight
        restArrivalFlightMockMvc.perform(delete("/api/arrival-flights/{id}", arrivalFlight.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArrivalFlight> arrivalFlightList = arrivalFlightRepository.findAll();
        assertThat(arrivalFlightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
