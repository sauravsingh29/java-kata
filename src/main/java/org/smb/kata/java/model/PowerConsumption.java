package org.smb.kata.java.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class PowerConsumption {

    @JsonProperty("ts")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;

    @JsonProperty("mid")
    private String meterId;

    @JsonProperty("v")
    private Double consumption;

    @JsonProperty("bid")
    private String buildingId;

    private String identifier = "heat"; // setting default to Heat.


    public PowerConsumption(LocalDateTime timeStamp, String meterId, Double consumption, String buildingId, String identifier) {
        this.timeStamp = timeStamp;
        this.meterId = meterId;
        this.consumption = consumption;
        this.buildingId = buildingId;
        this.identifier = identifier;
    }

    public PowerConsumption() {
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMeterId() {
        return meterId;
    }

    public Double getConsumption() {
        return consumption;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowerConsumption that = (PowerConsumption) o;
        return timeStamp.equals(that.timeStamp) && meterId.equals(that.meterId) && buildingId.equals(that.buildingId) && identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, meterId, buildingId, identifier);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PowerConsumption.class.getSimpleName() + "[", "]")
                .add("timeStamp=" + timeStamp)
                .add("meterId='" + meterId + "'")
                .add("consumption=" + consumption)
                .add("buildingId='" + buildingId + "'")
                .add("identifier='" + identifier + "'")
                .toString();
    }
}
