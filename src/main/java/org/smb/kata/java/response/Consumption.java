package org.smb.kata.java.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

public class Consumption implements Serializable {

    private static final long serialVersionUID = -9119230554956109173L;

    @JsonProperty("ts")
    private LocalDateTime timeStamp;

    @JsonProperty("meter_id")
    private String meterId;

    @JsonProperty("building_id")
    private String buildingId;

    @JsonProperty("consumption")
    private Double consumption;

    @JsonProperty("type")
    private String identifier;

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Consumption.class.getSimpleName() + "[", "]")
                .add("timeStamp=" + timeStamp)
                .add("meterId='" + meterId + "'")
                .add("buildingId='" + buildingId + "'")
                .add("consumption=" + consumption)
                .add("identifier='" + identifier + "'")
                .toString();
    }
}
