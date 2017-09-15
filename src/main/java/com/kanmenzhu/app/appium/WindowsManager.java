package com.kanmenzhu.app.appium;

/**
 * Windows平台Appium管理类
 * @author chang.lu
 *
 */

public class WindowsManager extends ServerManager {

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
		LOG.info("[Windows Appium]run Appium Server");
		String command = "appium.cmd";
		new Thread(new RunCommand(command)).start();
		try {
			Thread.sleep(1000);
			while (!run) {
				if (run || error) {
					break;
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (error) {
			LOG.info("[Windows Appium]启动失败...");
		}else {
			LOG.info("[Windows Appium]启动成功...");
		}
		
	}

	public void kill() {
		String command = "taskkill /F /im node.exe";
		LOG.info("[Windows Appium]kill Appium Server");
		new Thread(new RunCommand(command)).start();
		run = false;
	}


}
