package com.univr.pump.insulinpump.system;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends PageObject {

    private WebDriverWait wait;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[9]/a[1]")
    private WebElement buttonStatistics;

    @FindBy(xpath = "//*[@id=\"buttonModalBattery\"]")
    private WebElement buttonBattery;

    @FindBy(xpath = "//*[@id=\"buttonConfirmBattery\"]")
    private WebElement buttonConfirmBattery;

    @FindBy(xpath = "//*[@id=\"buttonCancelBattery\"]")
    private WebElement buttonCancelBattery;

    @FindBy(xpath = "//*[@id=\"buttonModalInsulin\"]")
    private WebElement buttonInsulin;

    @FindBy(xpath = "//*[@id=\"buttonConfirmInsulin\"]")
    private WebElement buttonConfirmInsulin;

    @FindBy(xpath = "//*[@id=\"buttonCancelInsulin\"]")
    private WebElement buttonCancelInsulin;

    @FindBy(xpath = "//*[@id=\"batterylabel\"]")
    private WebElement batteryLevelLabel;

    @FindBy(xpath = "//*[@id=\"refilllabel\"]")
    private WebElement insulinLevelLabel;

    @FindBy(xpath = "/html/body/div/div[1]/div[1]")
    private WebElement homeTitle;

    public HomePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, 10);
    }

    public String getTitle(){
        return this.homeTitle.getText();
    }

    public StatisticsPage showStatistics() {
        this.buttonStatistics.click();
        return new StatisticsPage(driver);
    }

    public String replaceBattery() {
        this.buttonBattery.click();
        wait.until(ExpectedConditions.elementToBeClickable(this.buttonConfirmBattery));
        this.buttonConfirmBattery.click();
        wait.until(ExpectedConditions.textToBePresentInElement(this.batteryLevelLabel, "100"));
        return this.batteryLevelLabel.getText();
    }

    public void waitForCharts(){
        this.homeTitle.click();
    }

    public String replaceInsulin(){
        this.buttonInsulin.click();
        wait.until(ExpectedConditions.elementToBeClickable(this.buttonConfirmInsulin));
        this.buttonConfirmInsulin.click();
        wait.until(ExpectedConditions.textToBePresentInElement(this.insulinLevelLabel, "100"));
        return this.insulinLevelLabel.getText();
    }

}
