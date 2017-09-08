package com.crystal_optech.app.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.crystal_optech.app.appium.ServerManager;
import com.crystal_optech.app.appium.WindowsManager;
import com.crystal_optech.app.testcase.BaseCase;

/**
 * TestNG 监听类扩展
 * @author Administrator
 *
 */
public class TestngListener implements ITestListener {

	private static final Logger LOG = LoggerFactory.getLogger(TestngListener.class);
	
	
	public void onFinish(ITestContext arg0) {
		
	}

	public void onStart(ITestContext arg0) {
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 执行失败截图
	 */
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
