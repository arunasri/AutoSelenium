package com.masteringselenium;

import static org.testng.Assert.assertEquals;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WashingtonGasTest {

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	  DesiredCapabilities caps = DesiredCapabilities.chrome();
	    caps.setCapability("platform", "Windows 7");
	    caps.setCapability("version", "45.0");
	 
	    driver = new RemoteWebDriver(new URL(URL), caps); baseUrl = "https://eservice.washgas.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  public static final String USERNAME = "waitforvisible";
  public static final String ACCESS_KEY =  System.getProperty("ACCESS_KEY");
  public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

  @Test
  public void testWashingtonGas() throws Exception {
    driver.get(baseUrl + "/Pages/Login.aspx");
    assertEquals(driver.findElement(By.cssSelector("label")).getText(), "Email Address");
    assertEquals(driver.findElement(By.xpath("//div[@id='ctl00_SPWebPartManager1_g_778d3a08_260c_4a12_808d_a051c9581a61']/div/div[2]/div[2]/label")).getText(), "Password");
    assertTrue(driver.findElement(By.id("ctl00_SPWebPartManager1_g_778d3a08_260c_4a12_808d_a051c9581a61_ctl00_hlForgotPassword")).getText().matches("^exact:Forgot Password[\\s\\S]$"));
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
