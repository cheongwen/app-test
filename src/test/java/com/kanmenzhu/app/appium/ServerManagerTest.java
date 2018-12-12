package com.kanmenzhu.app.appium;

import org.testng.annotations.Test;

import com.kanmenzhu.app.tools.BaseTools;
import com.kanmenzhu.app.tools.Driver;

import io.appium.java_client.AppiumDriver;


public class ServerManagerTest {

	@Test
	public void start() {
		ServerManager winServer = new ServerManager();
		winServer.start();
		AppiumDriver<?> driver = Driver.getDriver();
		try {
			BaseTools.wait(500);
			throw new Exception();
		} catch (Exception e) {
			driver.quit();
			winServer.stop();
			e.printStackTrace();
		}
	}

	@Test
	public void stop() {
		throw new RuntimeException("Test not implemented");
	}
}
