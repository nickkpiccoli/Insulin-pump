package com.univr.pump.insulinpump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class InsulinPumpApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsulinPumpApplication.class, args);
    }

}
