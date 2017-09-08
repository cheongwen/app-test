package com.crystal_optech.app.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crystal_optech.app.tools.BaseTools;
import com.crystal_optech.app.tools.DriverTools;
import com.crystal_optech.app.tools.Element;

import io.appium.java_client.AppiumDriver;

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
		BaseTools.wait(500);
		get("跳过").click();
	}
	
	/**
	 * 打开设置主页面
	 */
	public void OpenSettingPage(){
		skipHelloPage();
		get("settingPage").click();
	}
	
	
}
