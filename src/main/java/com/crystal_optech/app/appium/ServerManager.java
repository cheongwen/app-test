package com.crystal_optech.app.appium;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.crystal_optech.app.testcase.BaseCase;

@Component
public class ServerManager {
	
	static final Logger LOG = LoggerFactory.getLogger(BaseCase.class);
	
	volatile static boolean run = false;
	
	public void stop() {
	}
	public void start() {
	}
	public void restart() {
	}
	
}
