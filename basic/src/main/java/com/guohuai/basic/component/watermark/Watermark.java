package com.guohuai.basic.component.watermark;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Watermark {
	/**
	 * 待加水印的源文件路径
	 */
	private String srcPath;
	/**
	 * 加完水印后的文件路径（可以为空，为空时是在源文件增加水印；非空时在该目录下生成加完水印后的文件）
	 */
	private String tarPath;
	/**
	 * 文件增加的水印文字串
	 */
	private String content;
	/**
	 * 水印图片路径
	 */
	private String iconPath;
	/**
	 * 图片文件增加的水印图片对象
	 */
	private Image iconImage;
	/**
	 * 水印文字颜色（可以为空，默认为红色）
	 */
	private Color fontColor = Color.red;
	/**
	 * 水印文字旋转角度（可以为空，默认45度）
	 */
	private Integer degree = 45;
	/**
	 * 水印的透明度（可以为空，默认0.2f（透明度 值：0.0-1.0, 0.0为完全透明，1.0 为完全不透明））
	 */
	private Float alpha = 0.2f;
	/**
	 * 用户自定义字体文件路径（可以为空，为空则不使用自定义字体，默认字体为宋体）
	 */
	private String ttfPath;
	/**
	 * 水印文字的大小（可以为空，默认36磅）
	 */
	private Float fontSize = 36f;
	/**
	 * 水印文字样式（可以为空，字体样式有：Font.PLAIN - 普通样式；Font.ITALIC - 斜体样式；Font.BOLD - 加粗样式；默认为Font.PLAIN）
	 */
	private int fontStyle = Font.PLAIN;
}
