package org.smb.kata.java.api;

import org.smb.kata.java.response.Consumption;
import org.smb.kata.java.service.impl.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/consumption")
public class ConsumptionApi {

    private final ConsumptionService consumptionService;

    @Autowired
    public ConsumptionApi(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @GetMapping
    public ResponseEntity<Set<Consumption>> getConsumptionOrderedByTimeAndMeter() {
        return ResponseEntity.ok(consumptionService.getConsumptionsOrderByTimeAndMeterId());
    }

    @GetMapping("/meter/{meterId}")
    public ResponseEntity<Double> totalConsumptionByMeter(@PathVariable("meterId") String meterId) {
        return ResponseEntity.ok(consumptionService.totalConsumptionByMeter(meterId));
    }

    @GetMapping("/building/{buildingId}")
    public ResponseEntity<Double> totalConsumptionByBuilding(@PathVariable("buildingId") String buildingId) {
        return ResponseEntity.ok(consumptionService.totalConsumptionByBuilding(buildingId));
    }
}
