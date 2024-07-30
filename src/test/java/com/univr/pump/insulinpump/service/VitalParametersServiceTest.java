package com.univr.pump.insulinpump.service;

import com.univr.pump.insulinpump.dto.DateIntervalDto;
import com.univr.pump.insulinpump.dto.VitalParametersDto;
import com.univr.pump.insulinpump.model.VitalParameters;
import com.univr.pump.insulinpump.repository.VitalParametersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VitalParametersServiceTest {

    @Mock
    private VitalParametersRepository vitalParametersRepository;

    @InjectMocks
    private VitalParametersService vitalParametersService;

    private VitalParameters vitalParameters;
    private List<VitalParameters> vitalParametersList;
    private DateIntervalDto dateIntervalDto;

    @Before
    public void setUp() {
        vitalParameters = new VitalParameters();
        vitalParameters.setId(1L);
        vitalParameters.setTimestamp(LocalDateTime.now());
        vitalParameters.setHeartRate(80);
        vitalParameters.setBloodPressureSystolic(120);
        vitalParameters.setBloodPressureDiastolic(80);
        vitalParameters.setBloodSugarLevel(90);
        vitalParameters.setTemperature(36.6);

        vitalParametersList = new ArrayList<>();

        dateIntervalDto = new DateIntervalDto();
    }

    @Test
    public void getAllVitalParametersEmptyList() {
        when(vitalParametersRepository.findAll()).thenReturn(vitalParametersList);
        Iterable<VitalParametersDto> result = vitalParametersService.getAllVitalParameters();
        assertNotNull(result);
        assertEquals(0, ((List<VitalParametersDto>) result).size());
        verify(vitalParametersRepository, times(1)).findAll();
    }

    @Test
    public void getAllVitalParametersNotEmpty() {
        vitalParametersList.add(vitalParameters);
        when(vitalParametersRepository.findAll()).thenReturn(vitalParametersList);
        Iterable<VitalParametersDto> result = vitalParametersService.getAllVitalParameters();
        assertNotNull(result);
        assertEquals(1, ((List<VitalParametersDto>) result).size());
        verify(vitalParametersRepository, times(1)).findAll();
    }

    @Test
    public void getVitalParametersByTimeIntervalValidInterval() {
        vitalParametersList.add(vitalParameters);
        dateIntervalDto.setStartDate("2020-01-01T00:00:00");
        dateIntervalDto.setEndDate("2020-01-01T00:00:00");
        when(vitalParametersRepository.findAllByTimestampBetween(
                LocalDateTime.parse(dateIntervalDto.getStartDate()),
                LocalDateTime.parse(dateIntervalDto.getEndDate())
        )).thenReturn(vitalParametersList);
        Iterable<VitalParametersDto> result = vitalParametersService.getVitalParametersByTimeInterval(dateIntervalDto);
        assertNotNull(result);
        verify(vitalParametersRepository, times(1))
                .findAllByTimestampBetween(
                        LocalDateTime.parse(dateIntervalDto.getStartDate()),
                        LocalDateTime.parse(dateIntervalDto.getEndDate())
                );
    }

    @Test(expected = Exception.class)
    public void getVitalParametersByTimeIntervalInvalidInterval() {
        dateIntervalDto.setStartDate("2020-01-01T00:00:00");
        dateIntervalDto.setEndDate("2019-01-01T00:00:00");
        try {
            vitalParametersService.getVitalParametersByTimeInterval(dateIntervalDto);
        } catch (Exception ex) {
            verify(vitalParametersRepository, times(0))
                    .findAllByTimestampBetween(
                            LocalDateTime.parse(dateIntervalDto.getStartDate()),
                            LocalDateTime.parse(dateIntervalDto.getEndDate())
                    );
            throw ex;
        }
    }

    @Test
    public void getLastVitalParameters() {
        when(vitalParametersRepository.findFirstByOrderByTimestampDesc()).thenReturn(vitalParameters);
        VitalParametersDto result = vitalParametersService.getLastVitalParameters();
        assertNotNull(result);
        verify(vitalParametersRepository, times(1)).findFirstByOrderByTimestampDesc();
    }

    @Test
    public void getLastVitalParametersWhenExists() {
        when(vitalParametersRepository.findFirstByOrderByTimestampDesc()).thenReturn(vitalParameters);
        VitalParametersDto result = vitalParametersService.getLastVitalParameters();
        assertNotNull(result);
        verify(vitalParametersRepository, times(1)).findFirstByOrderByTimestampDesc();
    }

    @Test(expected = ResponseStatusException.class)
    public void getLastVitalParameters_WhenNotExists() {
        when(vitalParametersRepository.findFirstByOrderByTimestampDesc()).thenReturn(null);
        try {
            vitalParametersService.getLastVitalParameters();
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
            throw ex;
        }
    }

    @Test
    public void deleteAllVitalParameters() {
        vitalParametersService.deleteAllVitalParameters();
        verify(vitalParametersRepository, times(1)).deleteAll();
    }

    @Test
    public void saveVitalParametersTest() {
        int pressureSystolic = 120;
        int pressureDiastolic = 80;
        int heartRate = 70;
        int bloodSugarLevel = 5;
        double temperature = 36.5;
        vitalParametersService.saveVitalParameters(pressureSystolic,
                pressureDiastolic,
                heartRate,
                bloodSugarLevel,
                temperature);
        verify(vitalParametersRepository, times(1)).save(any(VitalParameters.class));
    }

}
