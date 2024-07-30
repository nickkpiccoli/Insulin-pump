package com.univr.pump.insulinpump.dto;

import lombok.Data;

@Data
public class DateIntervalDto {
    private String startDate;
    private String endDate;

    public DateIntervalDto() {
    }

    public DateIntervalDto(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
