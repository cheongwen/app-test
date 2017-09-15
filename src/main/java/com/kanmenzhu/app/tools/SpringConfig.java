package com.kanmenzhu.app.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * Spring 配置类
 * @author chang.lu
 *
 */

@ComponentScan
public class SpringConfig {
	
	@Autowired
	Environment evn ;
	
	@Bean
	public Config config() {
		return new Config(evn);
	}
	
	@Bean
	public ElementConfig elementConfig() {
		return new ElementConfig(evn);
	}
}
