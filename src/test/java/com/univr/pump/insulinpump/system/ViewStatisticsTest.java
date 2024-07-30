package com.univr.pump.insulinpump.system;
import static org.junit.Assert.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class ViewStatisticsTest extends BaseTest{

    @Test
    public void testChartUpdatesWithDateInput() {
        driver.get("http://localhost:8080/");
        HomePage page = new HomePage(driver);
        page.waitForCharts();
        StatisticsPage stats = page.showStatistics();
        assertEquals("Should be in statistics page", "Charts by Date Intervals", stats.getTitle());

        int[] initialDataCount = stats.getDataPoints();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println(currentDate);
        int[] updatedDataCount = stats.InsertDate(currentDate, currentDate);

        assertNotEquals("data points should be different", Arrays.toString(initialDataCount), Arrays.toString(updatedDataCount));

        int[] clearedChart = stats.clearCharts();

        assertArrayEquals("data points should be zero", new int[]{0, 0, 0, 0}, clearedChart);

        page = stats.backHome();
        assertEquals("Should be in home page", "Last measurements", page.getTitle());
    }

    @Test
    public void testChartUpdatesAll(){
        driver.get("http://localhost:8080/");
        HomePage page = new HomePage(driver);
        page.waitForCharts();
        StatisticsPage stats = page.showStatistics();
        assertEquals("Should be in statistics page", "Charts by Date Intervals", stats.getTitle());

        int[] initialDataCount = stats.getDataPoints();

        int[] updatedDataCount = stats.getChartsAll();

        assertNotEquals("data points should be different", Arrays.toString(initialDataCount), Arrays.toString(updatedDataCount));

        int[] clearedChart = stats.clearCharts();

        assertArrayEquals("data points should be zero", new int[]{0,0,0,0}, clearedChart);

        page = stats.backHome();
        assertEquals("Should be in home page", "Last measurements", page.getTitle());
    }
    @Test
    public void testChartUpdatesWithWrongDateInput() {
        driver.get("http://localhost:8080/");
        HomePage page = new HomePage(driver);
        page.waitForCharts();
        StatisticsPage stats = page.showStatistics();
        assertEquals("Should be in statistics page", "Charts by Date Intervals", stats.getTitle());

        int[] initialDataCount = stats.getDataPoints();

        int[] updatedDataCount = stats.InsertDateWithAlert("18-23-2023", "");

        assertEquals("data points should be the same", Arrays.toString(initialDataCount), Arrays.toString(updatedDataCount));

        int[] clearedChart = stats.clearCharts();

        assertArrayEquals("data points should be zero", new int[]{0, 0, 0, 0}, clearedChart);

        page = stats.backHome();
        assertEquals("Should be in home page", "Last measurements", page.getTitle());
    }

}