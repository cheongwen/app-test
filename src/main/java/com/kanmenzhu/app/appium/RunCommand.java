package com.kanmenzhu.app.appium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Appium Server 执行命令获取输出信息
 * @author chang.lu
 *
 */

public class RunCommand implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(RunCommand.class.getSimpleName());

	String cmd ;
	
	public RunCommand(String cmd) {
		this.cmd = cmd;
	}
	
	public void run() {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			printMessage(process.getInputStream());
			printMessage(process.getErrorStream());
		} catch (IOException e) {
			LOG.error("执行命令失败",e);
		} 
	}

	/**
	 * 输出appium日志信息
	 * @param input
	 */
	private static void printMessage(final InputStream input) {
		new Thread(new Runnable() {
	       	public void run() {
	       		Reader reader = new InputStreamReader(input);
	       		BufferedReader bf = new BufferedReader(reader);
	       		String line = null;
	       		try {
	       			while((line=bf.readLine())!=null) {
	       				LOG.info("[Appium]"+line);
	       				if (line.contains("started")) {
	       					ServerManager.run = true;
	       				}
	       				if (line.contains("Could not start REST")) {
							ServerManager.error = true;
							break;
						}
	       				if (line.contains("0 device(s) connected")) {
							ServerManager.error = true;
							break;
						}
	       			}
	       		} catch (IOException e) {
	       			LOG.error("[Appium]读取appium server日志失败",e);
	       		}
	       	}
		}).start();
	}
}
