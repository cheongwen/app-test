package com.kanmenzhu.app.appium;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kanmenzhu.app.tools.BaseTools;
import com.kanmenzhu.app.tools.Config;

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
	
	/**
	 * 停止Appium Server
	 */
	public void stop() {
		kill();
	}

	/**
	 * 启动Appium Server
	 */
	public void start() {
		run();
	}

	/**
	 * 重启Appium Server
	 */
	public void restart() {
		kill();
		run();
	}
	
	/**
	 * 运行Appium Server执行命令
	 */
	private void run() {
		LOG.info("[Appium]Appium Start");
		//获取配置文件config.properties中配置的启动appium的命令
		String command = Config.get("windows.appium.start","appium");
		if (isOSLinux()) {
			LOG.info("运行平台：Linux");
			command = Config.get("linux.appium.start","appium");
		}
		LOG.info("[CMD]"+command);
		//新启线程执行命令
		new Thread(new RunCommand(command)).start();
		try {
			Thread.sleep(1000);
			//循环判断appium是否启动成功，如果成功则退出循环
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
		//获取配置文件config.properties中配置的关闭appium的命令
		String command = Config.get("windows.appium.stop","appium");
		if (isOSLinux()) {
			LOG.info("运行平台：Linux");
			command = Config.get("linux.appium.stop","appium");
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
	
	public static boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            return true;
        } else {
            return false;
        }
    }
	
}
