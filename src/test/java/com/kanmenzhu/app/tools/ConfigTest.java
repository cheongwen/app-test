package com.kanmenzhu.app.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(classes = SpringConfig.class)
public class ConfigTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	Config config;
	
	
	@Test
	public void Configure() {
		System.out.println(config.get("auto.platform"));
	}
}