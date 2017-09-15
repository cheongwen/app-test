package com.kanmenzhu.app.tools;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

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
		xpath, linkText, id, name, className, desc
	};

	public Element() {
		driver = Driver.getDriver();
		action = new TouchAction(driver);
	}

	/**
	 * 根据配置获取元素查找方式及元素定位属性
	 * 
	 * @param ename
	 * @return
	 */
	public MobileElement get(String ename) {
		MobileElement me = null;
		String list = ElementConfig.get(ename);
		if (list != null) {
			String[] lists = list.split("\\|\\|");
			if (lists.length == 2) {
				type = getByType(lists[0]);
				address = lists[1];
				me = find(whichBy());
				// me = find();
			} else {
				LOG.error("[key=" + ename + "]的配置不正确，请检查");
			}
		} else {
			LOG.info("根据元素配置表未获取到[key=" + ename + "]的元素,根据名称查找元素");
			me = find(By.name(ename));
		}

		return me;
	}

	/**
	 * 根据配置获取元素查找方式及元素定位属性
	 * 
	 * @param ename
	 * @return
	 */
	public List<MobileElement> getList(String ename) {
		List<MobileElement> elist = null;
		String list = ElementConfig.get(ename);
		if (list != null) {
			String[] lists = list.split("\\|\\|");
			if (lists.length == 2) {
				type = getByType(lists[0]);
				address = lists[1];
				elist = findList(whichBy());
				// me = find();
			} else {
				LOG.error("[key=" + ename + "]的配置不正确，请检查");
			}
		} else {
			LOG.error("没有获取到[key=" + ename + "]的元素");
		}

		return elist;
	}

	/**
	 * 查找元素列表，并返回
	 * 
	 * @return List<MobileElement>
	 */
	private List<MobileElement> findList(By whichBy) {
		List<MobileElement> list = (List<MobileElement>) driver.findElements(whichBy);
		return list;
	}

	/**
	 * 查找元素，并返回
	 * 
	 * @return MoblieElement
	 */
	private MobileElement find(By by) {
		MobileElement e = (MobileElement) driver.findElement(by);
		return e;
	}

	private By whichBy() {
		By by;
		switch (type) {
		case xpath:
			by = By.xpath(address);
			break;
		case id:
			by = By.id(address);
			break;
		case name:
			by = By.name(address);
			break;
		case className:
			by = By.className(address);
			break;
		case linkText:
			by = By.linkText(address);
			break;
		default:
			by = By.id(address);
		}
		return by;
	}

	/**
	 * 查找元素，并返回
	 * 
	 * @return
	 */
	@Deprecated
	private MobileElement find() {
		MobileElement e = null;
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
		case linkText:
			e = (MobileElement) driver.findElement(By.linkText(address));
			break;
		case desc:
			e = (MobileElement) driver.findElementByAccessibilityId(address);
			break;
		default:
			e = (MobileElement) driver.findElement(By.id(address));
		}
		return e;

	}

	/**
	 * 在配置时间内判断元素是否存在
	 * 
	 * @param timeOut
	 *            秒
	 * @param ename
	 * @return
	 */
	public boolean isExist(int timeOut, String ename) {
		boolean flag = false;
		By by = null;
		String list = ElementConfig.get(ename);
		if (list != null) {
			String[] lists = list.split("\\|\\|");
			if (lists.length == 2) {
				type = getByType(lists[0]);
				address = lists[1];
				by = whichBy();
				flag = waitForLoad(timeOut, by, ename);
			} else {
				LOG.error("[key=" + ename + "]的配置不正确，请检查");
			}
		} else {
			LOG.error("没有获取到[key=" + ename + "]的元素");
		}
		return flag;
	}

	private boolean waitForLoad(int timeOut, final By by, String name) {
		boolean flag = false;
		try {
			flag = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					MobileElement element = driver.findElement(by);
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			LOG.error("!!超时!! " + timeOut + " 秒之后还没找到元素 [" + name + "]");
		}
		return flag;
	}

	/**
	 * 判断当前页面中是否存在查找的字符
	 * 
	 * @param 需要查找的字符
	 * @return true/false
	 */
	public boolean isContainTxt(String txt) {
		List<MobileElement> list = (List<MobileElement>) driver.findElementsByClassName("android.widget.TextView");
		for (MobileElement me : list) {
			if (me.getText().contains(txt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 弹出窗口处理，直接根据名称定位获取元素，并点击
	 */
	public void alert(String name) {
		driver.findElement(By.name(name)).click();
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
	 * 通过坐标长按N
	 * @param x
	 * @param y
	 * @param time 毫秒
	 * 
	 */
	public void longclickByXY(int x, int y, int time) {
		try {
			new TouchAction(driver).longPress(x, y).waitAction(time).perform();
		} catch (Exception e) {
			LOG.error("长按选择位置失败");

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
	 * 操作拖动条
	 */
	public void dragSeekBar(String el) {
		MobileElement slider = get(el);
		// 获取拖动条的开始拖动点的x坐标
		int xAxisStartPoint = slider.getLocation().getX();
		// 获取拖动条的结束点的x坐标 = 开始x坐标+滑动条元素的宽度
		int xAxisEndPoint = xAxisStartPoint + slider.getSize().getWidth();
		// 滚动条的y坐标
		int yAxis = slider.getLocation().getY();
		TouchAction act = new TouchAction(driver);
		act.press(xAxisStartPoint, yAxis).waitAction(800).moveTo(xAxisEndPoint - 1, yAxis).release().perform();
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
