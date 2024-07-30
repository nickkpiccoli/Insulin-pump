package com.univr.pump.insulinpump.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VitalParametersDto {
    private String timestamp;
    private int bloodPressureSystolic;
    private int bloodPressureDiastolic;
    private int heartRate;
    private int bloodSugarLevel;
    private double temperature;

    public VitalParametersDto() {
    }

    public VitalParametersDto(String timestamp,
                              int bloodPressureSystolic,
                              int bloodPressureDiastolic,
                              int heartRate,
                              int bloodSugarLevel,
                              double temperature) {
        this.timestamp = timestamp;
        this.bloodPressureSystolic = bloodPressureSystolic;
        this.bloodPressureDiastolic = bloodPressureDiastolic;
        this.heartRate = heartRate;
        this.bloodSugarLevel = bloodSugarLevel;
        this.temperature = temperature;
    }
}
