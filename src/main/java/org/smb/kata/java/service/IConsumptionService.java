package org.smb.kata.java.service;

import org.smb.kata.java.response.Consumption;

import java.util.Set;

public interface IConsumptionService {

    /**
     * Get all consumption data ordered by time and meter.
     *
     * @return Set of {@link Consumption}
     */
    Set<Consumption> getConsumptionsOrderByTimeAndMeterId();

    /**
     * Find the total consumption by meter.
     *
     * @param meterId Meter id.
     * @return total sum.
     */
    double totalConsumptionByMeter(final String meterId);

    /**
     * Find the total consumption by Building id.
     *
     * @param buildingId Building id.
     * @return total sum.
     */
    double totalConsumptionByBuilding(final String buildingId);
}
