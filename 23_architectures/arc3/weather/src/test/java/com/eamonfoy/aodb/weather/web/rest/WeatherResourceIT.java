package com.eamonfoy.aodb.weather.web.rest;

import com.eamonfoy.aodb.weather.WeatherApp;
import com.eamonfoy.aodb.weather.domain.Weather;
import com.eamonfoy.aodb.weather.repository.WeatherRepository;
import com.eamonfoy.aodb.weather.service.WeatherService;
import com.eamonfoy.aodb.weather.web.rest.errors.ExceptionTranslator;

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

import static com.eamonfoy.aodb.weather.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WeatherResource} REST controller.
 */
@SpringBootTest(classes = WeatherApp.class)
public class WeatherResourceIT {

    private static final String DEFAULT_AIRPORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AIRPORT_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_FORECAST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FORECAST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DAY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_HIGH_TEMPERATURE_VALUE = 1;
    private static final Integer UPDATED_HIGH_TEMPERATURE_VALUE = 2;

    private static final Integer DEFAULT_LOW_TEMPERATURE_VALUE = 1;
    private static final Integer UPDATED_LOW_TEMPERATURE_VALUE = 2;

    private static final Integer DEFAULT_FEELS_LIKE_HIGH_TEMPERATURE = 1;
    private static final Integer UPDATED_FEELS_LIKE_HIGH_TEMPERATURE = 2;

    private static final Integer DEFAULT_FEELS_LIKE_LOW_TEMPERATURE = 1;
    private static final Integer UPDATED_FEELS_LIKE_LOW_TEMPERATURE = 2;

    private static final String DEFAULT_PHRASE = "AAAAAAAAAA";
    private static final String UPDATED_PHRASE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROBABILITY_OF_PRECIP = 1;
    private static final Integer UPDATED_PROBABILITY_OF_PRECIP = 2;

    private static final String DEFAULT_PROBABILITY_OF_PRECIP_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_PROBABILITY_OF_PRECIP_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_NIGHT_PHRASE = "AAAAAAAAAA";
    private static final String UPDATED_NIGHT_PHRASE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NIGHT_ICON = 1;
    private static final Integer UPDATED_NIGHT_ICON = 2;

    private static final Integer DEFAULT_NIGHT_PROBABILITY_OF_PRECIP = 1;
    private static final Integer UPDATED_NIGHT_PROBABILITY_OF_PRECIP = 2;

    private static final String DEFAULT_NIGHT_PROBABILITY_OF_PRECIP_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_NIGHT_PROBABILITY_OF_PRECIP_UNITS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ICON = 1;
    private static final Integer UPDATED_ICON = 2;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherService weatherService;

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

    private MockMvc restWeatherMockMvc;

