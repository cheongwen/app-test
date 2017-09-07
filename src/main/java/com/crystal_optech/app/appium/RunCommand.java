package com.crystal_optech.app.appium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crystal_optech.app.testcase.BaseCase;

/**
 * Appium Server，执行命令获取输出信息
 * @author chang.lu
 *
 */

public class RunCommand implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(RunCommand.class);

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
			LOG.error("执行命令失败");
			e.printStackTrace();
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
	       				System.out.println("[Windows Appium]"+line);
	       				if (line.contains("started")) {
	       					ServerManager.run = true;
	       				}
	       			}
	       		} catch (IOException e) {
	       			e.printStackTrace();
	       		}
	       	}
		}).start();
	}
}
