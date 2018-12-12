package com.kanmenzhu.app.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 根据配置文件获取元素属性
 * @author chang.lu
 *
 */

public class ElementConfig {

	private static Properties prop;

	static {
		try {
			prop = new Properties();
			// 读取属性文件
			InputStream in = Object.class.getResourceAsStream("/element.properties");
			prop.load(in); /// 加载属性列表
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 根据配置项名称获取配置参数，如果获取不到，则返回null
	 * 
	 * @param name
	 * @return
	 */
	public static String get(String name) {
		return prop.getProperty(name);
	}
	
}
