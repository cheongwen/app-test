package com.crystal_optech.app.tools;

import java.util.Set;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

/**
 * 读取配置文件获取元素查找方式及元素定位属性
 * 
 * @author chang.lu
 *
 */
public class Element {

	private static final Logger LOG = LoggerFactory.getLogger(Element.class);

	private TouchAction action;

	AppiumDriver<?> driver;
	
	String address;
	ByType type;
	
	public enum ByType { 
		by, xpath, linkText, id, name, className
	};

	public Element() {
		driver = DriverTools.getDriver();
		action = new TouchAction(driver);
	}
	
	/**
	 * 根据配置获取元素查找方式及元素定位属性
	 * @param ename
	 * @return
	 */
	public MobileElement get(String ename) {
		MobileElement me = null;
		String list = ElementConfig.get(ename);
		if (list != null) {
			String[] lists = list.split("\\|\\|");
			if (lists.length == 2) {
				type= getByType(lists[0]);
				address = lists[1];
				me = find();
			} else {
				LOG.error("[key="+ename+"]的配置不正确，请检查");
			}
		}else {
			LOG.error("没有获取到[key="+ename+"]的元素");
		}
		
		return me;
	}
	
	/**
	 * 查找元素，并返回
	 * @return
	 */
	public MobileElement find() {
		MobileElement e=null;
		switch (type) {
		case xpath:
			e = (MobileElement) driver.findElement(By.xpath(address));	
			break;
		case id:
			e = (MobileElement) driver.findElement(By.id(address));
			break;
		case name:
			e = (MobileElement) driver.findElement(By.name(address));
			break;
		case className:
			e = (MobileElement) driver.findElement(By.className(address));
			break;
		default:
			e = (MobileElement) driver.findElement(By.id(address));
		}
		return e;

	}


	/**
	 * 向左滑动
	 */
	public void swipeToLeft() {
		
		int x = driver.manage().window().getSize().width;
		int y = driver.manage().window().getSize().height;
		try {
			driver.swipe((x / 8 * 7), (y / 2 * 1), (x / 8 * 1), (y / 2 * 1), 1000);
		} catch (Exception e) {
			driver.swipe((x / 8 * 7), (y / 2 * 1), (x / 8 * 1), (y / 2 * 1), 1000);
		}
	}

	/**
	 * 向右滑动
	 */
	public void swipeToRight() {
		int x = driver.manage().window().getSize().width;
		int y = driver.manage().window().getSize().height;
		try {
			driver.swipe((x / 8 * 1), (y / 2 * 1), (x / 8 * 7), (y / 2 * 1), 1000);
		} catch (Exception e) {
			driver.swipe((x / 8 * 1), (y / 2 * 1), (x / 8 * 7), (y / 2 * 1), 1000);
		}
	}

	/**
	 * 向上滑动
	 */
	public void swipeToUp() {
		int x = driver.manage().window().getSize().width;
		int y = driver.manage().window().getSize().height;
		try {
			driver.swipe((x / 2 * 1), (y / 4 * 3), (x / 2 * 1), (y / 4 * 1), 1500);
		} catch (Exception e) {
			driver.swipe((x / 2 * 1), (y / 4 * 3), (x / 2 * 1), (y / 4 * 1), 1500);
		}
	}

	/**
	 * 向下滑动
	 */
	public void swipeToDown() {
		int x = driver.manage().window().getSize().width;
		int y = driver.manage().window().getSize().height;
		try {
			driver.swipe((x / 2 * 1), (y / 8 * 1), (x / 2 * 1), (y / 8 * 7), 1000);
		} catch (Exception e) {
			driver.swipe((x / 2 * 1), (y / 8 * 1), (x / 2 * 1), (y / 8 * 7), 1000);
		}
	}

	/**
	 * 通过坐标点击
	 */
	public void tapByXY(int x, int y) {
		try {
			action.tap(x, y).release().perform();
		} catch (Exception e) {
			LOG.error("坐标点击失败");
		}
	}

	/**
	 * 拖动元素
	 * 
	 */
	public void dragElement(String e1, String e2) {
		action.press(get(e1)).moveTo(get(e2)).release().perform();
	}

	/**
	 * 判断元素是否出现
	 * 
	 */
	public boolean isElementDisplayed(String el) {
		boolean flag = false;
		try {
			get(el).isDisplayed();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 切换Webview页面查找元素
	 */
	public void switchtoWebview() {
		try {
			Set<String> contextNames = driver.getContextHandles();
			for (String contextName : contextNames) {
				LOG.info(contextName);
				if (contextName.toLowerCase().contains("webview")) {
					driver.context(contextName);
					LOG.info("跳转到web页 开始操作web页面");
					break;
				}
			}
		} catch (Exception e) {
			LOG.error("切换web页面失败");
			e.printStackTrace();
		}
	}

	
	public static ByType getByType(String type) {
		ByType byType = ByType.xpath;
		if (type == null || type.equalsIgnoreCase("xpath")) {
			byType = ByType.xpath;
		} else if (type.equalsIgnoreCase("id")) {
			byType = ByType.id;
		} else if (type.equalsIgnoreCase("name")) {
			byType = ByType.name;
		} else if (type.equalsIgnoreCase("className")) {
			byType = ByType.className;
		}
		return byType;
	}
}
