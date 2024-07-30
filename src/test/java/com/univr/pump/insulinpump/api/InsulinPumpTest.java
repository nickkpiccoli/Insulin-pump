package com.univr.pump.insulinpump.api;

import com.univr.pump.insulinpump.InsulinPumpApplication;
import com.univr.pump.insulinpump.service.InsulinMachineService;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InsulinPumpApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InsulinPumpTest {

    @LocalServerPort
    private int port;

    @MockBean
    private InsulinMachineService insulinMachineService;

    @Before
    public void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void chargeBatteryTest() {
        given()
                .when()
                .put("/sensors/battery/replace")
                .then()
                .statusCode(200);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void refillInsulinTankTest() {
        given()
                .when()
                .put("/sensors/tank/refill")
                .then()
                .statusCode(200);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getStatusTest() {
        Mockito.when(insulinMachineService.getBatteryLevel()).thenReturn(100);
        Mockito.when(insulinMachineService.getInsulinLevel()).thenReturn(50);
        given()
                .when()
                .get("sensors/status")
                .then()
                .statusCode(200)
                .body("battery", org.hamcrest.Matchers.equalTo(100))
                .body("tank", org.hamcrest.Matchers.equalTo(50));
    }
}
