package com.crystal_optech.app.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crystal_optech.app.tools.BaseTools;
import com.crystal_optech.app.tools.DriverTools;
import com.crystal_optech.app.tools.Element;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

/**
 * 测试基础类，提供封装方法
 * 建议每个测试用例都继承该类
 * @author chang.lu
 *
 */

public class BaseCase extends Element {
	
	AppiumDriver<?> driver ;

	private static final Logger LOG = LoggerFactory.getLogger(BaseCase.class);
	
	public BaseCase() {
		this.driver = DriverTools.getDriver();
	}
	
	public BaseCase(AppiumDriver<?> d) {
		this.driver = d;
	}
	
	/**
	 * 跳过欢迎页面
	 */
	public void skipHelloPage() {
		BaseTools.wait(3000);
		get("跳过").click();
	}
	
	/**
	 * 滑动欢迎页面
	 */
	public void swipeHelloPage() {
		BaseTools.wait(3000);
		for (int i = 0; i < 4; i++) {
			BaseTools.wait(1000);
			swipeToLeft();
		}
	}
	
	/**
	 * 打开设置主页面
	 */
	public void OpenSettingPage(){
		skipHelloPage();
		get("Main-HUD设置").click();
	}
		
}
