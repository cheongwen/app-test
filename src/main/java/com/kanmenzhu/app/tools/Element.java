package com.kanmenzhu.app.tools;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	
	/** 定位路径	 */
	String address;

	/** 定位方式	：xpath, linkText, id, name, className, desc */
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
			} else {
				LOG.error("[key=" + ename + "]的配置不正确，请检查");
			}
		} else {
			LOG.error("根据元素配置表未获取到[key=" + ename + "]的元素,根据名称查找元素");
			me = findByName(ename);
		}
		return me;
	}
	
	/**
	 * 通过xpath方式获取包含name的控件
	 * @param name
	 * @return 
	 */
	private MobileElement findByName(String name) {
		MobileElement e = find(By.xpath("//android.widget.TextView[contains(@text,'"+name+"')]"));
		return e;
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
			} else {
				LOG.error("[key=" + ename + "]的配置不正确，请检查");
			}
		} else {
			LOG.error("根据元素配置表未获取到[key=" + ename + "]的元素,根据名称查找元素");
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
	 * 在配置时间内判断元素是否存在
	 * 
	 * @param timeOut  单位：秒
	 * @param ename  element.properties文件中配置名称
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
			LOG.error("根据元素配置表未获取到[key=" + ename + "]的元素,根据名称查找元素");
			flag = waitForLoad(timeOut, By.xpath("//*[contains(@text,'"+ename+"')]"), ename);
		}
		if (flag) {
			LOG.info("Successful:界面存在元素["+ename+"]");
		}
		return flag;
	}

	private boolean waitForLoad(int timeOut, final By by, String name) {
		boolean flag = false;
		try {
			flag = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					MobileElement element = driver.findElement(by);
					return true;
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
		LOG.info("判断界面是否存在字符："+txt);
		MobileElement text = null;
		MobileElement button = null;
		try {
			text = (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+txt+"')]"));
		} catch (Exception e) {
			LOG.info("没有找到存在"+txt+"的TextView");
			try {
				button = (MobileElement) driver.findElement(By.xpath("//android.widget.Button[contains(@text,'"+txt+"')]"));
			} catch (Exception e1) {
				LOG.info("没有找到存在"+txt+"的button");
			}
		}
		
		if (text!=null||button!=null) {
			LOG.info("Successful:界面存在字符["+txt+"]");
			return true;
		}
		return false;
	}
	
	/**
	 * 弹出窗口处理，直接根据名称定位获取元素，并点击
	 */
	public void alert(String txt) {
		try {
			driver.findElement(By.name(txt)).click();
		} catch (Exception e2) {
			try {
				driver.findElement(By.xpath("//android.widget.Button[contains(@text,'"+txt+"')]")).click();
			} catch (Exception e) {
				LOG.info("没有找到存在"+txt+"的button");
				try {
					driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+txt+"')]")).click();
				} catch (Exception e1) {
					LOG.info("没有找到存在"+txt+"的TextView");
				}
			}
		}
	}

	/**
	 * 向左滑动n次
	 */
	public void swipeToLeft(int n) {
		int x = Driver.width;
		int y = Driver.height;
		for (int i = 0; i < n; i++) {
			try {
				driver.swipe((x / 8 * 7), (y / 2 * 1), (x / 8 * 1), (y / 2 * 1), 1000);
			} catch (Exception e) {
				LOG.error("第"+(i+1)+"次滑动失败",e);
			}
		}
	}

	/**
	 * 向右滑动n次
	 */
	public void swipeToRight(int n) {
		int x = Driver.width;
		int y = Driver.height;
		for (int i = 0; i < n; i++) {
			try {
				driver.swipe((x / 8 * 1), (y / 2 * 1), (x / 8 * 7), (y / 2 * 1), 1000);
			} catch (Exception e) {
				LOG.error("第"+(i+1)+"次滑动失败",e);
			}
		}
	}

	/**
	 * 向上滑动n次
	 */
	public void swipeToUp(int n) {
		int x = Driver.width;
		int y = Driver.height;
		for (int i = 0; i < n; i++) {
			try {
				driver.swipe((x / 2 * 1), (y / 4 * 3), (x / 2 * 1), (y / 4 * 1), 1000);
			} catch (Exception e) {
				LOG.error("第"+(i+1)+"次滑动失败",e);
			}
		}
	}

	/**
	 * 向下滑动n次
	 */
	public void swipeToDown(int n) {
		int x = Driver.width;
		int y = Driver.height;
		for (int i = 0; i < n; i++) {
			try {
				driver.swipe((x / 2 * 1), (y / 8 * 1), (x / 2 * 1), (y / 8 * 7), 1000);
			} catch (Exception e) {
				LOG.error("第"+(i+1)+"次滑动失败",e);
			}
		}
	}

	/**
	 * 通过坐标点击
	 */
	public void tapByXY(int x, int y) {
		try {
			action.tap(x, y).release().perform();
		} catch (Exception e) {
			LOG.error("坐标【"+x+","+y+"】点击失败");
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
			action.longPress(x, y).waitAction(time).perform();
		} catch (Exception e) {
			LOG.error("长按选择位置【"+x+","+y+"】失败");
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
	 * 以（x,y）为基准，计算得出（x,y-100）,(x,y+100)两个点，然后2个手指按住这两个点同时滑到（x,y）
	 */
	public void zoom() {
		int x = Driver.width;
		int y = Driver.height;
		int centerx = x/2;
		int centery = y/2;
		driver.zoom(centerx, centery);
	}

	/**
	 * 两个手指从（x,y）点开始向（x,y-100）和（x,y+100）滑动
	 */
	public void pinch() {
		int x = Driver.width;
		int y = Driver.height;
		int centerx = x/2;
		int centery = y/2;
		driver.pinch(centerx, centery);
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
