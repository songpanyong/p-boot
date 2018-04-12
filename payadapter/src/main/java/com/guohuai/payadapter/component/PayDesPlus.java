package com.guohuai.payadapter.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

@Configuration
public class PayDesPlus {
	private static String  PASSWORD_CRYPT_KEY="|5ES0*-7";

	@Value("${des.password:'|5ES0*-7'}")
	public void setPasswordCryptKey(String arg) {
		PASSWORD_CRYPT_KEY=arg;
	}

	public static String decrypt(String message) {
		String str = "";
		try {
			byte[] bytesrc = convertHexString(message);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] retByte = cipher.doFinal(bytesrc);
			str = new String(retByte);
			str = URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
		}
		return str;
	}

	public static byte[] encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes("UTF-8"));
	}

	public static String encrypt(String value) {
		String result = "";
		try {
			value = java.net.URLEncoder.encode(value, "UTF-8");
			result = toHexString(encrypt(value, PASSWORD_CRYPT_KEY)).toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		return result;
	}

	public static byte[] convertHexString(String ss) throws UnsupportedEncodingException {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		try {
			System.out.println(encrypt("44250100013100000622"));
			System.out.println(toHexString(encrypt("44250100013100000622", "|5ES0*-7")).toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
	}
	

}