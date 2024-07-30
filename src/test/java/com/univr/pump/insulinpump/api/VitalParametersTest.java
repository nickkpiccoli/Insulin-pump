package com.univr.pump.insulinpump.api;

import com.univr.pump.insulinpump.InsulinPumpApplication;
import com.univr.pump.insulinpump.controller.VitalParametersController;
import com.univr.pump.insulinpump.dto.DateIntervalDto;
import com.univr.pump.insulinpump.dto.VitalParametersDto;
import com.univr.pump.insulinpump.service.VitalParametersService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Optional.empty;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InsulinPumpApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VitalParametersTest {

    @LocalServerPort
    private int port;

    @MockBean
    private VitalParametersService vitalParametersService;

    @InjectMocks
    private VitalParametersController vitalParametersController;

    @Before
    public void setUp() {
        RestAssured.port = this.port;
    }

    /**
     * Test search by time interval Endpoint
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void searchByTimeIntervalValidTest() {
        DateIntervalDto dateInterval = new DateIntervalDto();
        dateInterval.setStartDate("2023-01-01T00:00:00");
        dateInterval.setEndDate("2023-01-02T00:00:00");
        List<VitalParametersDto> expectedResult = new ArrayList<>();
        when(vitalParametersService.getVitalParametersByTimeInterval(dateInterval))
                .thenReturn(expectedResult);
        given()
                .contentType(ContentType.JSON)
                .body(dateInterval)
                .when()
                .post("/vitalparameters/date")
                .then()
                .statusCode(200)
                .body(not(empty()));
    }

    /**
     * Test search by time interval Endpoint
     * (wrong date format)
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void searchByTimeIntervalInvalidTestWrongParams() {
        DateIntervalDto dateInterval = new DateIntervalDto();
        dateInterval.setStartDate("2023-01-02T00:00:00");
        dateInterval.setEndDate("2023-01-01T00:00:00");

        when(vitalParametersService.getVitalParametersByTimeInterval(dateInterval))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "From date is after To date"));

        given()
                .contentType(ContentType.JSON)
                .body(dateInterval)
                .when()
                .post("/vitalparameters/date")
                .then()
                .statusCode(400);
    }

    /**
     * Test search by wrong time interval Endpoint
     * (null params)
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void searchByTimeIntervalInvalidTestNullParams() {
        DateIntervalDto dateInterval = new DateIntervalDto();
        dateInterval.setStartDate(null);
        dateInterval.setEndDate("2023-01-01T00:00:00");

        when(vitalParametersService.getVitalParametersByTimeInterval(dateInterval))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format"));

        given()
                .contentType(ContentType.JSON)
                .body(dateInterval)
                .when()
                .post("/vitalparameters/date")
                .then()
                .statusCode(400);
    }

    /**
     * Test get all vital parameters Endpoint
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testGetVitalParameters() {
        VitalParametersDto vitalParametersDto = new VitalParametersDto();
        vitalParametersDto.setTimestamp("2020-01-01T00:00:00");
        vitalParametersDto.setBloodPressureSystolic(120);
        vitalParametersDto.setBloodPressureDiastolic(80);
        vitalParametersDto.setHeartRate(80);
        vitalParametersDto.setBloodSugarLevel(120);
        vitalParametersDto.setTemperature(36.6);

        when(vitalParametersService.getAllVitalParameters()).thenReturn(
                List.of(
                        vitalParametersDto
                )
        );

        given()
                .when()
                .get("/vitalparameters/")
                .then()
                .statusCode(200)
                .body("timestamp", Matchers.hasItem("2020-01-01T00:00:00"))
                .body("bloodPressureSystolic", Matchers.hasItem(120))
                .body("bloodPressureDiastolic", Matchers.hasItem(80))
                .body("heartRate", Matchers.hasItem(80))
                .body("bloodSugarLevel", Matchers.hasItem(120))
                .body("temperature", Matchers.hasItem(36.6f));
    }

    /**
     * Test get last vital parameters Endpoint
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getLastVitalParametersTest() {
        VitalParametersDto lastVitalParameter = new VitalParametersDto();
        lastVitalParameter.setTimestamp("2020-01-01T00:00:00");
        lastVitalParameter.setBloodPressureSystolic(120);
        lastVitalParameter.setBloodPressureDiastolic(80);
        lastVitalParameter.setHeartRate(80);
        lastVitalParameter.setBloodSugarLevel(120);
        lastVitalParameter.setTemperature(36.6);

        Mockito.when(vitalParametersService.getLastVitalParameters()).thenReturn(lastVitalParameter);

        given()
                .when()
                .get("/vitalparameters/last")
                .then()
                .statusCode(200)
                .body("timestamp", Matchers.equalTo("2020-01-01T00:00:00"))
                .body("bloodPressureSystolic", Matchers.equalTo(120))
                .body("bloodPressureDiastolic", Matchers.equalTo(80))
                .body("heartRate", Matchers.equalTo(80))
                .body("bloodSugarLevel", Matchers.equalTo(120))
                .body("temperature", Matchers.equalTo(36.6f));
    }

    /**
     * Test remove all vital parameters Endpoint
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteAllVitalParametersTest() {
        doNothing().when(vitalParametersService).deleteAllVitalParameters();
        given()
                .when()
                .delete("/vitalparameters/")
                .then()
                .statusCode(204);
    }
}
