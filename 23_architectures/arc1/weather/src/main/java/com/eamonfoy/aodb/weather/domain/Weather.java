package com.eamonfoy.aodb.weather.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Weather.
 */
@Entity
@Table(name = "weather")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Weather implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "airport_code", nullable = false)
    private String airportCode;

    @NotNull
    @Column(name = "forecast_date", nullable = false)
    private Instant forecastDate;

    @NotNull
    @Column(name = "day_name", nullable = false)
    private String dayName;

    @NotNull
    @Column(name = "high_temperature_value", nullable = false)
    private Integer highTemperatureValue;

    @NotNull
    @Column(name = "low_temperature_value", nullable = false)
    private Integer lowTemperatureValue;

    @NotNull
    @Column(name = "feels_like_high_temperature", nullable = false)
    private Integer feelsLikeHighTemperature;

    @NotNull
    @Column(name = "feels_like_low_temperature", nullable = false)
    private Integer feelsLikeLowTemperature;

    @NotNull
    @Column(name = "phrase", nullable = false)
    private String phrase;

    @NotNull
    @Column(name = "probability_of_precip", nullable = false)
    private Integer probabilityOfPrecip;

    @NotNull
    @Column(name = "probability_of_precip_units", nullable = false)
    private String probabilityOfPrecipUnits;

    @NotNull
    @Column(name = "night_phrase", nullable = false)
    private String nightPhrase;

    @NotNull
    @Column(name = "night_icon", nullable = false)
    private Integer nightIcon;

    @NotNull
    @Column(name = "night_probability_of_precip", nullable = false)
    private Integer nightProbabilityOfPrecip;

    @NotNull
    @Column(name = "night_probability_of_precip_units", nullable = false)
    private String nightProbabilityOfPrecipUnits;

    @NotNull
    @Column(name = "icon", nullable = false)
    private Integer icon;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public Weather airportCode(String airportCode) {
        this.airportCode = airportCode;
        return this;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public Instant getForecastDate() {
        return forecastDate;
    }

    public Weather forecastDate(Instant forecastDate) {
        this.forecastDate = forecastDate;
        return this;
    }

    public void setForecastDate(Instant forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getDayName() {
        return dayName;
    }

    public Weather dayName(String dayName) {
        this.dayName = dayName;
        return this;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Integer getHighTemperatureValue() {
        return highTemperatureValue;
    }

    public Weather highTemperatureValue(Integer highTemperatureValue) {
        this.highTemperatureValue = highTemperatureValue;
        return this;
    }

    public void setHighTemperatureValue(Integer highTemperatureValue) {
        this.highTemperatureValue = highTemperatureValue;
    }

    public Integer getLowTemperatureValue() {
        return lowTemperatureValue;
    }

    public Weather lowTemperatureValue(Integer lowTemperatureValue) {
        this.lowTemperatureValue = lowTemperatureValue;
        return this;
    }

    public void setLowTemperatureValue(Integer lowTemperatureValue) {
        this.lowTemperatureValue = lowTemperatureValue;
    }

    public Integer getFeelsLikeHighTemperature() {
        return feelsLikeHighTemperature;
    }

    public Weather feelsLikeHighTemperature(Integer feelsLikeHighTemperature) {
        this.feelsLikeHighTemperature = feelsLikeHighTemperature;
        return this;
    }

    public void setFeelsLikeHighTemperature(Integer feelsLikeHighTemperature) {
        this.feelsLikeHighTemperature = feelsLikeHighTemperature;
    }

    public Integer getFeelsLikeLowTemperature() {
        return feelsLikeLowTemperature;
    }

    public Weather feelsLikeLowTemperature(Integer feelsLikeLowTemperature) {
        this.feelsLikeLowTemperature = feelsLikeLowTemperature;
        return this;
    }

    public void setFeelsLikeLowTemperature(Integer feelsLikeLowTemperature) {
        this.feelsLikeLowTemperature = feelsLikeLowTemperature;
    }

    public String getPhrase() {
        return phrase;
    }

    public Weather phrase(String phrase) {
        this.phrase = phrase;
        return this;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Integer getProbabilityOfPrecip() {
        return probabilityOfPrecip;
    }

    public Weather probabilityOfPrecip(Integer probabilityOfPrecip) {
        this.probabilityOfPrecip = probabilityOfPrecip;
        return this;
    }

    public void setProbabilityOfPrecip(Integer probabilityOfPrecip) {
        this.probabilityOfPrecip = probabilityOfPrecip;
    }

    public String getProbabilityOfPrecipUnits() {
        return probabilityOfPrecipUnits;
    }

    public Weather probabilityOfPrecipUnits(String probabilityOfPrecipUnits) {
        this.probabilityOfPrecipUnits = probabilityOfPrecipUnits;
        return this;
    }

    public void setProbabilityOfPrecipUnits(String probabilityOfPrecipUnits) {
        this.probabilityOfPrecipUnits = probabilityOfPrecipUnits;
    }

    public String getNightPhrase() {
        return nightPhrase;
    }

    public Weather nightPhrase(String nightPhrase) {
        this.nightPhrase = nightPhrase;
        return this;
    }

    public void setNightPhrase(String nightPhrase) {
        this.nightPhrase = nightPhrase;
    }

    public Integer getNightIcon() {
        return nightIcon;
    }

    public Weather nightIcon(Integer nightIcon) {
        this.nightIcon = nightIcon;
        return this;
    }

    public void setNightIcon(Integer nightIcon) {
        this.nightIcon = nightIcon;
    }

    public Integer getNightProbabilityOfPrecip() {
        return nightProbabilityOfPrecip;
    }

    public Weather nightProbabilityOfPrecip(Integer nightProbabilityOfPrecip) {
        this.nightProbabilityOfPrecip = nightProbabilityOfPrecip;
        return this;
    }

    public void setNightProbabilityOfPrecip(Integer nightProbabilityOfPrecip) {
        this.nightProbabilityOfPrecip = nightProbabilityOfPrecip;
    }

    public String getNightProbabilityOfPrecipUnits() {
        return nightProbabilityOfPrecipUnits;
    }

    public Weather nightProbabilityOfPrecipUnits(String nightProbabilityOfPrecipUnits) {
        this.nightProbabilityOfPrecipUnits = nightProbabilityOfPrecipUnits;
        return this;
    }

    public void setNightProbabilityOfPrecipUnits(String nightProbabilityOfPrecipUnits) {
        this.nightProbabilityOfPrecipUnits = nightProbabilityOfPrecipUnits;
    }

    public Integer getIcon() {
        return icon;
    }

    public Weather icon(Integer icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Weather)) {
            return false;
        }
        return id != null && id.equals(((Weather) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Weather{" +
            "id=" + getId() +
            ", airportCode='" + getAirportCode() + "'" +
            ", forecastDate='" + getForecastDate() + "'" +
            ", dayName='" + getDayName() + "'" +
            ", highTemperatureValue=" + getHighTemperatureValue() +
            ", lowTemperatureValue=" + getLowTemperatureValue() +
            ", feelsLikeHighTemperature=" + getFeelsLikeHighTemperature() +
            ", feelsLikeLowTemperature=" + getFeelsLikeLowTemperature() +
            ", phrase='" + getPhrase() + "'" +
            ", probabilityOfPrecip=" + getProbabilityOfPrecip() +
            ", probabilityOfPrecipUnits='" + getProbabilityOfPrecipUnits() + "'" +
            ", nightPhrase='" + getNightPhrase() + "'" +
            ", nightIcon=" + getNightIcon() +
            ", nightProbabilityOfPrecip=" + getNightProbabilityOfPrecip() +
            ", nightProbabilityOfPrecipUnits='" + getNightProbabilityOfPrecipUnits() + "'" +
            ", icon=" + getIcon() +
            "}";
    }
}
