package com.univr.pump.insulinpump.scheduled;

import com.univr.pump.insulinpump.mock.Patient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VitalParametersMonitoringTask {

    private final Patient patient;

    public VitalParametersMonitoringTask(Patient patient) {
        this.patient = patient;
    }

    /**
     * Simulate the modification of vital parameters
     * (blood sugar level, diastolic blood pressure,
     * systolic blood pressure, heart rate, temperature).
     */
    @Scheduled(fixedRate = 5000)
    public void modifyVitalParameters() {
        patient.modifyBloodSugarLevel();
        patient.modifyDiastolicBloodPressure();
        patient.modifySystolicBloodPressure();
        patient.modifyHeartRate();
        patient.modifyTemperature();
    }
}
