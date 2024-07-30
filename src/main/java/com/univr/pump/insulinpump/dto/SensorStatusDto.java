package com.univr.pump.insulinpump.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorStatusDto {
    private int battery;
    private int tank;

    public SensorStatusDto() {
    }

    public SensorStatusDto(int battery, int tank) {
        this.battery = battery;
        this.tank = tank;
    }
}
