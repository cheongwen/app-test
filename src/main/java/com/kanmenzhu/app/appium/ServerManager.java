package com.kanmenzhu.app.appium;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kanmenzhu.app.tools.BaseTools;

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
	public volatile static boolean run = false;
	/**
	 * Appium Server 执行报错标识
	 */
	volatile static boolean error = false;
	
	public void stop() {
		kill();
	}

	public void start() {
		run();
	}

	public void restart() {
		kill();
		run();
	}
	
	private void run() {
		LOG.info("[Appium]Appium Start");
		Properties prop = BaseTools.getProperties();
		String command = prop.getProperty("appium.start");
		if (StringUtils.isBlank(command)) {
			//未获取到配置
			command = "appium";
		}
		LOG.info("[CMD]"+command);
		new Thread(new RunCommand(command)).start();
		try {
			Thread.sleep(1000);
			while (!run) {
				LOG.info("Wait the appium server start...");
				if (run || error) {
					break;
				}
				Thread.sleep(500);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (error) {
			LOG.info("[Appium]启动失败...");
		}else {
			LOG.info("[Appium]启动成功...");
		}
		
	}

	public void kill() {
		LOG.info("[Appium]kill Appium Server");
		Properties prop = BaseTools.getProperties();
		String command = prop.getProperty("appium.stop");
		if (StringUtils.isBlank(command)) {
			//未获取到配置
			command = "pkill -9 node";
		}
		LOG.info("[CMD]"+command);
//		new Thread(new RunCommand(command)).start();
		try {
			Runtime.getRuntime().exec(command);
			run = false;
		} catch (IOException e) {
			LOG.error("[Appium]关闭Appium Server出错",e);
		}
	}
	
}
