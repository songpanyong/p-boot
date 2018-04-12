package com.guohuai.basic.component.watermark;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.guohuai.basic.common.StringUtil;

public class ImgWatermark{
	/**
	 * 给图片增加文字水印
	 */
	public static boolean imgWatermarkByText(Watermark watermark) {
		try {
			// 读取待加水印图片
			Image srcImg = ImageIO.read(new File(watermark.getSrcPath()));
			// 获取源图片宽和高
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);
			BufferedImage buffImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
			// 获得绘图画笔对象
			Graphics2D g = buffImg.createGraphics();
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			g.setColor(watermark.getFontColor());
			g.setFont(getFontByUserDefinedTTF(watermark));
			// 设置对线段锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			// 设置水印的透明度（透明度 ：0.0-1.0, 0.0为完全透明，1.0 为完全不透明）
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, watermark.getAlpha()));
			if(watermark.getDegree() != null){				
				// 水印文字旋转位置
				g.rotate(Math.toRadians(watermark.getDegree()), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}
			int contentHeight = new Float(watermark.getFontSize()).intValue();
			int contentWidth = contentHeight * getLength(watermark.getContent());
			// 绘制图片水印文字
			g.drawString(watermark.getContent(), (srcImgWidth - contentWidth)/2, (srcImgHeight - contentHeight)/2);				
			g.dispose();
			// 输出添加水印图片
			FileOutputStream outImgStream = null;
			if(StringUtil.isEmpty(watermark.getTarPath())){
				outImgStream = new FileOutputStream(watermark.getSrcPath());
			}else{
				outImgStream = new FileOutputStream(watermark.getTarPath());
			}
			// 写出加完水印图片
			ImageIO.write(buffImg, "JPG", outImgStream);
			outImgStream.flush();
			outImgStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 给图片增加图片水印
	 */
	public static boolean imgWatermarkByIcon(Watermark watermark) {
		try {
			// 读取待加水印图片
			Image srcImg = ImageIO.read(new File(watermark.getSrcPath()));
			// 为图片添加加水印
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			if(watermark.getDegree() != null){				
				g.rotate(Math.toRadians(watermark.getDegree()),(double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, watermark.getAlpha()));
	        Image iconImage = watermark.getIconImage();
	        int iconWidth = iconImage.getWidth(null);
            int iconHeight = iconImage.getHeight(null);
            // 上减下加，左减右加
            g.drawImage(iconImage, (srcImg.getWidth(null) - iconWidth) / 2, (srcImg.getHeight(null) - iconHeight) / 2, iconWidth, iconHeight, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			// 输出添加水印图片
			FileOutputStream outImgStream = null;
			if(StringUtil.isEmpty(watermark.getTarPath())){
				outImgStream = new FileOutputStream(watermark.getSrcPath());
			}else{
				outImgStream = new FileOutputStream(watermark.getTarPath());
			}
			ImageIO.write(buffImg, "JPG", outImgStream);
			outImgStream.flush();
			outImgStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 获取水印文字的长度值
	 */
	private static int getLength(String waterMarkContent) {
        int textLength = waterMarkContent.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(waterMarkContent.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }
	
	/**
	 * 获取水印文字的长度值（备用）
	 */
	public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}
	
	/**
	 * 为水印文字设置字体，样式和大小
	 */
	private static Font getFontByUserDefinedTTF(Watermark watermark) throws FileNotFoundException, FontFormatException, IOException{
		Font font = null;
		if(!StringUtil.isEmpty(watermark.getTtfPath())){
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(watermark.getTtfPath())));
			font = font.deriveFont(watermark.getFontStyle(), watermark.getFontSize());
		}else{
			font = new Font("宋体", watermark.getFontStyle(), new Float(watermark.getFontSize()).intValue());
		}
		return font;
	}
	
	/**
	 * 检验增加水印参数信息
	 */
	private void asemmbleWatermarkReq(Watermark watermark, String watermarkType){
		
	}
}
