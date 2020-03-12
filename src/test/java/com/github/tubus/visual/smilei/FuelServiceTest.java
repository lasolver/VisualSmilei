package com.github.tubus.visual.smilei;

import com.github.tubus.visual.smilei.service.FuelServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class FuelServiceTest {

    private LocalDateTime fromTime = LocalDateTime.of(2020, 3, 1, 10, 0, 0);
    private LocalDateTime toTime = LocalDateTime.of(2020, 3, 2, 15, 0, 0);


    @Test
    public void fuelServiceSampleTest() {
        FuelServiceImpl fuelService = new FuelServiceImpl();
        fuelService.getFuel(1001, 669, fromTime, toTime);
    }
}
