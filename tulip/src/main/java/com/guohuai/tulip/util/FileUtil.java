package com.guohuai.tulip.util;

import java.io.File;

public class FileUtil {
	/**
	 * 删除文件或目录
	 * @param File file a file or a directory
	 * @param boolean deleteSelf when true delete self , other not delete self
	 */
	public static void deleteFiles(File file, boolean deleteSelf) {
		if (file.isDirectory()) {
			File[] fileArr = file.listFiles();
			for (File tmpFile : fileArr) {
				deleteFiles(tmpFile, true);
			}
			if (deleteSelf) {
				file.delete();
			}
		} else if (file.isFile()) {
			file.delete();
		}
	}
	
	/**
	 * 删除文件或目录
	 * @param String path a file string path or a dir string path
	 * @param boolean deleteSelf when true delete self , other not delete self
	 */
	public static void deleteFiles(String path, boolean deleteSelf) {
		deleteFiles(new File(path), deleteSelf);
	}
	
	/**
	 *  删除文件或目录
	 * @param File[] fileArr
	 */
//	public static void deleteFiles(File[] fileArr) {
//		for (File file : fileArr) {
//			deleteFiles(file);
//		}
//	}
	
	/**
	 *  删除文件或目录
	 * @param String[] pathArr
	 */
//	public static void deleteFiles(String[] pathArr) {
//		for (String path : pathArr) {
//			deleteFiles(path);
//		}
//	}
	
	public static boolean mkdirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}
	
	public static void main(String[] args) {
		deleteFiles("D:\\unix111", false);
		deleteFiles("D:\\unix222", true);
	}
}
