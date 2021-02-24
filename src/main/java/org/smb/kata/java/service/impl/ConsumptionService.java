package org.smb.kata.java.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smb.kata.java.exception.PowerConsumptionException;
import org.smb.kata.java.model.PowerConsumption;
import org.smb.kata.java.response.Consumption;
import org.smb.kata.java.service.IConsumptionService;
import org.smb.kata.java.util.DataProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ConsumptionService implements IConsumptionService {

    private final DataProvider dataProvider;

    private static final Logger log = LoggerFactory.getLogger(ConsumptionService.class);

    private DecimalFormat decimalFormat = new DecimalFormat("#.####");

    @Autowired
    public ConsumptionService(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public Set<Consumption> getConsumptionsOrderByTimeAndMeterId() {
        log.debug("Started method [getConsumptionsOrderByTimeAndMeterId] execution.");
        LinkedHashSet<Consumption> powerConsumptions = dataProvider.getConsumptionData().stream()
                .sorted(comparing(PowerConsumption::getTimeStamp).thenComparing(PowerConsumption::getMeterId))
                .map(pc -> {
                    final Consumption consumption = new Consumption();
                    BeanUtils.copyProperties(pc, consumption);
                    return consumption;
                })
                .collect(toCollection(LinkedHashSet::new));
        log.debug("Finished method [getConsumptionsOrderByTimeAndMeterId] execution. Total response {}.", powerConsumptions.size());
        return powerConsumptions;
    }

    @Override
    public double totalConsumptionByMeter(String meterId) {
        log.debug("Started method [totalConsumptionByMeter] execution for meter id {}.", meterId);
        List<PowerConsumption> meterConsumptions = dataProvider.getConsumptionData().stream().filter(cd -> cd.getMeterId().equals(meterId))
                .collect(Collectors.toList());
        if (null == meterConsumptions || meterConsumptions.size() == 0) {
            log.warn("No meter found with id {}.", meterId);
            throw new PowerConsumptionException("Meter not found.", NOT_FOUND);
        }
        double total = meterConsumptions.stream().collect(Collectors.summingDouble(PowerConsumption::getConsumption));
        log.debug("Finished method [totalConsumptionByMeter] execution for meter id {}.Total response {}.", meterId, total);
        return Double.valueOf(decimalFormat.format(total));
    }

    @Override
    public double totalConsumptionByBuilding(String buildingId) {
        log.debug("Started method [totalConsumptionByBuilding] execution for building id {}.", buildingId);
        List<PowerConsumption> buildingConsumption = dataProvider.getConsumptionData().stream().filter(cd -> cd.getBuildingId().equals(buildingId))
                .collect(Collectors.toList());
        if (null == buildingConsumption || buildingConsumption.size() == 0) {
            log.warn("No meter found with id {}.", buildingId);
            throw new PowerConsumptionException("Meter not found.", NOT_FOUND);
        }
        double total = buildingConsumption.stream().collect(Collectors.summingDouble(PowerConsumption::getConsumption));
        log.debug("Finished method [totalConsumptionByBuilding] execution for building id {}. Total response {}.", buildingId, total);
        return Double.valueOf(decimalFormat.format(total));
    }
}
