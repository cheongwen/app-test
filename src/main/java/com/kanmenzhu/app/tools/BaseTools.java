package com.kanmenzhu.app.tools;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 测试工具
 * @author chang.lu
 *
 */

public class BaseTools {

	private static final Logger LOG = LoggerFactory.getLogger(BaseTools.class);
	
	public static void wait(int time) {
		if (time>0) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String dateName() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String name = dateFormat.format(new Date());
		return name;
	}
	
	/**
	 * 截图并保存
	 * @param picName
	 */
	public static void captureScreenShot(String picName) {
		File srcFile = Driver.getDriver().getScreenshotAs(OutputType.FILE);
        File location = new File("screenshots");
        File targetFile = new File(location.getAbsolutePath()+File.separator+picName+BaseTools.dateName()+".png");
        LOG.info("[截图]保存地址：" + targetFile.getPath());
        try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e1) {
        	LOG.info("[截图]搬迁图片失败");
            e1.printStackTrace();
        }
		
	}
}
