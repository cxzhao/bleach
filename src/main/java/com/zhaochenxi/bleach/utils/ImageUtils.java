package com.zhaochenxi.bleach.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	public static String compressImage(String ImagePath) {
		return ImagePath;
	}

	/**
	 * 读取文件中的图片
	 *
	 * @param ImagePath
	 * @return
	 */
	public static BufferedImage getImage(String ImagePath) {
		BufferedImage srcImage = null;
		FileInputStream in = null;
		try {

			File file = new File(ImagePath);
			in = new FileInputStream(file);
			byte[] b = new byte[5];
			in.read(b);
			srcImage = javax.imageio.ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("读取图片文件出错！", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("关闭文件输入流出错！", e);
				}
			}
		}
		return srcImage;
	}

	/**
	 * 将图片按照指定的图片尺寸、图片质量压缩
	 * 
	 * @param srcImgPath
	 * @param outImgPath
	 * @param new_w
	 * @param new_h
	 * @param per
	 */
	public static void compress(String srcImgPath, String outImgPath, int new_w, int new_h, float per) {
		// 得到图片
		BufferedImage src = getImage(srcImgPath);
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		// 根据原图的大小生成空白画布
		BufferedImage tempImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
		// 在新的画布上生成原图的缩略图
		Graphics2D g = tempImg.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, old_w, old_h);
		g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
		g.dispose();
		BufferedImage newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		newImg.getGraphics().drawImage(tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 调用方法输出图片文件
		outImage(outImgPath, newImg);
	}

	/**
	 * 将图片按照指定的尺寸比例、图片质量压缩
	 * 
	 * @param srcImgPath
	 *            :源图片路径
	 * @param outImgPath
	 *            :输出的压缩图片的路径
	 * @param ratio
	 *            :压缩后的图片尺寸比例
	 * @param per
	 *            :百分比
	 */
	public static void compress(String srcImgPath, String outImgPath, float ratio, float per) {
		// 得到图片
		BufferedImage src = getImage(srcImgPath);
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		int new_w = 0;
		// 新图的宽
		int new_h = 0;
		// 新图的长
		BufferedImage tempImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tempImg.createGraphics();
		g.setColor(Color.white);
		// 从原图上取颜色绘制新图g.fillRect(0, 0, old_w, old_h);
		g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
		g.dispose();
		new_w = (int) Math.round(old_w * ratio);
		new_h = (int) Math.round(old_h * ratio);
		BufferedImage newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		newImg.getGraphics().drawImage(tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 调用方法输出图片文件
		outImage(outImgPath, newImg);
	}

	/**
	 * * 将图片文件输出到指定的路径
	 * 
	 * @param outImgPath
	 * @param newImg
	 */
	public static void outImage(String outImgPath, BufferedImage newImg) {
		// 判断输出的文件夹路径是否存在，不存在则创建
		File file = new File(outImgPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		// 输出到文件流
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outImgPath);
			ImageIO.write(newImg, "JPG", fos);
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static void main(String[] args) {
		compress("F://ling.jpg", "F://ling4.jpg", 0.5f, 1f);
	}

}
