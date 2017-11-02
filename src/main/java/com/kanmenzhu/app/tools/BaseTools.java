package com.kanmenzhu.app.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.MobileElement;

/**
 * 测试工具
 * @author chang.lu
 *
 */

public class BaseTools {

	private static final Logger LOG = LoggerFactory.getLogger(BaseTools.class.getSimpleName());

	private static String ImageDir = "screenshots";

	
	/**
	 * 等待
	 * @param time 单位：毫秒
	 */
	public static void wait(int time) {
		if (time > 0) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 当前时间，格式：mmss
	 * @return 分秒
	 */
	public static String nowTime() {
		DateFormat dateFormat = new SimpleDateFormat("mmss");
		String name = dateFormat.format(new Date());
		return name;
	}

	/**
	 * 当前日期，格式：yyyyMMddHH
	 * @return 年月日时
	 */
	public static String todayDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
		String name = dateFormat.format(new Date());
		return name;
	}

	/**
	 * 截图并保存
	 * 
	 * @param picName
	 *            图片名称
	 */
	public static void captureScreenShot(String picName) {
		File srcFile = Driver.getDriver().getScreenshotAs(OutputType.FILE);
		File targetFile = new File(getPicPath(picName));
		LOG.info("[截图]保存地址：" + targetFile.getPath());
		try {
			FileUtils.copyFile(srcFile, targetFile);
		} catch (IOException e) {
			LOG.info("[截图]搬迁图片失败", e);
		}
	}

	/**
	 * 根据控件元素进行截图
	 * @param me 控件元素
	 * @param picName
	 */
	public static void captureByElement(MobileElement me, String picName) {
		int x = me.getLocation().x;
		int y = me.getLocation().y;
		int w = me.getSize().width;
		int h = me.getSize().height;
		File srcFile = Driver.getDriver().getScreenshotAs(OutputType.FILE);
		File targetFile = new File(getPicPath(picName));
		LOG.info("[截图]保存地址：" + targetFile.getPath());
		try {
			FileUtils.copyFile(srcFile, targetFile);
			BufferedImage image = BMPLoader.getImageFromFile(targetFile);
			LOG.info("截图：起始坐标[" + x + "," + y + "],宽" + w + ",高" + h);
			BufferedImage subimage = BMPLoader.getSubImage(image, x, y, w, h);
			FileOutputStream output = new FileOutputStream(targetFile);
			ImageIO.write(subimage, "png", output);
			output.close();
		} catch (IOException e) {
			LOG.info("[截图]搬迁图片失败", e);
		}
	}

	/**
	 * 获取config文件的属性配置
	 * 
	 * @return
	 */
	public static Properties getProperties() {
		Properties prop = new Properties();
		try {
			// 读取属性文件
			InputStream in = Object.class.getResourceAsStream("/config.properties");
			prop.load(in); /// 加载属性列表
			// Iterator<String> it = prop.stringPropertyNames().iterator();
			// while (it.hasNext()) {
			// String key = it.next();
			// System.out.println(key + ":" + prop.getProperty(key));
			// }
			in.close();
			return prop;
		} catch (Exception e) {
			LOG.error("获取配置文件config.properties失败", e);
		}
		return prop;
	}

	/**
	 * 对比2张图片的相似度
	 * 
	 * @param standard
	 *            标准图片名称
	 * @param actual
	 *            实际截图名称
	 * @return 相似度 100为完全相同
	 */
	public static double comparePic(String standard, String actual) {
		LOG.info("开始图片全图对比");
		String path1 = getStandardPath(standard);
		String path2 = getPicPath(actual);
		LOG.info("图片1：" + path1);
		LOG.info("图片2：" + path2);
		// 标准图片
		BufferedImage image1 = BMPLoader.getImageFromFile(new File(path1));
		BufferedImage image2 = BMPLoader.getImageFromFile(new File(path2));
		return BMPLoader.compareImage(image1, image2);
	}

	/**
	 * 将图片截图进行比较
	 * 
	 * @param standard
	 *            标准图片路径
	 * @param actual
	 *            需要比较图片路径
	 * @param x
	 *            截图起始横坐标
	 * @param y
	 *            截图起始纵坐标
	 * @param width
	 *            截图宽度
	 * @param height
	 *            截图高度
	 * @return 图片相似度 100为完全相同
	 */
	public static double comparePartPic(String path1, String path2, int x, int y, int width, int height) {
		LOG.info("开始图片截图对比");
		LOG.info("图片1：" + path1);
		LOG.info("图片2：" + path2);
		// 标准图片
		BufferedImage image1 = BMPLoader.getImageFromFile(new File(path1));
		BufferedImage image2 = BMPLoader.getImageFromFile(new File(path2));
		// 截图比较
		LOG.info("截图：起始坐标[" + x + "," + y + "],宽" + width + ",高" + height);
		BufferedImage subimage1 = BMPLoader.getSubImage(image1, x, y, width, height);
		BufferedImage subimage2 = BMPLoader.getSubImage(image2, x, y, width, height);
		return BMPLoader.compareImage(subimage1, subimage2);
	}

	
	/**
	 * 拼装标准图片路径，标准图片存储目录为standard
	 * @param name
	 * @return
	 */
	public static String getStandardPath(String name) {
		return ImageDir + File.separator + "standard" + File.separator + Config.get("auto.platform") + "." + name
				+ ".png";
	}

	/**
	 * 拼装当前截图路径
	 * @param name
	 * @return
	 */
	public static String getPicPath(String name) {
		return ImageDir + File.separator + todayDate() + File.separator + Config.get("auto.platform") + "." + name
				+ ".png";
	}

}
