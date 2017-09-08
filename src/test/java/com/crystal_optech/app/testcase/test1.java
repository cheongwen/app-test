package com.crystal_optech.app.testcase;

import org.testng.annotations.Test;

import com.crystal_optech.app.tools.BaseTools;

public class test1 extends BaseCaseTest {

	@Test
	public void test2() {
		BaseTools.wait(1000);
		bcase.swipeToLeft();
		BaseTools.wait(1000);
		bcase.swipeToLeft();
		BaseTools.wait(1000);
		bcase.swipeToLeft();
		BaseTools.wait(1000);
		bcase.swipeToLeft();
		BaseTools.wait(1000);
	}
	
	
}
