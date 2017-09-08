package com.crystal_optech.app.testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.javascript.host.canvas.ext.WEBGL_compressed_texture_s3tc;

public class BaseCase {
	
	WebDriver driver;

	private static final Logger LOG = LoggerFactory.getLogger(BaseCase.class);
	
	public BaseCase(WebDriver driver) {
		this.driver = driver;
	}
	
	public static void main(String[] args) {
		LOG.info("test log");
		LOG.debug("TEST LOG");
		LOG.error("TEST LOGSSSSSS");
	}
	
	private void skipHelloPage() {
		WebElement tiaoguo = driver.findElement(By.id("com.crystal_optech.auditos:id/image"));
		tiaoguo.click();
	}
	
	
}
