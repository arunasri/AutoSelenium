package com.masteringselenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ForgotPasswordTest {
	private WebDriver driver;
	public static final String USERNAME = "waitforvisible";
	public static final String ACCESS_KEY = System.getProperty("ACCESS_KEY");
	public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

	@Test
	public void Forgot_Password() throws IOException {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("version", "45.0");

		driver = new RemoteWebDriver(new URL(URL), caps);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("http://stagingwaitforvisible.herokuapp.com/users/sign_in/");
		driver.findElement(By.partialLinkText("Forgot your")).click();
		Properties p = new Properties();
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/login.properties");
		p.load(file);
		WebElement ele = driver.findElement(By.id(p.getProperty("FP_Email")));

		ele.sendKeys("praneetha.palemall");
		String email = ele.getText();
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(email);
		System.out.println(matcher.matches());
		if (matcher.matches()) {
			driver.findElement(By.xpath(p.getProperty("SendME_Link"))).click();
			System.out.println("it clicked the url");
		}
		driver.quit();
	}
	// Forgot Password Link issue: staleElement Reference Exception and Illegal
	// Argument Exception were
	// found with "send me reset password instructions" Link.
	// Reason could be "data disable with" option is enabled, because of that we
	// couldn't find element location on webpage

}