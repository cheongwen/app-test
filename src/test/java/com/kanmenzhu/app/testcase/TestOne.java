package com.kanmenzhu.app.testcase;

import org.testng.annotations.Test;

import com.kanmenzhu.app.appium.ServerManager;
import com.kanmenzhu.app.appium.WindowsManager;
import com.kanmenzhu.app.tools.Driver;
import com.kanmenzhu.app.tools.SpringConfig;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

@ContextConfiguration(classes = SpringConfig.class)
public class TestOne extends AbstractTestNGSpringContextTests {

	ServerManager server;
	AndroidDriver<WebElement> driver;

	@Test
	public void f() {
		WebElement tiaoguo = driver.findElement(By.id("com.crystal_optech.auditos:id/image"));
		tiaoguo.click();
		WebElement mine = driver.findElement(By.id("com.crystal_optech.auditos:id/top_head_container_menu"));
		mine.click();
	}
	
	@BeforeClass
	public void beforeClass() {
		driver = (AndroidDriver<WebElement>) Driver.getDriver();
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	@BeforeSuite
	public void beforeSuite() {
		server = new WindowsManager();
		server.start();
	}

	@AfterSuite
	public void afterSuite() {
		server.stop();
	}

}
