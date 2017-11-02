package com.kanmenzhu.app.testcase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

import com.kanmenzhu.app.tools.SuiteListener;
import com.kanmenzhu.app.tools.Config;
import com.kanmenzhu.app.tools.Driver;
import com.kanmenzhu.app.tools.SpringConfig;
import com.kanmenzhu.app.tools.TestListener;

import io.appium.java_client.AppiumDriver;

/**
 * BaseCaseTest作为测试用例类的父类，提供用例执行前后的处理工作
 * 每一个测试类需要继承此类
 * @author chang.lu
 *
 */

@Listeners({ TestListener.class, SuiteListener.class })
@ContextConfiguration(classes = SpringConfig.class)
public class BaseCaseTest extends AbstractTestNGSpringContextTests {

	private static final Logger LOG = LoggerFactory.getLogger(BaseCaseTest.class.getSimpleName());
	
	/**
	 * 超时时间，单位：秒
	 */
	public int timeOut;

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
		Driver.autoDriver = "Appium";
		//获取toast，使用uiautomator2
		//Driver.autoDriver = "uiautomator2";
		driver = Driver.getDriver();
		bcase = new BaseCase(driver);
		timeOut = Integer.valueOf(Config.get("timeOut","2"));
		//跳过欢迎界面
		bcase.skipHelloPage();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		//quit执行后，失去session，需要重建连接
		Driver.reset();
		LOG.info("测试end");
	}
	
	


}
