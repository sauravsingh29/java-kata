package org.smb.kata.java.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.smb.kata.java.model.PowerConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class DataProviderTest {

    @Autowired
    private ObjectMapper objectMapper;

    private DataProvider dataProvider;

    @BeforeEach
    public void setup() {
        dataProvider = new DataProvider(objectMapper);
    }

    @Test
    void loadConsumptionData() {
        dataProvider.loadConsumptionData();
        List<PowerConsumption> consumptionData = dataProvider.getConsumptionData();
        Assert.noNullElements(consumptionData, "Power consumption data can't be empty.");
    }
}