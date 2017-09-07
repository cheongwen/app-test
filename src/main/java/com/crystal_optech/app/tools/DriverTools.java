package com.crystal_optech.app.tools;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.crystal_optech.app.appium.ServerManager;
import com.crystal_optech.app.appium.WindowsManager;
import com.crystal_optech.app.tools.Config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * 创建基于Android和iOS的driver
 * 
 * @author chang.lu
 *
 */

public class DriverTools {

	String ANDROID = "Android";
	String IOS = "IOS";

	public IOSDriver<?> iosDriver;
	public AndroidDriver<?> androidDriver;
	
	public DriverTools() {
		if (ANDROID.equals(Config.get("auto.platform"))) {
			createAndroidDriver(Config.get("auto.udid"),Config.get("auto.port"),Config.get("auto.appPackage"),Config.get("auto.appActivity"));
		} else if (IOS.equals(Config.get("auto.platform"))) {
			createIOSDriver();
		} else {
			createAndroidDriver(Config.get("auto.udid"),Config.get("auto.port"),Config.get("auto.appPackage"),Config.get("auto.appActivity"));
		}

	}

	
	/**
	 * 未完成，不可使用
	 * @return
	 */
	private IOSDriver<?> createIOSDriver() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// 使用的自动化测试引擎：Appium(默认)或Selendroid
		capabilities.setCapability("automationName", "Appium");
		// 测试的手机操作系统：iOS,Android,FirefoxOS
		capabilities.setCapability("platformName", "Android");
		// 手机操作系统版本
		capabilities.setCapability("platformVersion", "4.1.1");
		// 使用的手机类型或模拟器类型:iPhone Simulator,iPad Simulator,iPhone Retina 4-inch,android
		// Emulator,Galaxy S4等
		// 在 iOS上，这个关键字的值必须是使用 `instruments -s devices` 得到的可使用的设备名称之一。在
		// Android上，这个关键字目前不起作用
		capabilities.setCapability("deviceName", "android Emulator");
		// 连接的物理设备的唯一设备标识
		capabilities.setCapability("udid", "94122ad8");
		iosDriver = new IOSDriver<WebElement>(capabilities);
		return iosDriver;
	}

	private AndroidDriver<?> createAndroidDriver(String udid, String port, String appPackage, String appActivity) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// 使用的自动化测试引擎：Appium(默认)或Selendroid
		capabilities.setCapability("automationName", "Appium");
		// 测试的手机操作系统：iOS,Android,FirefoxOS
		capabilities.setCapability("platformName", "Android");
		// driver的session超时时间，默认是60秒
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "600");
		// 手机操作系统版本
		capabilities.setCapability("platformVersion", "8.0.0");
		// 使用的手机类型或模拟器类型:iPhone Simulator,iPad Simulator,iPhone Retina 4-inch,android
		// Emulator,Galaxy S4等
		// 在 iOS上，这个关键字的值必须是使用 `instruments -s devices` 得到的可使用的设备名称之一。
		// 在 Android上，这个关键字目前不起作用
		capabilities.setCapability("deviceName", "android Emulator");
		// 连接的物理设备的唯一设备标识
		// 通过adb devices命令查看模拟器的udid
		capabilities.setCapability("udid", udid);
		// 使用的是WINDOWS平台
		capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
		// 测试的web浏览器，如果是测app则忽略
		// capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		// 要启动的app包
		capabilities.setCapability("appPackage", appPackage);
		// 要启动的Activity
		capabilities.setCapability("appActivity", appActivity);
		// 支持中文输入
		capabilities.setCapability("unicodeKeyboard", "True");
		// 重置输入法为系统默认
		capabilities.setCapability("resetKeyboard", "True"); // 重置输入法为系统默认

		// 安装时不对apk进行重签名，设置很有必要，否则有的apk在重签名后无法正常使用
		// caps.setCapability("noSign", "True");
		try {
			androidDriver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return androidDriver;
	}

}
