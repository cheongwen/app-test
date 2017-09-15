package com.kanmenzhu.app.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.kanmenzhu.app.tools.BaseTools;

public class test1 extends BaseCaseTest {

	@Test
	public void test2() {
		try {
			bgonavi.goToNivaPage("玛利亚");
			for (int i = 0; i < 3; i++) {
				bgonavi.swipeLeftAddress();
			}
			bcase.get("GoNav-去导航").click();
			bcase.isExist(3, "Nav-目的地址");
			bgonavi.updateNvaiAddress("浙江大学");
			bgonavi.addWayAddress("城西银泰");
			BaseTools.wait(2000);
			bcase.get("Nav-开始导航").click();
			bcase.get("Nav-模拟导航").click();
			BaseTools.wait(2000);
			System.out.println("pppppppp::::"+driver.getPageSource());
		} catch (Exception e) {
			System.out.println("pppppppp::::"+driver.getPageSource());
		}
		
	}
	
	
}
