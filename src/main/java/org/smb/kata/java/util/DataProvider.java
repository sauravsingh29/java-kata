package org.smb.kata.java.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smb.kata.java.model.PowerConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Double.valueOf;

/**
 * Data loader from CSV and JSON files.
 */
@Component
public class DataProvider {

    private static final Logger log = LoggerFactory.getLogger(DataProvider.class.getName());

    private static final String COMMA_DELIMITER = ";";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<PowerConsumption> consumptionData = new ArrayList<>(0);

    private final ObjectMapper objectMapper;

    @Autowired
    public DataProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadConsumptionData() {
        ClassPathResource classPathResource = new ClassPathResource("data/electricity-consumption.csv");
        Scanner scanner = null;
        try {
            scanner = new Scanner(classPathResource.getInputStream());
            scanner.nextLine(); // skipping first line. we know we have data in file so avoid checking null conditions.
            final Set<PowerConsumption> electricData = new HashSet<>(0);
            while (scanner.hasNext()) {
                String nextLine = scanner.nextLine();
                if (null != nextLine) {
                    String[] dataArray = nextLine.replaceAll("\"", "").split(COMMA_DELIMITER);
                    if (null != dataArray && dataArray.length == 4) {
                        LocalDateTime timeStamp = LocalDateTime.parse(dataArray[0], DATE_TIME_FORMATTER);
                        PowerConsumption electricityConsumption = new PowerConsumption(timeStamp, dataArray[1],
                                valueOf(dataArray[2]), dataArray[3], "electricity");
                        electricData.add(electricityConsumption);
                    }
                }
            }
            consumptionData.addAll(electricData);
            log.info("Total data loaded {}", consumptionData.size());
        } catch (Exception e) {
            log.error("Failed to load Electricity Consumption from csv file.", e);
        } finally {
            if (null != scanner) {
                scanner.close();
            }
        }
        loadHeatData();
//        List<PowerConsumption> datas = new ArrayList<>(0);
//        Set<PowerConsumption> collect = datas.stream().sorted(Comparator.comparing(PowerConsumption::getTimeStamp)
//                .thenComparing(PowerConsumption::getMeterId)).collect(Collectors.toCollection(LinkedHashSet::new));
//        System.out.println("Data "+ collect);
//        double sum = consumptionData.stream()
//                .filter(cd -> cd.getMeterId().equals("aa6eb262-7195-4a26-81b7-9a524ce3d1de"))
//                .collect(Collectors.summingDouble(PowerConsumption::getConsumption));
//
//        System.out.println("Total consumption a6eb262-7195-4a26-81b7-9a524ce3d1de "+ sum);
//        Double collect1 = consumptionData.stream().filter(cd -> cd.getBuildingId().equals("aaa2e364-5216-11e8-bbbe-000d3a2b8cf3"))
//                .collect(Collectors.toList()).stream().collect(Collectors.summingDouble(PowerConsumption::getConsumption));
//        System.out.println("Total consumption of building aaa2e364-5216-11e8-bbbe-000d3a2b8cf3 "+ collect1);
    }

    private void loadHeatData() {
        ClassPathResource classPathResource = new ClassPathResource("data/heat-consumption.json");
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, PowerConsumption.class);
        try {
            List<PowerConsumption> powerConsumptions = objectMapper.readValue(classPathResource.getInputStream(), collectionType);
            consumptionData.addAll(powerConsumptions);
        } catch (Exception e) {
            log.error("Failed to load heat consumption data.", e);
        }
    }

    public List<PowerConsumption> getConsumptionData() {
        return consumptionData;
    }

}
