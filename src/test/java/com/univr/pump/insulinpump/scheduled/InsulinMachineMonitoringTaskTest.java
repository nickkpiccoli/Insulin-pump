package com.univr.pump.insulinpump.scheduled;

import com.univr.pump.insulinpump.mock.Patient;
import com.univr.pump.insulinpump.service.InsulinMachineService;
import com.univr.pump.insulinpump.service.VitalParametersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InsulinMachineMonitoringTaskTest {

    @Mock
    private InsulinMachineService insulinMachineService;
    @Mock
    private Patient patient;
    @Mock
    private VitalParametersService vitalParametersService;

    @InjectMocks
    private InsulinMachineMonitoringTask task;

    @Test
    public void decrBatteryWhenBatteryIsPositive() {
        when(insulinMachineService.getBatteryLevel()).thenReturn(10);

        task.decrBattery();

        verify(insulinMachineService, times(1)).decrBattery();
    }

    @Test
    public void decrBatteryWhenBatteryIsZero() {
        when(insulinMachineService.getBatteryLevel()).thenReturn(0);

        task.decrBattery();

        verify(insulinMachineService, times(0)).decrBattery();
    }

    @Test
    public void newVitalSignsWhenBatteryIsNotZero() {
        when(insulinMachineService.getBatteryLevel()).thenReturn(10);
        when(patient.getPressureSystolic()).thenReturn(120);
        when(patient.getPressureDiastolic()).thenReturn(80);
        when(patient.getHeartRate()).thenReturn(70);
        when(patient.getGlucoseLevel()).thenReturn(100);
        when(patient.getTemperature()).thenReturn(36.5);

        task.newVitalSigns();

        verify(vitalParametersService, times(1))
                .saveVitalParameters(120,
                        80,
                        70,
                        100,
                        36.5);
    }

    @Test
    public void newVitalSignsWhenBatteryIsZero() {
        when(insulinMachineService.getBatteryLevel()).thenReturn(0);

        task.newVitalSigns();

        verify(vitalParametersService, never())
                .saveVitalParameters(
                anyInt(),
                anyInt(),
                anyInt(),
                anyInt(),
                anyDouble());
    }

    @Test
    public void insulinPumpWithBatteryAndNeedsInjection() {
        when(patient.getGlucoseLevel()).thenReturn(120);
        when(patient.getPreviousGlucoseLevel()).thenReturn(110);
        when(patient.getPreviousPreviousGlucoseLevel()).thenReturn(100);
        when(insulinMachineService.getBatteryLevel()).thenReturn(10);
        when(insulinMachineService.injectInsulin(anyInt())).thenReturn(true);

        task.insulinPump();

        verify(insulinMachineService, times(1)).injectInsulin(anyInt());
        verify(patient, times(1)).setGlucoseLevel(anyInt());
    }

    @Test
    public void insulinPumpWithLowBattery() {
        when(insulinMachineService.getBatteryLevel()).thenReturn(0);

        task.insulinPump();

        verify(insulinMachineService, never()).injectInsulin(anyInt());
        verify(patient, never()).setGlucoseLevel(anyInt());
    }

    @Test
    public void insulinPumpNoInjectionNeeded() {
        when(patient.getGlucoseLevel()).thenReturn(110);
        when(patient.getPreviousGlucoseLevel()).thenReturn(108);
        when(patient.getPreviousPreviousGlucoseLevel()).thenReturn(106);
        when(insulinMachineService.getBatteryLevel()).thenReturn(10);

        task.insulinPump();

        verify(insulinMachineService, times(1)).injectInsulin(anyInt());
        verify(patient, never()).setGlucoseLevel(anyInt());
    }

    @Test
    public void insulinPumpInjectionFails() {
        when(patient.getGlucoseLevel()).thenReturn(120);
        when(patient.getPreviousGlucoseLevel()).thenReturn(110);
        when(patient.getPreviousPreviousGlucoseLevel()).thenReturn(100);
        when(insulinMachineService.getBatteryLevel()).thenReturn(10);
        when(insulinMachineService.injectInsulin(anyInt())).thenReturn(false);

        task.insulinPump();

        verify(insulinMachineService, times(1)).injectInsulin(anyInt());
        verify(patient, never()).setGlucoseLevel(anyInt());
    }

    @Test
    public void insulinPumpGlucoseLevelBelowThreshold() {
        when(patient.getGlucoseLevel()).thenReturn(125);
        when(patient.getPreviousGlucoseLevel()).thenReturn(120);
        when(patient.getPreviousPreviousGlucoseLevel()).thenReturn(115);
        when(insulinMachineService.getBatteryLevel()).thenReturn(10);
        when(insulinMachineService.injectInsulin(anyInt())).thenReturn(true);

        task.insulinPump();

        verify(insulinMachineService, times(1)).injectInsulin(anyInt());
        verify(patient, times(1)).setGlucoseLevel(anyInt());
    }

    @Test
    public void insulinPumpGlucoseLevelAboveThreshold() {
        when(patient.getGlucoseLevel()).thenReturn(130);
        when(patient.getPreviousGlucoseLevel()).thenReturn(120);
        when(patient.getPreviousPreviousGlucoseLevel()).thenReturn(115);
        when(insulinMachineService.getBatteryLevel()).thenReturn(10);
        when(insulinMachineService.injectInsulin(anyInt())).thenReturn(true);

        task.insulinPump();

        verify(insulinMachineService, times(1)).injectInsulin(anyInt());
        verify(patient, times(1)).setGlucoseLevel(anyInt());
    }
}
