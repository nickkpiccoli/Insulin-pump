package com.univr.pump.insulinpump.system;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StatisticsPage extends PageObject{

    @FindBy(xpath = "/html/body/div/div/div[1]\n")
    private WebElement statisticsTitle;

    @FindBy(xpath = "//*[@id=\"start\"]")
    private WebElement startDate;

    @FindBy(xpath = "//*[@id=\"end\"]")
    private WebElement endDate;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[3]/button")
    private WebElement showCharts;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[4]/button")
    private WebElement clearCharts;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[5]/a")
    private WebElement backHome;

    @FindBy(css = "#chart1 .apexcharts-series path")
    private List<WebElement> initialDataPointsFirst;

    @FindBy(id = "chart1")
    private WebElement firstChart;

    @FindBy(css = "#chart2 .apexcharts-series path")
    private List<WebElement> initialDataPointsSecond;

    @FindBy(id = "chart2")
    private WebElement secondChart;

    @FindBy(css = "#chart3 .apexcharts-series path")
    private List<WebElement> initialDataPointsThird;

    @FindBy(id = "chart3")
    private WebElement thirdChart;


    @FindBy(css = "#chart4 .apexcharts-series path")
    private List<WebElement> initialDataPointsFourth;

    @FindBy(id = "chart4")
    private WebElement fourthChart;


    public StatisticsPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle(){
        return this.statisticsTitle.getText();
    }

    public HomePage backHome(){
        this.backHome.click();
        return new HomePage(driver);
    }

    public int[] getDataPoints(){
        int[] points = {initialDataPointsFirst.size(), initialDataPointsSecond.size(), initialDataPointsThird.size(), initialDataPointsFourth.size()};
        return points;
    }

    public int[] InsertDate(String start, String end){
        System.out.println(start);
        this.startDate.sendKeys(start);
        this.endDate.sendKeys(end);
        this.showCharts.click();
        this.statisticsTitle.click();
        return getDataPoints();
    }

    public int[] InsertDateWithAlert(String start, String end){
        System.out.println(start);
        this.startDate.sendKeys(start);
        this.endDate.sendKeys(end);
        this.showCharts.click();
        clickAlert();
        this.statisticsTitle.click();
        return getDataPoints();
    }

    public int[] getChartsAll(){
        this.showCharts.click();
        this.statisticsTitle.click();
        return getDataPoints();
    }

    public int[] clearCharts() {
        this.clearCharts.click();
        return getDataPoints();
    }

    public void clickAlert(){
        driver.switchTo().alert().accept();
    }
}
