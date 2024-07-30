package com.univr.pump.insulinpump.controller;

import com.univr.pump.insulinpump.dto.DateIntervalDto;
import com.univr.pump.insulinpump.dto.VitalParametersDto;
import com.univr.pump.insulinpump.service.VitalParametersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/vitalparameters")
public class VitalParametersController {


    private final VitalParametersService vitalParametersService;

    public VitalParametersController(VitalParametersService vitalParametersService) {
        this.vitalParametersService = vitalParametersService;
    }

    /**
     * The system uses this API to get vital parameters of a patient in a given time interval
     * @param dateInterval
     * @return vital parameters of a patient in a given time interval
     */
    @PostMapping("/date")
    public Iterable<VitalParametersDto> searchByTimeInterval(@RequestBody DateIntervalDto dateInterval) {
        try {
            return vitalParametersService.getVitalParametersByTimeInterval(dateInterval);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }
    }

    /**
     * The system uses this API to get all vital parameters
     * @return all vital parameters
     */
    @GetMapping("/")
    public Iterable<VitalParametersDto> getVitalParameters() {
        return vitalParametersService.getAllVitalParameters();
    }

    /**
     * The system uses this API to get the last vital parameter
     * @return last vital parameter
     */
    @GetMapping("/last")
    public VitalParametersDto getLastVitalParameters() {
        return vitalParametersService.getLastVitalParameters();
    }

    /**
     * The system uses this API to remove all vital parameters
     * @return void
     */
    @DeleteMapping("/")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAllVitalParameters() {
        vitalParametersService.deleteAllVitalParameters();
    }

}
