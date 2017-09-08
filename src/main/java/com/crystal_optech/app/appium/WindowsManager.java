package com.crystal_optech.app.appium;

import com.crystal_optech.app.tools.BaseTools;

/**
 * Windows平台Appium管理类
 * @author chang.lu
 *
 */

public class WindowsManager extends ServerManager {

	public WindowsManager() {

	}

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
				if (run) {
					LOG.info("[Windows Appium]开启");
					break;
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOG.info("[Windows Appium]开始执行case");
	}

	public void kill() {
		String command = "taskkill /F /im node.exe";
		LOG.info("[Windows Appium]kill Appium Server");
		new Thread(new RunCommand(command)).start();
		run = false;
	}


}
