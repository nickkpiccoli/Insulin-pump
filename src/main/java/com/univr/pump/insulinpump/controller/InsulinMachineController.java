package com.univr.pump.insulinpump.controller;

import com.univr.pump.insulinpump.dto.SensorStatusDto;

import com.univr.pump.insulinpump.service.InsulinMachineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class InsulinMachineController {

    private final InsulinMachineService insulinMachineService;

    public InsulinMachineController(InsulinMachineService insulinMachineService) {
        this.insulinMachineService = insulinMachineService;
    }

    /**
     * The system uses this API to replace the battery
     */
    @PutMapping("/battery/replace")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public void chargeBattery() {
        insulinMachineService.chargeBattery();
    }

    /**
     * The system uses this API to refill the insulin pump
     */
    @PutMapping("/tank/refill")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public void refillInsulinTank() {
        insulinMachineService.refillInsulinPump();
    }

    /**
     * The system uses this API to get the status of the sensors
     * @return status of the sensors
     */
    @GetMapping("/status")
    public SensorStatusDto getStatus() {
        return new SensorStatusDto(
                insulinMachineService.getBatteryLevel(),
                insulinMachineService.getInsulinLevel());
    }
}
