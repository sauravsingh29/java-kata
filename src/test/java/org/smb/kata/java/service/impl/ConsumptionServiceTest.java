package org.smb.kata.java.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.smb.kata.java.exception.PowerConsumptionException;
import org.smb.kata.java.model.PowerConsumption;
import org.smb.kata.java.response.Consumption;
import org.smb.kata.java.util.DataProvider;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConsumptionServiceTest {

    @Mock
    private DataProvider dataProvider;

    @InjectMocks
    private ConsumptionService consumptionService;

    List<PowerConsumption> powerConsumptions;

    @BeforeEach
    public void setup() {
        powerConsumptions = new ArrayList<>(0);
        powerConsumptions.add(new PowerConsumption(LocalDateTime.MAX, randomUUID().toString(), 8.7584, randomUUID().toString(), "electricity"));
        powerConsumptions.add(new PowerConsumption(LocalDateTime.MIN, randomUUID().toString(), 0.235, randomUUID().toString(), "heat"));
    }

    @Test
    @Order(1)
    void getConsumptionsOrderByTimeAndMeterId() {
        when(dataProvider.getConsumptionData()).thenReturn(powerConsumptions);
        Set<Consumption> actual = consumptionService.getConsumptionsOrderByTimeAndMeterId();
        assertEquals(powerConsumptions.size(), actual.size());
        assertEquals(actual.stream().findFirst().get().getTimeStamp(), LocalDateTime.MIN);
    }

    @Test
    @Order(2)
    void totalConsumptionByMeter_notfound() {
        when(dataProvider.getConsumptionData()).thenReturn(Collections.emptyList());
        assertThrows(PowerConsumptionException.class, () -> consumptionService.totalConsumptionByMeter(randomUUID().toString()));
    }

    @Test
    @Order(3)
    void totalConsumptionByMeter() {
        String meterId = powerConsumptions.get(0).getMeterId();
        when(dataProvider.getConsumptionData()).thenReturn(powerConsumptions);
        double actual = consumptionService.totalConsumptionByMeter(meterId);
        assertEquals(8.7584, actual);
    }

    @Test
    @Order(4)
    void totalConsumptionByBuilding_notfound() {
        when(dataProvider.getConsumptionData()).thenReturn(Collections.emptyList());
        assertThrows(PowerConsumptionException.class, () -> consumptionService.totalConsumptionByBuilding(randomUUID().toString()));
    }

    @Test
    @Order(5)
    void totalConsumptionByBuilding() {
        PowerConsumption pc = powerConsumptions.get(0);
        powerConsumptions.add(new PowerConsumption(LocalDateTime.now(), randomUUID().toString(), 8.547, pc.getBuildingId(), "heat"));
        when(dataProvider.getConsumptionData()).thenReturn(powerConsumptions);
        double expected = consumptionService.totalConsumptionByBuilding(pc.getBuildingId());
        String act = new DecimalFormat("#.####").format(8.547 + pc.getConsumption());
        assertEquals(Double.valueOf(act), expected);
    }
}