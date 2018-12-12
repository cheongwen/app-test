package com.kanmenzhu.app.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

public class ConfigTest {
	
	
	@Test
	public void Configure() {
		System.out.println(Config.get("auto.platform"));
	}
}