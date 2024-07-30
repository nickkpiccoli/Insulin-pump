package com.univr.pump.insulinpump.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VitalParametersDtoTest {

    @Test
    public void testConstructorAndGetters() {
        String timestamp = "2023-01-01T00:00:00";
        int bloodPressureSystolic = 120;
        int bloodPressureDiastolic = 80;
        int heartRate = 80;
        int bloodSugarLevel = 120;
        double temperature = 36.6;

        VitalParametersDto vitalParametersDto = new VitalParametersDto(
                timestamp, bloodPressureSystolic, bloodPressureDiastolic,
                heartRate, bloodSugarLevel, temperature);

        assertEquals(timestamp, vitalParametersDto.getTimestamp());
        assertEquals(bloodPressureSystolic, vitalParametersDto.getBloodPressureSystolic());
        assertEquals(bloodPressureDiastolic, vitalParametersDto.getBloodPressureDiastolic());
        assertEquals(heartRate, vitalParametersDto.getHeartRate());
        assertEquals(bloodSugarLevel, vitalParametersDto.getBloodSugarLevel());
        assertEquals(temperature, vitalParametersDto.getTemperature(), 0.001);
    }

    @Test
    public void testDefaultConstructor() {
        VitalParametersDto vitalParametersDto = new VitalParametersDto();

        assertEquals(0, vitalParametersDto.getBloodPressureSystolic());
        assertEquals(0, vitalParametersDto.getBloodPressureDiastolic());
        assertEquals(0, vitalParametersDto.getHeartRate());
        assertEquals(0, vitalParametersDto.getBloodSugarLevel());
        assertEquals(0.0, vitalParametersDto.getTemperature(), 0.001);
    }

    @Test
    public void testSetters() {
        String timestamp = "2023-01-01T00:00:00";
        int bloodPressureSystolic = 120;
        int bloodPressureDiastolic = 80;
        int heartRate = 80;
        int bloodSugarLevel = 120;
        double temperature = 36.6;

        VitalParametersDto vitalParametersDto = new VitalParametersDto();

        vitalParametersDto.setTimestamp(timestamp);
        vitalParametersDto.setBloodPressureSystolic(bloodPressureSystolic);
        vitalParametersDto.setBloodPressureDiastolic(bloodPressureDiastolic);
        vitalParametersDto.setHeartRate(heartRate);
        vitalParametersDto.setBloodSugarLevel(bloodSugarLevel);
        vitalParametersDto.setTemperature(temperature);

        assertEquals(timestamp, vitalParametersDto.getTimestamp());
        assertEquals(bloodPressureSystolic, vitalParametersDto.getBloodPressureSystolic());
        assertEquals(bloodPressureDiastolic, vitalParametersDto.getBloodPressureDiastolic());
        assertEquals(heartRate, vitalParametersDto.getHeartRate());
        assertEquals(bloodSugarLevel, vitalParametersDto.getBloodSugarLevel());
        assertEquals(temperature, vitalParametersDto.getTemperature(), 0.001); // Utilizza delta per double
    }
}
