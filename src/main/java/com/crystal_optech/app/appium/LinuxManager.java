package com.crystal_optech.app.appium;

import java.io.IOException;

public class LinuxManager extends ServerManager {
	
	String udid;
	int port;

	public LinuxManager(String udid, int port) {
		this.udid = udid;
		this.port = port;
	}
	
	public void stop() {
		kill();
	}

	public void start() {
		run(udid, port);
	}

	public void restart() {
		kill();
		run(udid, port);
	}
	
	private void run(String udid, int port) {
		LOG.info("run " + udid + " Appium Server in port " + port + "...");
        //TODO
	}
	
	public void kill(){
		LOG.info("kill Appium Server task ...");
        //TODO
    }
	
	/**
	 * 调用环境执行命令
	 * @param command
	 */
	private void runCommand(String command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
