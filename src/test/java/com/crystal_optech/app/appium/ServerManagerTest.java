package com.crystal_optech.app.appium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.crystal_optech.app.tools.BaseTools;
import com.crystal_optech.app.tools.Config;
import com.crystal_optech.app.tools.DriverTools;
import com.crystal_optech.app.tools.SpringConfig;

import io.appium.java_client.android.AndroidDriver;

@ContextConfiguration(classes = SpringConfig.class)
public class ServerManagerTest extends AbstractTestNGSpringContextTests {

	@Test
	public void start() {
		ServerManager winServer = new WindowsManager();
		winServer.start();
		DriverTools divers = new DriverTools();
		AndroidDriver<WebElement> driver = (AndroidDriver<WebElement>) divers.androidDriver;
		try {
			BaseTools.wait(500);
			throw new Exception();
		} catch (Exception e) {
			driver.quit();
			driver.close();
			winServer.stop();
			e.printStackTrace();
		}
	}

	@Test
	public void stop() {
		throw new RuntimeException("Test not implemented");
	}
}
