package com.masteringselenium;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutoSpecLoginTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  public static final String USERNAME = "waitforvisible";
  public static final String ACCESS_KEY =  System.getProperty("ACCESS_KEY");
  public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
 
  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    DesiredCapabilities caps = DesiredCapabilities.chrome();
    caps.setCapability("platform", "Windows XP");
    caps.setCapability("version", "43.0");
 
    driver = new RemoteWebDriver(new URL(URL), caps);
 
    baseUrl = "http://autospecs.pype.io/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/AutoSpecs/login");
  }

  @Test
  public void testAutoSpecLogin() throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, 15);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.form-group.pypeFormGroup > #email")));
    driver.findElement(By.cssSelector("div.form-group.pypeFormGroup > #email")).clear();
    driver.findElement(By.cssSelector("div.form-group.pypeFormGroup > #email")).sendKeys("subbarao.pasupuleti@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("welcome12");
    driver.findElement(By.xpath("(//button[@type='submit'])[7]")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.ellipsisSpan.ng-binding")));
    driver.findElement(By.cssSelector("span.ellipsisSpan.ng-binding")).click();
    driver.findElement(By.linkText("Logout")).click();
  }

  @Test
   public void testInvaid() throws Exception {
     WebDriverWait wait = new WebDriverWait(driver, 15);
     wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.form-group.pypeFormGroup > #email")));
     driver.findElement(By.cssSelector("div.form-group.pypeFormGroup > #email")).clear();
     driver.findElement(By.cssSelector("div.form-group.pypeFormGroup > #email")).sendKeys("subbarao.pasupuleti@gmail.com");
     driver.findElement(By.id("password")).clear();
     driver.findElement(By.id("password")).sendKeys("adfajdfk");
     driver.findElement(By.xpath("(//button[@type='submit'])[7]")).click();
     assertTrue(driver.findElement(By.cssSelector(".alert.flashStyle ")).getText().startsWith("Email or password is incorrect"));
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
