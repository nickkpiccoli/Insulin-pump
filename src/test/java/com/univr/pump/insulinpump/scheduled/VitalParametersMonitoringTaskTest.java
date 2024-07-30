package com.univr.pump.insulinpump.scheduled;

import com.univr.pump.insulinpump.mock.Patient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class VitalParametersMonitoringTaskTest {

    @Mock
    private Patient patient;

    @InjectMocks
    private VitalParametersMonitoringTask vitalParametersMonitoringTask;

    @Test
    public void modifyVitalParametersTest() {
        vitalParametersMonitoringTask.modifyVitalParameters();

        verify(patient, times(1)).modifyBloodSugarLevel();
        verify(patient, times(1)).modifyDiastolicBloodPressure();
        verify(patient, times(1)).modifySystolicBloodPressure();
        verify(patient, times(1)).modifyHeartRate();
        verify(patient, times(1)).modifyTemperature();
    }
}
