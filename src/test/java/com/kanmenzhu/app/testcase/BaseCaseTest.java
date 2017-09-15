package com.kanmenzhu.app.testcase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.kanmenzhu.app.appium.ServerManager;
import com.kanmenzhu.app.appium.WindowsManager;
import com.kanmenzhu.app.tools.Config;
import com.kanmenzhu.app.tools.Driver;
import com.kanmenzhu.app.tools.SpringConfig;
import com.kanmenzhu.app.tools.TestngListener;

import io.appium.java_client.AppiumDriver;

/**
 * BaseCaseTest作为测试用例类的父类，提供用例执行前后的处理工作
 * 每一个测试类需要继承此类
 * @author chang.lu
 *
 */

@Listeners(TestngListener.class)
@ContextConfiguration(classes = SpringConfig.class)
public class BaseCaseTest extends AbstractTestNGSpringContextTests {

	private static final Logger LOG = LoggerFactory.getLogger(BaseCaseTest.class.getSimpleName());
	
	/**
	 * 超时时间，单位：秒
	 */
	public int timeOut ;
	
	ServerManager server;
	AppiumDriver<?> driver;
	BaseCase bcase;

	@AfterMethod
	public void afterMethod(){
		//该方法用于每个用例执行完成后，返回到主界面
		bcase.goToMain();
	}
	
	@BeforeClass
	public void beforeClass() {
		LOG.info("测试begin");
		driver = Driver.getDriver();
		bcase = new BaseCase(driver);
		timeOut = Integer.valueOf(Config.get("timeOut","2"));
		bcase.skipHelloPage();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		//quit执行后，失去session，需要重建连接
		Driver.reset();
		LOG.info("测试end");
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
