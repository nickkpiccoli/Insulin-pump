package com.univr.pump.insulinpump.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VitalParametersTest {

    @Test
    public void testCreateVitalParameters() {
        VitalParameters vitalParameters = new VitalParameters();

        assertNotNull(vitalParameters);
    }

    @Test
    public void testSettersAndGetters() {
        LocalDateTime timestamp = LocalDateTime.now();
        int bloodPressureSystolic = 120;
        int bloodPressureDiastolic = 80;
        int heartRate = 80;
        int bloodSugarLevel = 120;
        double temperature = 36.6;

        VitalParameters vitalParameters = new VitalParameters();

        vitalParameters.setTimestamp(timestamp);
        vitalParameters.setBloodPressureSystolic(bloodPressureSystolic);
        vitalParameters.setBloodPressureDiastolic(bloodPressureDiastolic);
        vitalParameters.setHeartRate(heartRate);
        vitalParameters.setBloodSugarLevel(bloodSugarLevel);
        vitalParameters.setTemperature(temperature);

        assertEquals(timestamp, vitalParameters.getTimestamp());
        assertEquals(bloodPressureSystolic, vitalParameters.getBloodPressureSystolic().intValue());
        assertEquals(bloodPressureDiastolic, vitalParameters.getBloodPressureDiastolic().intValue());
        assertEquals(heartRate, vitalParameters.getHeartRate().intValue());
        assertEquals(bloodSugarLevel, vitalParameters.getBloodSugarLevel().intValue());
        assertEquals(temperature, vitalParameters.getTemperature(), 0.001);
    }
}
