package com.kanmenzhu.app.tools;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * 根据配置文件获取系统配置属性
 * @author chang.lu
 *
 */

public class Config {
	
	public final static String ANDROID = "Android";
	public final static String IOS = "IOS";
	private static Properties prop;

	static {
		try {
			prop = new Properties();
			// 读取属性文件
			InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties");
			ClassLoader classLoader = Config.class.getClassLoader();
			URL resource = classLoader.getResource("config.properties");
			System.out.println(resource.getPath());
			//InputStreamReader read = new InputStreamReader(in, "UTF-8");
			prop.load(in); /// 加载属性列表
			in.close();
			//read.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * 根据配置项名称获取配置参数，如果获取不到，则返回默认值
	 * @param name 配置项名称
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String get(String name,String defaultValue) {
		return prop.getProperty(name, defaultValue);
	}
	
	/**
	 * 根据配置项名称获取配置参数，如果获取不到，则返回null
	 * @param name
	 * @return
	 */
	public static String get(String name) {
		return prop.getProperty(name);
	}
	
}
