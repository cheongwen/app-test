package com.crystal_optech.app.testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.crystal_optech.app.appium.ServerManager;
import com.crystal_optech.app.appium.WindowsManager;
import com.crystal_optech.app.tools.BaseTools;
import com.crystal_optech.app.tools.Config;
import com.crystal_optech.app.tools.DriverTools;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * Hello world!
 *
 */
public class App extends BaseTools{
	
    public static void main( String[] args ) throws InterruptedException
    {
    	String udid = Config.get("auto.udid");
        int port = Integer.valueOf(Config.get("auto.port"));
    	ServerManager winServer = new WindowsManager();
        winServer.start();
        String appPackage = "com.crystal_optech.auditos";
        String appActivity = "com.crystal_optech.auditos.LogoActivity";
        try {
			
			AndroidDriver<AndroidElement> driver = (AndroidDriver<AndroidElement>)DriverTools.getDriver();
			try {
				wait(500);
				AndroidElement tiaoguo = driver.findElement(By.id("com.crystal_optech.auditos:id/image"));
				tiaoguo.click();
				WebElement tos = driver.findElementByName("TOS");
				System.out.println("============"+tos.getAttribute("text"));
				WebElement mine = driver.findElement(By.id("com.crystal_optech.auditos:id/top_head_container_menu"));
				mine.click();
				WebElement menu = driver.findElement(By.name("HUD设置"));
				driver.getAutomationName();
				
			} catch (Exception e) {
				e.printStackTrace();
				driver.quit();
				driver.close();
			}
		} catch (Exception e) {
			winServer.stop();
			e.printStackTrace();
		}
    }
    
   
}
