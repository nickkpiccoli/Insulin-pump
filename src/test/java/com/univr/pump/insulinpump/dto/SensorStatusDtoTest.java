package com.univr.pump.insulinpump.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SensorStatusDtoTest {

    @Test
    public void testConstructorAndGetters() {
        int batteryLevel = 80;
        int tankLevel = 50;

        SensorStatusDto sensorStatusDto = new SensorStatusDto(batteryLevel, tankLevel);

        assertEquals(batteryLevel, sensorStatusDto.getBattery());
        assertEquals(tankLevel, sensorStatusDto.getTank());
    }

    @Test
    public void testDefaultConstructor() {
        SensorStatusDto sensorStatusDto = new SensorStatusDto();

        assertEquals(0, sensorStatusDto.getBattery());
        assertEquals(0, sensorStatusDto.getTank());
    }

    @Test
    public void testSetters() {
        int batteryLevel = 80;
        int tankLevel = 50;

        SensorStatusDto sensorStatusDto = new SensorStatusDto();

        sensorStatusDto.setBattery(batteryLevel);
        sensorStatusDto.setTank(tankLevel);

        assertEquals(batteryLevel, sensorStatusDto.getBattery());
        assertEquals(tankLevel, sensorStatusDto.getTank());
    }
}
