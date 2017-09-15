package com.kanmenzhu.app.appium;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.kanmenzhu.app.tools.BaseTools;
import com.kanmenzhu.app.tools.Driver;
import com.kanmenzhu.app.tools.SpringConfig;

import io.appium.java_client.AppiumDriver;

@ContextConfiguration(classes = SpringConfig.class)
public class ServerManagerTest extends AbstractTestNGSpringContextTests {

	@Test
	public void start() {
		ServerManager winServer = new WindowsManager();
		winServer.start();
		AppiumDriver<?> driver = Driver.getDriver();
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
