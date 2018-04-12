package com.guohuai.payment.ucfpay.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class AESCoder {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String CHAR_SET = "utf-8";

    public AESCoder() {
    }

    public static String createKey() {
        byte[] key = initSecretKey();
        return Hex.encodeHexString(key);
    }

    private static byte[] initSecretKey() {
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
            return new byte[0];
        }

        kg.init(128);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    public static Key toKey(byte[] key) {
        return new SecretKeySpec(key, "AES");
    }

    public static byte[] encrypt(byte[] data, Key key) throws GeneralSecurityException {
        return encrypt(data, key, "AES/ECB/PKCS5Padding");
    }

    public static byte[] encrypt(byte[] data, byte[] key) throws GeneralSecurityException {
        return encrypt(data, key, "AES/ECB/PKCS5Padding");
    }

    public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws GeneralSecurityException {
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(1, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws GeneralSecurityException {
        return decrypt(data, key, "AES/ECB/PKCS5Padding");
    }

    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        return decrypt(data, key, "AES/ECB/PKCS5Padding");
    }

    public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws GeneralSecurityException {
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(2, key);
        return cipher.doFinal(data);
    }

    private static String showByteArray(byte[] data) {
        if (null == data) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder("{");
            byte[] arr$ = data;
            int len$ = data.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                byte b = arr$[i$];
                sb.append(b).append(",");
            }

            sb.deleteCharAt(sb.length() - 1);
            sb.append("}");
            return sb.toString();
        }
    }

    public static String encrypt(String data, String key) throws Exception {
        byte[] keyByte = Hex.decodeHex(UcfDigestUtils.digest(key).toCharArray());
        Key k = toKey(keyByte);
        byte[] encryptData = encrypt(data.getBytes("utf-8"), k);
        return Base64.encodeBase64String(encryptData);
    }

    public static String decrypt(String data, String key) throws Exception {
        byte[] keyByte = Hex.decodeHex(UcfDigestUtils.digest(key).toCharArray());
        Key k = toKey(keyByte);
        byte[] decryptData = decrypt(Base64.decodeBase64(data), k);
        return new String(decryptData, "utf-8");
    }

    public static void main(String[] args) throws Exception {
        byte[] key = initSecretKey();
        System.out.println("key：" + showByteArray(key));
        System.out.println("key：" + Hex.encodeHexString(key));
        System.out.println("key：" + showByteArray(Hex.decodeHex(Hex.encodeHexString(key).toCharArray())));
        key = Hex.decodeHex(Hex.encodeHexString(key).toCharArray());
        Key k = toKey(key);
        String data = "AES数据";
        System.out.println("加密前数据: string:" + data);
        System.out.println("加密前数据: byte[]:" + showByteArray(data.getBytes()));
        System.out.println();
        byte[] encryptData = encrypt(data.getBytes(), k);
        System.out.println("加密后数据: byte[]:" + showByteArray(encryptData));
        System.out.println("加密后数据: hexStr:" + Hex.encodeHexString(encryptData));
        System.out.println();
        byte[] decryptData = decrypt(encryptData, k);
        System.out.println("解密后数据: byte[]:" + showByteArray(decryptData));
        System.out.println("解密后数据: string:" + new String(decryptData));
        System.out.println();
        System.out.println();
        System.out.println();
        String data2 = "AES数据21212";
        String tempkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbj/6kF5xteqnO1rub+dq+0THaW9Hs/EO2GNuCE5dr703v8M6zLGVtReuCr0Oxc9aP+sMckOLtTglsm+McjiaDYsIGkcLXEwaV8ZFfLMswQ65rJd6rizUNLAsQIQiJvS4lfe3biGhdap/+gxQR2XFKgiKvghyQFMvn936rjiK83QIDAQAB";
        System.out.println("加密前数据: string:" + data2);
        System.out.println();
        String encryptData2 = encrypt(data2, tempkey);
        System.out.println("加密后数据: hexStr:" + encryptData2);
        System.out.println();
        String decryptData2 = decrypt(encryptData2, tempkey);
        System.out.println("解密后数据: string:" + decryptData2);
        System.out.println();
    }
}
