package com.crystal_optech.app.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

import com.crystal_optech.app.appium.ServerManager;


@ComponentScan
public class SpringConfig {
	
	@Autowired
	Environment evn ;
	
	@Bean
	public Config config() {
		return new Config(evn);
	}
	
}
