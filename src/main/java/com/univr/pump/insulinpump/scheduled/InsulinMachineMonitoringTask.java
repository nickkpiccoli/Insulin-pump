package com.univr.pump.insulinpump.scheduled;

import com.univr.pump.insulinpump.mock.Patient;
import com.univr.pump.insulinpump.service.InsulinMachineService;
import com.univr.pump.insulinpump.service.VitalParametersService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InsulinMachineMonitoringTask {
    private final Patient patient;
    private final InsulinMachineService insulinMachineService;
    private final VitalParametersService vitalParametersService;

    public InsulinMachineMonitoringTask(Patient patient,
                                        InsulinMachineService insulinMachineService,
                                        VitalParametersService vitalParametersService) {
        this.patient = patient;
        this.insulinMachineService = insulinMachineService;
        this.vitalParametersService = vitalParametersService;
    }

    /**
     * Simulates the battery discharge.
     * The battery is discharged by 1% every 10 minutes.
     */
    @Scheduled(fixedRate = 600000)
    public void decrBattery() {
        if (insulinMachineService.getBatteryLevel() > 0) {
            insulinMachineService.decrBattery();
        }
    }

    /**
     * Simulates the insulin pump monitoring.
     * Checks if the sugar level is increasing
     * if so, it calculates the compensation dose
     * and injects it.
     * If the battery is low, the injection is not performed.
     */
    @Scheduled(fixedRate = 10000)
    public void insulinPump() {
        int r2 = patient.getGlucoseLevel();
        int r1 = patient.getPreviousGlucoseLevel();
        int r0 = patient.getPreviousPreviousGlucoseLevel();

        if (insulinMachineService.getBatteryLevel() == 0) {
            return;
        }

        int rateIncrease = r2 - r1;
        int compDose = calculateCompDose(r2, r1, r0, rateIncrease);


        if (compDose > 0 && rateIncrease > 0  && patient.getGlucoseLevel() >= 90 && patient.getGlucoseLevel() < 130) {
            boolean injected = insulinMachineService.injectInsulin(compDose);
            if (injected) {
                patient.setGlucoseLevel(patient.getGlucoseLevel() - (rateIncrease * 3));
            }
        }
        if (patient.getGlucoseLevel() >= 130) {
            boolean injected = insulinMachineService.injectInsulin(5);
            if (injected) {
                patient.setGlucoseLevel(90);
            }
        }
    }

    /**
     * Calculates the compensation dose.
     * If the rate of increase is greater than the previous one,
     * the compensation dose is calculated.
     * Otherwise, the compensation dose is set to 0.
     *
     * @param r2           current glucose level
     * @param r1           previous glucose level
     * @param r0           previous previous glucose level
     * @param rateIncrease rate of increase
     * @return compensation dose
     */
    private int calculateCompDose(int r2, int r1, int r0, int rateIncrease) {
        if (rateIncrease >= (r1 - r0)) {
            int compDose = (int) Math.round((r2 - r1) / 4.0);
            return Math.max(compDose, 1);
        }
        return 0;
    }

    /**
     * Ogni 10 minuti effettua una misurazione dei parametri vitali del paziente.
     * Se la batteria è scarica la misurazione
     * non viene effettuata.
     */
    @Scheduled(fixedRate = 10000)
    public void newVitalSigns() {
        if(insulinMachineService.getBatteryLevel() == 0) {
            return;
        }

        vitalParametersService.saveVitalParameters(
                patient.getPressureSystolic(),
                patient.getPressureDiastolic(),
                patient.getHeartRate(),
                patient.getGlucoseLevel(),
                patient.getTemperature()
        );
    }

}
