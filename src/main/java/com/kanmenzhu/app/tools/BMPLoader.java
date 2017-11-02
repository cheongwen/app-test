package com.kanmenzhu.app.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片对比，借鉴他人
 *
 */
public class BMPLoader {

	private static final Logger LOG = LoggerFactory.getLogger(BMPLoader.class.getSimpleName());
	
	public static double compareImage(BufferedImage myImage, BufferedImage otherImage) {
		// BufferedImage otherImage = other.getBufferedImage();
		// BufferedImage myImage = getBufferedImage();
		if (otherImage.getWidth() != myImage.getWidth()) {
			LOG.info("图片宽度不一致");
			return 0; 
		}
		if (otherImage.getHeight() != myImage.getHeight()) {
			LOG.info("图片高度不一致");
			return 0; 
		}
		int width = myImage.getWidth();
		int height = myImage.getHeight();
		int numDiffPixels = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
					numDiffPixels++;
				}
			}
		}
		double numberPixels = height * width;
		double samePixels = numberPixels - numDiffPixels;
		double samePercent = samePixels / numberPixels * 100;
		LOG.info("相似像素数量：" + samePixels + " 不相似像素数量：" + numDiffPixels + " 相似率：" + String.format("%.1f", samePercent) + "%");
		return samePercent;
	}

	public static BufferedImage getSubImage(BufferedImage image, int x, int y, int w, int h) {
		return image.getSubimage(x, y, w, h);
	}

	public static BufferedImage getImageFromFile(File f) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// if failed, then copy it to local path for later check:TBD
			// FileUtils.copyFile(f, new File(p1));
			LOG.error("加载图片文件报错",e);
		}
		return img;
	}

	@Deprecated
	public static String[][] getPX(String args) {
		int[] rgb = new int[3];
		File file = new File(args);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		String[][] list = new String[width][height];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
			}
		}
		return list;
	}

	@Deprecated
	public static int compareImage(String imgPath1, String imgPath2) {
		String[] images = { imgPath1, imgPath2 };
		if (images.length == 0) {
			System.out.println("Usage >java BMPLoader ImageFile.bmp");
			System.exit(0);
		}
		String[][] list1 = getPX(images[0]);
		String[][] list2 = getPX(images[1]);
		int xiangsi = 0;
		int busi = 0;
		int i = 0, j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m = 0; m < strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n = 0; n < value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		list1 = getPX(images[1]);
		list2 = getPX(images[0]);
		i = 0;
		j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m = 0; m < strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n = 0; n < value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		String baifen = "";
		try {
			baifen = ((Double.parseDouble(xiangsi + "") / Double.parseDouble((busi + xiangsi) + "")) + "");
			baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);
		} catch (Exception e) {
			baifen = "0";
		}
		if (baifen.length() <= 0) {
			baifen = "0";
		}
		if (busi == 0) {
			baifen = "100";
		}
		LOG.info("相似像素数量：" + xiangsi + " 不相似像素数量：" + busi + " 相似率：" + Integer.parseInt(baifen) + "%");
		return Integer.parseInt(baifen);
	}

	public static void main(String[] args) throws IOException {
		BufferedImage f1 = getImageFromFile(new File("F:\\eclipse-workspace\\Automation\\screenshots\\standard\\checkHalfWay23.png"));
		//标准图片
//				BufferedImage image = BMPLoader.getImageFromFile(path1);
//				BufferedImage imageall = BMPLoader.getImageFromFile(path2);
				BufferedImage subimage = BMPLoader.getSubImage(f1, 0, 70, 1080, 1920-70);
				FileOutputStream out = new FileOutputStream(new File("F:\\eclipse-workspace\\Automation\\screenshots\\standard\\checkHalfWay1.png"));
				ImageIO.write(subimage, "png", out);
//		compareImage(f1, f2);
	}
}