package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Data Transfer Object for an event.
 * It stores all references as ids on referenced objects.
 *
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public final class EventDto extends Entity implements Serializable {
    private static final long serialVersionUID = -5204432920845515270L;

    private final String eventDate;
    private final long eventTypeId;
    private final long eventStatusId;
    private final long eventDestinationId;
    private final long gateId;
    private final long startTime;
    private final long durationTime;
    private final long repeatInterval;
    private final double cost;
    private final long peopleLimit;
    private final long contestants;
    private final String dateAdded;

    @JsonCreator
    public EventDto(@JsonProperty("id") long id,
                    @JsonProperty("event_date") String eventDate,
                    @JsonProperty("event_type_id") long eventTypeId,
                    @JsonProperty("event_status_id") long eventStatusId,
                    @JsonProperty("event_destination_id") long eventDestinationId,
                    @JsonProperty("gate_id") long gateId,
                    @JsonProperty("start_time") long startTime,
                    @JsonProperty("duration_time") long durationTime,
                    @JsonProperty("repeat_interval") long repeatInterval,
                    @JsonProperty("cost") double cost,
                    @JsonProperty("people_limit") long peopleLimit,
                    @JsonProperty("contestants") long contestants,
                    @JsonProperty("date_added") String dateAdded) {
        this.id = id;
        this.eventDate = eventDate;
        this.eventTypeId = eventTypeId;
        this.eventStatusId = eventStatusId;
        this.eventDestinationId = eventDestinationId;
        this.gateId = gateId;
        this.startTime = startTime;
        this.durationTime = durationTime;
        this.repeatInterval = repeatInterval;
        this.cost = cost;
        this.peopleLimit = peopleLimit;
        this.contestants = contestants;
        this.dateAdded = dateAdded;
    }

    public String getEventDate() {
        return eventDate;
    }

    public long getEventTypeId() {
        return eventTypeId;
    }

    public long getEventStatusId() {
        return eventStatusId;
    }

    public long getEventDestinationId() {
        return eventDestinationId;
    }

    public long getGateId() {
        return gateId;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public double getCost() {
        return cost;
    }

    public long getPeopleLimit() {
        return peopleLimit;
    }

    public long getContestants() {
        return contestants;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    @Override
    public String toString() {
        return "EventDto{" +
                "eventDate='" + eventDate + '\'' +
                ", eventTypeId=" + eventTypeId +
                ", eventStatusId=" + eventStatusId +
                ", eventDestinationId=" + eventDestinationId +
                ", gateId=" + gateId +
                ", startTime=" + startTime +
                ", durationTime=" + durationTime +
                ", repeatInterval=" + repeatInterval +
                ", cost=" + cost +
                ", peopleLimit=" + peopleLimit +
                ", contestants=" + contestants +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }
}
