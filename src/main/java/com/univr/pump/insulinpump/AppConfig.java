package com.univr.pump.insulinpump;

import com.univr.pump.insulinpump.mock.Patient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Patient patient() {
        return new Patient();
    }

}
