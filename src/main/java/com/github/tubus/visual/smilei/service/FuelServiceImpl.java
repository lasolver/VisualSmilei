package com.github.tubus.visual.smilei.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FuelServiceImpl {

    private final RestTemplate restTemplate = new RestTemplate();

    public FuelServiceImpl() {
    }

    public List<DataSeries> getFuel(Integer operatorId, Integer deviceId, LocalDateTime fromTime, LocalDateTime toTime) {
        String request = "http://localhost:7888/reports/compositions/fuel/operators/" + operatorId +
                "/devices/" + deviceId + "?fromTime=" + fromTime.toString() + ":00.000Z" +
                "&toTime=" + toTime.toString() + ":00.000Z";
        log.info(request);
        JsonNode response = restTemplate.getForObject(request, JsonNode.class);

        log.info(response.asText());

        JsonNode data = response.get("data");
        if (data == null) {
            return Collections.emptyList();
        }
        JsonNode tanksInfo = data.get(0).get("tanks");

        if (tanksInfo != null) {
            List<DataSeries> dataSeries = new ArrayList<>();
            for (JsonNode tank : tanksInfo) {
                for (JsonNode sensor : tank) {
                    System.out.println(sensor);
                    DataSeries ds = new DataSeries();
                    ds.setName("Fuel by sensor TODO");
                    for (JsonNode fuel : sensor) {
                        ds.add(new DataSeriesItem(LocalDateTime.parse(fuel.get("utc").asText().substring(0, 19)).plusHours(3).toInstant(ZoneOffset.UTC), fuel.get("smoothed").asDouble()));
                    }
                    dataSeries.add(ds);
                }
            }
            return dataSeries;
        }
        return Collections.emptyList();
    }
}
