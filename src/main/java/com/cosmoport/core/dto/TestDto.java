package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class TestDto extends Entity implements Serializable {
    private static final long serialVersionUID = 4278429126310424089L;

    private final int departureTime;
    private final String type;
    private final int duration;
    private final String destination;
    private final double cost;
    private final String status;
    private final int gateNo;
    private final int passengersMax;
    private final int bought;
    private final int dateAdded;

    @JsonCreator
    public TestDto(@JsonProperty("id") long id, @JsonProperty("departure_time") int departureTime, @JsonProperty("type") String type,
                   @JsonProperty("duration") int duration, @JsonProperty("destination") String destination,
                   @JsonProperty("cost") double cost, @JsonProperty("status") String status,
                   @JsonProperty("gate_no") int gateNo, @JsonProperty("passengers_max") int passengersMax,
                   @JsonProperty("bought") int bought,
                   @JsonProperty("date_added") int dateAdded) {
        this.id = id;
        this.departureTime = departureTime;
        this.type = type;
        this.duration = duration;
        this.destination = destination;
        this.cost = cost;
        this.status = status;
        this.gateNo = gateNo;
        this.passengersMax = passengersMax;
        this.bought = bought;
        this.dateAdded = dateAdded;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public String getDestination() {
        return destination;
    }

    public double getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public int getGateNo() {
        return gateNo;
    }

    public int getPassengersMax() {
        return passengersMax;
    }

    public int getBought() {
        return bought;
    }

    public int getDateAdded() {
        return dateAdded;
    }
}
