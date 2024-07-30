package com.univr.pump.insulinpump.service;

import com.univr.pump.insulinpump.dto.DateIntervalDto;
import com.univr.pump.insulinpump.dto.VitalParametersDto;
import com.univr.pump.insulinpump.model.VitalParameters;
import com.univr.pump.insulinpump.repository.VitalParametersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VitalParametersService {

    private final VitalParametersRepository vitalParametersRepository;

    public VitalParametersService(VitalParametersRepository vitalParametersRepository) {
        this.vitalParametersRepository = vitalParametersRepository;
    }

    /**
     * Get all vital parameters
     * @return all vital parameters
     */
    public Iterable<VitalParametersDto> getAllVitalParameters() {
        Iterable<VitalParameters> result = vitalParametersRepository.findAll();
        return convertToDtoList(result);
    }

    /**
     * Find all vital parameters of a patient in a given time interval
     * @param dateIntervalDto
     * @return vital parameters of a patient in a given time interval
     */
    public Iterable<VitalParametersDto> getVitalParametersByTimeInterval(
            DateIntervalDto dateIntervalDto) {
        validateDateInterval(dateIntervalDto);
        Iterable<VitalParameters> result = vitalParametersRepository.findAllByTimestampBetween(
                LocalDateTime.parse(dateIntervalDto.getStartDate()),
                LocalDateTime.parse(dateIntervalDto.getEndDate()));
        return convertToDtoList(result);
    }

    /**
     * Get last vital parameters
     * @return last vital parameters
     */
    public VitalParametersDto getLastVitalParameters() {
        VitalParameters vitalParameter = vitalParametersRepository.findFirstByOrderByTimestampDesc();
        if (vitalParameter == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No vital parameters found");
        }
        return convertToDto(vitalParameter);
    }

    /**
     * Delete all vital parameters
     */
    public void deleteAllVitalParameters() {
        vitalParametersRepository.deleteAll();
    }


    /**
     * Create a vital parameterDto list from a vital parameter list
     * @param vitalParameter
     * @return vital parameterDto list
     */
    private VitalParametersDto convertToDto(VitalParameters vitalParameter) {
        VitalParametersDto dto = new VitalParametersDto();

        dto.setTimestamp(String.valueOf(vitalParameter.getTimestamp()));
        dto.setBloodPressureSystolic(vitalParameter.getBloodPressureSystolic());
        dto.setBloodPressureDiastolic(vitalParameter.getBloodPressureDiastolic());
        dto.setHeartRate(vitalParameter.getHeartRate());
        dto.setBloodSugarLevel(vitalParameter.getBloodSugarLevel());
        dto.setTemperature(vitalParameter.getTemperature());

        return dto;
    }


    /**
     * Create a vital parameter list from a vital parameterDto list
     * @param vitalParameters
     * @return vital parameter list
     */
    private Iterable<VitalParametersDto> convertToDtoList(Iterable<VitalParameters> vitalParameters) {
        List<VitalParametersDto> dtos = new ArrayList<>();
        for (VitalParameters vitalParameter : vitalParameters) {
            dtos.add(convertToDto(vitalParameter));
        }
        return dtos;
    }

    /**
     * Validate the date interval dto
     * @param dto
     */
    private void validateDateInterval(DateIntervalDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DTO cannot be null");
        }

        validateDateInterval(dto.getStartDate(), dto.getEndDate());
    }

    /**
     * Validate the date interval
     * @param fromDate
     * @param toDate
     */
    private void validateDateInterval(String fromDate, String toDate) {
        if (!isValidDate(fromDate) || !isValidDate(toDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }

        if (LocalDateTime.parse(fromDate).isAfter(LocalDateTime.parse(toDate))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From date is after To date");
        }
    }

    /**
     * Check if the date is valid
     * @param date
     * @return true if the date is valid, false otherwise
     */
    private boolean isValidDate(String date) {
        try {
            LocalDateTime.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Save the vital parameters
     * @param pressureSystolic
     * @param pressureDiastolic
     * @param heartRate
     * @param bloodSugarLevel
     * @param temperature
     */
    public void saveVitalParameters(int pressureSystolic, int pressureDiastolic, int heartRate, int bloodSugarLevel, Double temperature) {
        VitalParameters vitalParameters = new VitalParameters(
                LocalDateTime.now(),
                pressureSystolic,
                pressureDiastolic,
                heartRate,
                bloodSugarLevel,
                temperature
        );
        vitalParametersRepository.save(vitalParameters);
    }
}