    private Weather weather;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeatherResource weatherResource = new WeatherResource(weatherService);
        this.restWeatherMockMvc = MockMvcBuilders.standaloneSetup(weatherResource)
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
    public static Weather createEntity(EntityManager em) {
        Weather weather = new Weather()
            .airportCode(DEFAULT_AIRPORT_CODE)
            .forecastDate(DEFAULT_FORECAST_DATE)
            .dayName(DEFAULT_DAY_NAME)
            .highTemperatureValue(DEFAULT_HIGH_TEMPERATURE_VALUE)
            .lowTemperatureValue(DEFAULT_LOW_TEMPERATURE_VALUE)
            .feelsLikeHighTemperature(DEFAULT_FEELS_LIKE_HIGH_TEMPERATURE)
            .feelsLikeLowTemperature(DEFAULT_FEELS_LIKE_LOW_TEMPERATURE)
            .phrase(DEFAULT_PHRASE)
            .probabilityOfPrecip(DEFAULT_PROBABILITY_OF_PRECIP)
            .probabilityOfPrecipUnits(DEFAULT_PROBABILITY_OF_PRECIP_UNITS)
            .nightPhrase(DEFAULT_NIGHT_PHRASE)
            .nightIcon(DEFAULT_NIGHT_ICON)
            .nightProbabilityOfPrecip(DEFAULT_NIGHT_PROBABILITY_OF_PRECIP)
            .nightProbabilityOfPrecipUnits(DEFAULT_NIGHT_PROBABILITY_OF_PRECIP_UNITS)
            .icon(DEFAULT_ICON);
        return weather;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weather createUpdatedEntity(EntityManager em) {
        Weather weather = new Weather()
            .airportCode(UPDATED_AIRPORT_CODE)
            .forecastDate(UPDATED_FORECAST_DATE)
            .dayName(UPDATED_DAY_NAME)
            .highTemperatureValue(UPDATED_HIGH_TEMPERATURE_VALUE)
            .lowTemperatureValue(UPDATED_LOW_TEMPERATURE_VALUE)
            .feelsLikeHighTemperature(UPDATED_FEELS_LIKE_HIGH_TEMPERATURE)
            .feelsLikeLowTemperature(UPDATED_FEELS_LIKE_LOW_TEMPERATURE)
            .phrase(UPDATED_PHRASE)
            .probabilityOfPrecip(UPDATED_PROBABILITY_OF_PRECIP)
            .probabilityOfPrecipUnits(UPDATED_PROBABILITY_OF_PRECIP_UNITS)
            .nightPhrase(UPDATED_NIGHT_PHRASE)
            .nightIcon(UPDATED_NIGHT_ICON)
            .nightProbabilityOfPrecip(UPDATED_NIGHT_PROBABILITY_OF_PRECIP)
            .nightProbabilityOfPrecipUnits(UPDATED_NIGHT_PROBABILITY_OF_PRECIP_UNITS)
            .icon(UPDATED_ICON);
        return weather;
    }

    @BeforeEach
    public void initTest() {
        weather = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeather() throws Exception {
        int databaseSizeBeforeCreate = weatherRepository.findAll().size();

        // Create the Weather
        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isCreated());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeCreate + 1);
        Weather testWeather = weatherList.get(weatherList.size() - 1);
        assertThat(testWeather.getAirportCode()).isEqualTo(DEFAULT_AIRPORT_CODE);
        assertThat(testWeather.getForecastDate()).isEqualTo(DEFAULT_FORECAST_DATE);
        assertThat(testWeather.getDayName()).isEqualTo(DEFAULT_DAY_NAME);
        assertThat(testWeather.getHighTemperatureValue()).isEqualTo(DEFAULT_HIGH_TEMPERATURE_VALUE);
        assertThat(testWeather.getLowTemperatureValue()).isEqualTo(DEFAULT_LOW_TEMPERATURE_VALUE);
        assertThat(testWeather.getFeelsLikeHighTemperature()).isEqualTo(DEFAULT_FEELS_LIKE_HIGH_TEMPERATURE);
        assertThat(testWeather.getFeelsLikeLowTemperature()).isEqualTo(DEFAULT_FEELS_LIKE_LOW_TEMPERATURE);
        assertThat(testWeather.getPhrase()).isEqualTo(DEFAULT_PHRASE);
        assertThat(testWeather.getProbabilityOfPrecip()).isEqualTo(DEFAULT_PROBABILITY_OF_PRECIP);
        assertThat(testWeather.getProbabilityOfPrecipUnits()).isEqualTo(DEFAULT_PROBABILITY_OF_PRECIP_UNITS);
        assertThat(testWeather.getNightPhrase()).isEqualTo(DEFAULT_NIGHT_PHRASE);
        assertThat(testWeather.getNightIcon()).isEqualTo(DEFAULT_NIGHT_ICON);
        assertThat(testWeather.getNightProbabilityOfPrecip()).isEqualTo(DEFAULT_NIGHT_PROBABILITY_OF_PRECIP);
        assertThat(testWeather.getNightProbabilityOfPrecipUnits()).isEqualTo(DEFAULT_NIGHT_PROBABILITY_OF_PRECIP_UNITS);
        assertThat(testWeather.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    @Transactional
    public void createWeatherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weatherRepository.findAll().size();

        // Create the Weather with an existing ID
        weather.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAirportCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setAirportCode(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkForecastDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setForecastDate(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setDayName(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHighTemperatureValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setHighTemperatureValue(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLowTemperatureValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setLowTemperatureValue(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeelsLikeHighTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setFeelsLikeHighTemperature(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeelsLikeLowTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setFeelsLikeLowTemperature(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhraseIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setPhrase(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProbabilityOfPrecipIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setProbabilityOfPrecip(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProbabilityOfPrecipUnitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setProbabilityOfPrecipUnits(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNightPhraseIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setNightPhrase(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNightIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setNightIcon(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNightProbabilityOfPrecipIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setNightProbabilityOfPrecip(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNightProbabilityOfPrecipUnitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setNightProbabilityOfPrecipUnits(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = weatherRepository.findAll().size();
        // set the field null
        weather.setIcon(null);

        // Create the Weather, which fails.

        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeathers() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        // Get all the weatherList
        restWeatherMockMvc.perform(get("/api/weathers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weather.getId().intValue())))
            .andExpect(jsonPath("$.[*].airportCode").value(hasItem(DEFAULT_AIRPORT_CODE)))
            .andExpect(jsonPath("$.[*].forecastDate").value(hasItem(DEFAULT_FORECAST_DATE.toString())))
            .andExpect(jsonPath("$.[*].dayName").value(hasItem(DEFAULT_DAY_NAME)))
            .andExpect(jsonPath("$.[*].highTemperatureValue").value(hasItem(DEFAULT_HIGH_TEMPERATURE_VALUE)))
            .andExpect(jsonPath("$.[*].lowTemperatureValue").value(hasItem(DEFAULT_LOW_TEMPERATURE_VALUE)))
            .andExpect(jsonPath("$.[*].feelsLikeHighTemperature").value(hasItem(DEFAULT_FEELS_LIKE_HIGH_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].feelsLikeLowTemperature").value(hasItem(DEFAULT_FEELS_LIKE_LOW_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].phrase").value(hasItem(DEFAULT_PHRASE)))
            .andExpect(jsonPath("$.[*].probabilityOfPrecip").value(hasItem(DEFAULT_PROBABILITY_OF_PRECIP)))
            .andExpect(jsonPath("$.[*].probabilityOfPrecipUnits").value(hasItem(DEFAULT_PROBABILITY_OF_PRECIP_UNITS)))
            .andExpect(jsonPath("$.[*].nightPhrase").value(hasItem(DEFAULT_NIGHT_PHRASE)))
            .andExpect(jsonPath("$.[*].nightIcon").value(hasItem(DEFAULT_NIGHT_ICON)))
            .andExpect(jsonPath("$.[*].nightProbabilityOfPrecip").value(hasItem(DEFAULT_NIGHT_PROBABILITY_OF_PRECIP)))
            .andExpect(jsonPath("$.[*].nightProbabilityOfPrecipUnits").value(hasItem(DEFAULT_NIGHT_PROBABILITY_OF_PRECIP_UNITS)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }
    
    @Test
    @Transactional
    public void getWeather() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        // Get the weather
        restWeatherMockMvc.perform(get("/api/weathers/{id}", weather.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weather.getId().intValue()))
            .andExpect(jsonPath("$.airportCode").value(DEFAULT_AIRPORT_CODE))
            .andExpect(jsonPath("$.forecastDate").value(DEFAULT_FORECAST_DATE.toString()))
            .andExpect(jsonPath("$.dayName").value(DEFAULT_DAY_NAME))
            .andExpect(jsonPath("$.highTemperatureValue").value(DEFAULT_HIGH_TEMPERATURE_VALUE))
            .andExpect(jsonPath("$.lowTemperatureValue").value(DEFAULT_LOW_TEMPERATURE_VALUE))
            .andExpect(jsonPath("$.feelsLikeHighTemperature").value(DEFAULT_FEELS_LIKE_HIGH_TEMPERATURE))
            .andExpect(jsonPath("$.feelsLikeLowTemperature").value(DEFAULT_FEELS_LIKE_LOW_TEMPERATURE))
            .andExpect(jsonPath("$.phrase").value(DEFAULT_PHRASE))
            .andExpect(jsonPath("$.probabilityOfPrecip").value(DEFAULT_PROBABILITY_OF_PRECIP))
            .andExpect(jsonPath("$.probabilityOfPrecipUnits").value(DEFAULT_PROBABILITY_OF_PRECIP_UNITS))
            .andExpect(jsonPath("$.nightPhrase").value(DEFAULT_NIGHT_PHRASE))
            .andExpect(jsonPath("$.nightIcon").value(DEFAULT_NIGHT_ICON))
            .andExpect(jsonPath("$.nightProbabilityOfPrecip").value(DEFAULT_NIGHT_PROBABILITY_OF_PRECIP))
            .andExpect(jsonPath("$.nightProbabilityOfPrecipUnits").value(DEFAULT_NIGHT_PROBABILITY_OF_PRECIP_UNITS))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON));
    }

    @Test
    @Transactional
    public void getNonExistingWeather() throws Exception {
        // Get the weather
        restWeatherMockMvc.perform(get("/api/weathers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeather() throws Exception {
        // Initialize the database
        weatherService.save(weather);

        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();

        // Update the weather
        Weather updatedWeather = weatherRepository.findById(weather.getId()).get();
        // Disconnect from session so that the updates on updatedWeather are not directly saved in db
        em.detach(updatedWeather);
        updatedWeather
            .airportCode(UPDATED_AIRPORT_CODE)
            .forecastDate(UPDATED_FORECAST_DATE)
            .dayName(UPDATED_DAY_NAME)
            .highTemperatureValue(UPDATED_HIGH_TEMPERATURE_VALUE)
            .lowTemperatureValue(UPDATED_LOW_TEMPERATURE_VALUE)
            .feelsLikeHighTemperature(UPDATED_FEELS_LIKE_HIGH_TEMPERATURE)
            .feelsLikeLowTemperature(UPDATED_FEELS_LIKE_LOW_TEMPERATURE)
            .phrase(UPDATED_PHRASE)
            .probabilityOfPrecip(UPDATED_PROBABILITY_OF_PRECIP)
            .probabilityOfPrecipUnits(UPDATED_PROBABILITY_OF_PRECIP_UNITS)
            .nightPhrase(UPDATED_NIGHT_PHRASE)
            .nightIcon(UPDATED_NIGHT_ICON)
            .nightProbabilityOfPrecip(UPDATED_NIGHT_PROBABILITY_OF_PRECIP)
            .nightProbabilityOfPrecipUnits(UPDATED_NIGHT_PROBABILITY_OF_PRECIP_UNITS)
            .icon(UPDATED_ICON);

        restWeatherMockMvc.perform(put("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeather)))
            .andExpect(status().isOk());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
        Weather testWeather = weatherList.get(weatherList.size() - 1);
        assertThat(testWeather.getAirportCode()).isEqualTo(UPDATED_AIRPORT_CODE);
        assertThat(testWeather.getForecastDate()).isEqualTo(UPDATED_FORECAST_DATE);
        assertThat(testWeather.getDayName()).isEqualTo(UPDATED_DAY_NAME);
        assertThat(testWeather.getHighTemperatureValue()).isEqualTo(UPDATED_HIGH_TEMPERATURE_VALUE);
        assertThat(testWeather.getLowTemperatureValue()).isEqualTo(UPDATED_LOW_TEMPERATURE_VALUE);
        assertThat(testWeather.getFeelsLikeHighTemperature()).isEqualTo(UPDATED_FEELS_LIKE_HIGH_TEMPERATURE);
        assertThat(testWeather.getFeelsLikeLowTemperature()).isEqualTo(UPDATED_FEELS_LIKE_LOW_TEMPERATURE);
        assertThat(testWeather.getPhrase()).isEqualTo(UPDATED_PHRASE);
        assertThat(testWeather.getProbabilityOfPrecip()).isEqualTo(UPDATED_PROBABILITY_OF_PRECIP);
        assertThat(testWeather.getProbabilityOfPrecipUnits()).isEqualTo(UPDATED_PROBABILITY_OF_PRECIP_UNITS);
        assertThat(testWeather.getNightPhrase()).isEqualTo(UPDATED_NIGHT_PHRASE);
        assertThat(testWeather.getNightIcon()).isEqualTo(UPDATED_NIGHT_ICON);
        assertThat(testWeather.getNightProbabilityOfPrecip()).isEqualTo(UPDATED_NIGHT_PROBABILITY_OF_PRECIP);
        assertThat(testWeather.getNightProbabilityOfPrecipUnits()).isEqualTo(UPDATED_NIGHT_PROBABILITY_OF_PRECIP_UNITS);
        assertThat(testWeather.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingWeather() throws Exception {
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();

        // Create the Weather

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeatherMockMvc.perform(put("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWeather() throws Exception {
        // Initialize the database
        weatherService.save(weather);

        int databaseSizeBeforeDelete = weatherRepository.findAll().size();

        // Delete the weather
        restWeatherMockMvc.perform(delete("/api/weathers/{id}", weather.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
