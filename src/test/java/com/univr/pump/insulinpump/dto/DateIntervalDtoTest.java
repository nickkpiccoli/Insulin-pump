package com.univr.pump.insulinpump.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DateIntervalDtoTest {

    @Test
    public void testConstructorAndGetters() {
        String startDate = "2023-01-01T00:00:00";
        String endDate = "2023-01-02T00:00:00";

        DateIntervalDto dateIntervalDto = new DateIntervalDto(startDate, endDate);

        assertEquals(startDate, dateIntervalDto.getStartDate());
        assertEquals(endDate, dateIntervalDto.getEndDate());
    }

    @Test
    public void testDefaultConstructor() {
        DateIntervalDto dateIntervalDto = new DateIntervalDto();

        assertNull(dateIntervalDto.getStartDate());
        assertNull(dateIntervalDto.getEndDate());
    }

    @Test
    public void testSetters() {
        String startDate = "2023-01-01T00:00:00";
        String endDate = "2023-01-02T00:00:00";

        DateIntervalDto dateIntervalDto = new DateIntervalDto();

        dateIntervalDto.setStartDate(startDate);
        dateIntervalDto.setEndDate(endDate);

        assertEquals(startDate, dateIntervalDto.getStartDate());
        assertEquals(endDate, dateIntervalDto.getEndDate());
    }
}
