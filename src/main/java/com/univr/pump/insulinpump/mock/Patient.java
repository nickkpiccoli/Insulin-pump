package com.univr.pump.insulinpump.mock;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class Patient {
    private int pressureSystolic;
    private int pressureDiastolic;
    private int heartRate;
    private double temperature;
    private int glucoseLevel;
    private int previousGlucoseLevel;
    private int previousPreviousGlucoseLevel;

    private final static int INITIAL_PRESSURE_SYSTOLIC = 100;
    private final static int INITIAL_PRESSURE_DIASTOLIC = 70;
    private final static int INITIAL_HEART_RATE = 80;
    private final static double INITIAL_TEMPERATURE = 36.5;
    private final static int INITIAL_GLUCOSE_LEVEL = 90;

    public Patient() {
        this.pressureSystolic = INITIAL_PRESSURE_SYSTOLIC;
        this.pressureDiastolic = INITIAL_PRESSURE_DIASTOLIC;
        this.heartRate = INITIAL_HEART_RATE;
        this.temperature = INITIAL_TEMPERATURE;
        this.glucoseLevel = INITIAL_GLUCOSE_LEVEL;
        this.previousGlucoseLevel = INITIAL_GLUCOSE_LEVEL;
        this.previousPreviousGlucoseLevel = INITIAL_GLUCOSE_LEVEL;
    }

    /**
     * Simulates the variation of the patient's systolic blood pressure.
     * The systolic pressure is randomly increased or decreased by a value between 0 and 5.
     * If the systolic pressure is lower than 90 or higher than 140, it is set to 120.
     */
    public void modifySystolicBloodPressure() {
        java.util.Random random = new java.util.Random();
        int variation = random.nextInt(6);
        if (random.nextBoolean()) {
            this.pressureSystolic += variation;
        } else {
            this.pressureSystolic -= variation;
        }

        if (this.pressureSystolic < 90 || this.pressureSystolic > 140) {
            this.pressureSystolic = 120;
        }
    }

    /**
     * Simulates the variation of the patient's diastolic blood pressure.
     * The diastolic pressure is randomly increased or decreased by a value between 0 and 5.
     * If the diastolic pressure is lower than 60 or higher than 90, it is set to 80.
     */
    public void modifyDiastolicBloodPressure() {
        java.util.Random random = new java.util.Random();
        int variation = random.nextInt(6);
        if (random.nextBoolean()) {
            this.pressureDiastolic += variation;
        } else {
            this.pressureDiastolic -= variation;
        }

        if (this.pressureDiastolic < 60 || this.pressureDiastolic > 90) {
            this.pressureDiastolic = 80;
        }
    }

    /**
     * Simulates the variation of the patient's heart rate.
     * The heart rate is randomly increased or decreased by a value between 0 and 5.
     * If the heart rate is lower than 60 or higher than 100, it is set to 80.
     */
    public void modifyHeartRate() {
        java.util.Random random = new java.util.Random();
        int variation = random.nextInt(6);
        if (random.nextBoolean()) {
            this.heartRate += variation;
        } else {
            this.heartRate -= variation;
        }

        if (this.heartRate < 60 || this.heartRate > 100) {
            this.heartRate = 80;
        }
    }

    /**
     * Simulates the variation of the patient's temperature.
     * The temperature is randomly increased or decreased by a value between 0 and 0.1.
     * If the temperature is lower than 35 or higher than 39, it is set to 36.5.
     * The temperature is set to 36.5 with a probability of 1%.
     */
    public void modifyTemperature() {
        Random random = new Random();
        double variation = random.nextDouble() / 10;
        if (random.nextBoolean()) {
            this.temperature += variation;
        } else {
            this.temperature -= variation;
        }

        if (this.temperature < 35 || this.temperature > 39) {
            this.temperature = 36.5;
        }

        if (random.nextInt(100) == 0) {
            this.temperature = 36.5;
        }
    }


    /**
     * Simulates the variation of the patient's blood sugar level.
     * The blood sugar level is randomly increased by a value between 0 and 10.
     * If the blood sugar level is lower than 80, it is set to 100.
     * After modifying the glucose level, store the previous glucose levels.
     */
    public void modifyBloodSugarLevel() {
        if(this.glucoseLevel > 140)
            return;
        int random = (int) (Math.random() * 11);

        previousPreviousGlucoseLevel = previousGlucoseLevel;
        previousGlucoseLevel = glucoseLevel;

        if(Math.random() < 0.5)
            this.glucoseLevel += random;

        if (this.glucoseLevel < 80) {
            this.glucoseLevel = 100;
        }
    }
}
