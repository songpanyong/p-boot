package com.guohuai.basic.component.watermark;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;

import com.alibaba.fastjson.JSON;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PdfWatermark{
	/**
	 * 给Pdf增加文字水印
	 */
	public static Boolean pdfWatermarkByText(Watermark watermark) {
		log.info("pdfWatermarkByText watermark : "+JSON.toJSONString(watermark));
		try {
			PdfReader reader = new PdfReader(new FileInputStream(new File(watermark.getSrcPath())));
			BufferedOutputStream bos;
			String tarPath = watermark.getTarPath();
			if(tarPath == null){
				bos = new BufferedOutputStream(new FileOutputStream(new File(watermark.getSrcPath())));
			}else{
				bos = new BufferedOutputStream(new FileOutputStream(new File(tarPath+System.currentTimeMillis()+".pdf")));
			}
			PdfStamper stamper = new PdfStamper(reader, bos);
			int total = reader.getNumberOfPages() + 1;
			PdfContentByte content;
			BaseFont bf = BaseFont.createFont(watermark.getTtfPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			PdfGState gs = new PdfGState();
			for (int i = 1; i < total; i++) {
				content = stamper.getOverContent(i);     // 在内容上方加水印
				gs.setFillOpacity(watermark.getAlpha()); // 设置水印的透明度
				content.beginText();
				content.setColorFill(watermark.getFontColor());     // 设置字体颜色
				content.setFontAndSize(bf, watermark.getFontSize());// 设置字体大小
				content.setTextMatrix(70, 200);
				content.showTextAligned(Element.ALIGN_CENTER, watermark.getContent(), 300, 350, watermark.getDegree());
				content.endText();
			}
			stamper.close();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 给Pdf增加图片水印
	 */
	public static Boolean pdfWatermarkByIcon(Watermark watermark) {
		try {
			PdfReader reader = new PdfReader(new FileInputStream(new File(watermark.getSrcPath())));
			BufferedOutputStream bos;
			String tarPath = watermark.getTarPath();
			if(tarPath == null){
				bos = new BufferedOutputStream(new FileOutputStream(new File(watermark.getSrcPath())));
			}else{
				bos = new BufferedOutputStream(new FileOutputStream(new File(tarPath+System.currentTimeMillis()+".pdf")));
			}
			PdfStamper stamper = new PdfStamper(reader, bos);
			int total = reader.getNumberOfPages() + 1;
			PdfContentByte content;
			PdfGState gs = new PdfGState();
			for (int i = 1; i < total; i++) {
				// 在内容上方加水印
				content = stamper.getOverContent(i);
				gs.setFillOpacity(watermark.getAlpha());
				Image image = Image.getInstance(Files.readAllBytes(new File(watermark.getIconPath()).toPath()));
				//旋转角度
				if(watermark.getDegree()!=null){					
					image.setRotationDegrees(watermark.getDegree());
				}
				image.setAbsolutePosition(200, 406); 
				image.scaleToFit(200, 200);
				content.addImage(image);
			}
			stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}	
