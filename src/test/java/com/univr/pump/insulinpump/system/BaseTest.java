package com.univr.pump.insulinpump.system;


import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class BaseTest {

    protected WebDriver driver;

    @Before
    public void setUp() {

        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);

        if(SystemUtils.IS_OS_WINDOWS){
            System.setProperty("webdriver.gecko.driver",
                    Paths.get("src/test/resources/geckodriver-win64/geckodriver.exe").toString());
        }
        else if (SystemUtils.IS_OS_MAC){
            System.setProperty("webdriver.gecko.driver",
                    Paths.get("src/test/resources/geckodriver-mac-arm/geckodriver").toString());
        }
        else if (SystemUtils.IS_OS_LINUX){
            //Set the path for the driver executable
        }
        if (driver == null) {
            driver = new FirefoxDriver(options);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
