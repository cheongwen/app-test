package com.crystal_optech.app.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.crystal_optech.app.appium.ServerManager;
import com.crystal_optech.app.appium.WindowsManager;
import com.crystal_optech.app.tools.DriverTools;
import com.crystal_optech.app.tools.SpringConfig;
import com.crystal_optech.app.tools.TestngListener;

import io.appium.java_client.AppiumDriver;

@Listeners(TestngListener.class)
@ContextConfiguration(classes = SpringConfig.class)
public class BaseCaseTest extends AbstractTestNGSpringContextTests {

	private static final Logger LOG = LoggerFactory.getLogger(BaseCaseTest.class.getSimpleName());
	
	ServerManager server;
	AppiumDriver<?> driver;
	BaseCase bcase;

	@BeforeClass
	public void beforeClass() {
		LOG.info("测试begin ");
		driver = DriverTools.getDriver();
		bcase = new BaseCase(driver);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		//quit执行后，失去session，需要重建连接
		DriverTools.reset();
		LOG.info("测试end ");
	}
	
	/**
	 * 测试开始前，开启Appium服务
	 */
	@BeforeSuite
	public void beforeSuite() {
		LOG.info("测试Suite开始，开启appium server");
		server = new WindowsManager();
		server.start();
	}
	
	
	/**
	 * 测试结束后，关闭Appium服务
	 */
	@AfterSuite
	public void afterSuite() {
		driver.quit();
		LOG.info("测试Suite结束，关闭appium server");
		server.stop();
	}

}
