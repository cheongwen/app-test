package com.kanmenzhu.app.appium;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Appium Server 管理类
 * @author chang.lu
 *
 */

@Component
public class ServerManager {
	
	static final Logger LOG = LoggerFactory.getLogger(ServerManager.class);
	
	/**
	 * Appium Server 启动标识
	 */
	volatile static boolean run = false;
	/**
	 * Appium Server 执行报错标识
	 */
	volatile static boolean error = false;
	
	public void stop() {}
	
	public void start() {}
	
	public void restart() {}
	
}
