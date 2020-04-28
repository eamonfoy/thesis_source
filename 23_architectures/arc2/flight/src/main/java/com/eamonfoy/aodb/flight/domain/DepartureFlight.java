package com.eamonfoy.aodb.flight.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DepartureFlight.
 */
@Entity
@Table(name = "departure_flight")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DepartureFlight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actual")
    private Instant actual;

    @Column(name = "estimated")
    private Instant estimated;

    @NotNull
    @Column(name = "scheduled", nullable = false)
    private Instant scheduled;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "aircraft", nullable = false)
    private String aircraft;

    @NotNull
    @Column(name = "terminal", nullable = false)
    private String terminal;

    @NotNull
    @Column(name = "duration", nullable = false)
    private String duration;

    @NotNull
    @Column(name = "tail_number", nullable = false)
    private String tailNumber;

    @NotNull
    @Column(name = "airport_code", nullable = false)
    private String airportCode;

    @NotNull
    @Column(name = "airline", nullable = false)
    private String airline;

    @NotNull
    @Column(name = "flight_number", nullable = false)
    private String flightNumber;

    @NotNull
    @Column(name = "gate", nullable = false)
    private String gate;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "status_text", nullable = false)
    private String statusText;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getActual() {
        return actual;
    }

    public DepartureFlight actual(Instant actual) {
        this.actual = actual;
        return this;
    }

    public void setActual(Instant actual) {
        this.actual = actual;
    }

    public Instant getEstimated() {
        return estimated;
    }

    public DepartureFlight estimated(Instant estimated) {
        this.estimated = estimated;
        return this;
    }

    public void setEstimated(Instant estimated) {
        this.estimated = estimated;
    }

    public Instant getScheduled() {
        return scheduled;
    }

    public DepartureFlight scheduled(Instant scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public void setScheduled(Instant scheduled) {
        this.scheduled = scheduled;
    }

    public String getCity() {
        return city;
    }

    public DepartureFlight city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAircraft() {
        return aircraft;
    }

    public DepartureFlight aircraft(String aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getTerminal() {
        return terminal;
    }

    public DepartureFlight terminal(String terminal) {
        this.terminal = terminal;
        return this;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getDuration() {
        return duration;
    }

    public DepartureFlight duration(String duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public DepartureFlight tailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
        return this;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public DepartureFlight airportCode(String airportCode) {
        this.airportCode = airportCode;
        return this;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirline() {
        return airline;
    }

    public DepartureFlight airline(String airline) {
        this.airline = airline;
        return this;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public DepartureFlight flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getGate() {
        return gate;
    }

    public DepartureFlight gate(String gate) {
        this.gate = gate;
        return this;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getStatus() {
        return status;
    }

    public DepartureFlight status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public DepartureFlight statusText(String statusText) {
        this.statusText = statusText;
        return this;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartureFlight)) {
            return false;
        }
        return id != null && id.equals(((DepartureFlight) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DepartureFlight{" +
            "id=" + getId() +
            ", actual='" + getActual() + "'" +
            ", estimated='" + getEstimated() + "'" +
            ", scheduled='" + getScheduled() + "'" +
            ", city='" + getCity() + "'" +
            ", aircraft='" + getAircraft() + "'" +
            ", terminal='" + getTerminal() + "'" +
            ", duration='" + getDuration() + "'" +
            ", tailNumber='" + getTailNumber() + "'" +
            ", airportCode='" + getAirportCode() + "'" +
            ", airline='" + getAirline() + "'" +
            ", flightNumber='" + getFlightNumber() + "'" +
            ", gate='" + getGate() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusText='" + getStatusText() + "'" +
            "}";
    }
}
