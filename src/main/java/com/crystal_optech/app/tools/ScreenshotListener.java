package com.crystal_optech.app.tools;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.crystal_optech.app.testcase.BaseCase;

import io.appium.java_client.android.AndroidDriver;

public class ScreenshotListener implements ITestListener {

	private static final Logger LOG = LoggerFactory.getLogger(BaseCase.class);

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailure(ITestResult result) {
		LOG.info("[截图]用例执行失败");
		String dest = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
		LOG.info("[截图]"+dest);
		BaseTools.captureScreenShot(dest);
	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

}
