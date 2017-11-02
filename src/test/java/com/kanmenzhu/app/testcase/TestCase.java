package com.kanmenzhu.app.testcase;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class TestCase extends BaseCaseTest {

	@Test
	public void test() {
		bcase.swipeToDown(3);
		bcase.swipeToUp(2);
		assertTrue(bcase.isContainTxt("Hello"),"判断是否存在Hello的字符");
	}
	
}
