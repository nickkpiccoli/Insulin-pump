package com.univr.pump.insulinpump.system;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReplacementTest extends BaseTest{
    @Test
    public void testBatteryReplacement() {
        driver.get("http://localhost:8080/");
        HomePage home = new HomePage(driver);
        assertEquals("Battery should be at 100%", "Battery level: 100%", home.replaceBattery());
    }
    @Test
    public void testInsulinReplacement() {
        driver.get("http://localhost:8080/");
        HomePage home = new HomePage(driver);
        assertEquals("Insulin tank should be at 100 unit", "Insulin dose available: 100", home.replaceInsulin());
    }
}
