package com.guohuai.basic.common;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.watermark.ImgWatermark;
import com.guohuai.basic.component.watermark.PdfWatermark;
import com.guohuai.basic.component.watermark.Watermark;

import lombok.extern.slf4j.Slf4j;

/**
 * 简介：GIF，PNG，JPG，PDF等文件增加水印工具类
 */
@Slf4j
public class WatermarkUtils {
	/**
	 * 功能简介：给图片增加文字水印
	 * 参数说明：WatermarkReq watermarkReq
	 * 返回类型：boolean
	 * 创建时间：2017-08-31 11:16:08
	 * 备注信息：注意必要参数的传递
	 */
	public static boolean imgWatermarkByText(Watermark watermark) {
		return ImgWatermark.imgWatermarkByText(watermark);
	}

	/**
	 * 功能简介：给图片增加图片水印
	 * 参数说明：WatermarkReq watermarkReq
	 * 返回类型：boolean
	 * 创建时间：2017-08-31 13:36:17
	 * 备注信息：注意必要参数的传递
	 */
	public static boolean imgWatermarkByIcon(Watermark watermark) {
		Image srcImg =null;
		if(!StringUtil.isEmpty(watermark.getIconPath())){			
			try {
				srcImg = ImageIO.read(new File(watermark.getIconPath()));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		watermark.setIconImage(srcImg);
		return ImgWatermark.imgWatermarkByIcon(watermark);
	}

	/**
	 * 功能简介：给PDF增加文字水印
	 * 参数说明：WatermarkReq watermarkReq
	 * 返回类型：boolean
	 * 创建时间：2017-08-31 15:16:21
	 * 备注信息：注意必要参数的传递
	 */
	public static boolean pdfWatermarkByText(Watermark watermark) {
		if(StringUtil.isEmpty(watermark.getSrcPath()) || StringUtil.isEmpty(watermark.getContent()) || StringUtil.isEmpty(watermark.getTtfPath())){
			log.info("pdfWatermarkByText:待加水印文件或内容或字体文件为空");
			return Boolean.FALSE;
		}
		return PdfWatermark.pdfWatermarkByText(watermark);
	}
	
	/**
	 * 功能简介：给PDF增加图片水印
	 * 参数说明：WatermarkReq watermarkReq
	 * 返回类型：boolean
	 * 创建时间：2017-08-31 17:06:06
	 * 备注信息：注意必要参数的传递
	 */
	public static boolean pdfWatermarkByIcon(Watermark watermark){
		return PdfWatermark.pdfWatermarkByIcon(watermark);
	}
}
