package com.univr.pump.insulinpump.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class VitalParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime timestamp;
    private Integer bloodPressureSystolic;
    private Integer bloodPressureDiastolic;
    private Integer heartRate;
    private Integer bloodSugarLevel;
    private Double temperature;

    public VitalParameters() {}

    public VitalParameters(LocalDateTime timestamp
            , Integer bloodPressureSystolic
            , Integer bloodPressureDiastolic
            , Integer heartRate
            , Integer bloodSugarLevel
            , Double temperature) {
        this.timestamp = timestamp;
        this.bloodPressureDiastolic = bloodPressureDiastolic;
        this.bloodPressureSystolic = bloodPressureSystolic;
        this.heartRate = heartRate;
        this.bloodSugarLevel = bloodSugarLevel;
        this.temperature = temperature;
    }
}