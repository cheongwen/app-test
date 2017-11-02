package com.kanmenzhu.app.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanmenzhu.app.tools.BaseTools;
import com.kanmenzhu.app.tools.Config;
import com.kanmenzhu.app.tools.Driver;
import com.kanmenzhu.app.tools.Element;

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
	
	/**
	 * 超时时间，单位：秒
	 */
	public int timeOut = Integer.valueOf(Config.get("timeOut","2"));
	
	public BaseCase() {
		this.driver = Driver.getDriver();
	}
	
	public BaseCase(AppiumDriver<?> d) {
		this.driver = d;
	}
	
	/**
	 * 跳过欢迎页面
	 */
	public void skipHelloPage() {
		if (isExist(timeOut, "跳过")) {
			get("跳过").click();
		}
	}
	
	/**
	 * 滑动欢迎页面
	 */
	public void swipeHelloPage() {
		if (isExist(timeOut, "跳过")) {
			swipeToLeft(4);
		}
	}
	
	/**
	/**
	 * 返回主界面操作
	 */
	public void goToMain() {
		while (!isExist(1, "主页")) {
			get("返回").click();
		}
	}
		
}
