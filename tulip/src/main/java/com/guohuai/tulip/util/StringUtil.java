package com.guohuai.tulip.util;
import java.util.UUID;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class StringUtil {
    public final static String EMPTY = "";
    public final static boolean isEmpty(String s) {
        return s == null || s.trim().equals(EMPTY);
    }
    public final static String vague(String s, int begin, int end) {
        if (isEmpty(s)) {
            return s;
        }
        if (s.length() <= begin + end) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = begin; i < chars.length - end; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }
    public final static String uuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
    // public final static String myuid() {
    // UUID uuid = Generators.timeBasedGenerator().generate();
    // long mostSigBits = uuid.getMostSignificantBits();
    // long leastSigBits = uuid.getLeastSignificantBits();
    // return (
    // digits(mostSigBits, 4) +
    // digits(mostSigBits >> 16, 4) +
    // digits(mostSigBits >> 32, 8) +
    // digits(leastSigBits >> 48, 4) +
    // digits(leastSigBits, 12)
    // );
    // }
//
    public final static boolean in(String param, String... factor) {
        if (null != factor && factor.length != 0) {
            for (String f : factor) {
                if (param.equals(f)) {
                    return true;
                }
            }
        }
        return false;
    }
	public final static boolean inWithIgnoreCase(String param, String... factor) {
     if (null != factor && factor.length != 0) {
         for (String f : factor) {
             if (param.equalsIgnoreCase(f)) {
                 return true;
             }
         }
     }
     return false;
 }
//
//	private static final Random random = new Random();
//	public static String random(int bytesLength) {
//		Validate.isTrue(bytesLength > 0, "numBytes argument must be a positive integer (1 or larger)", bytesLength);
//		byte[] bytes = new byte[bytesLength];
//		random.nextBytes(bytes);
//		String s = encodeBase64(bytes);
//		return s;
//	}
//
//	public static String encodeBase64(byte[] bytes) {
//		return Base64.encodeBase64String(bytes);
//	}
//
//	public static byte[] decodeBase64(String cipher) {
//		return Base64.decodeBase64(cipher);
//	}
    public static boolean isExsit(String source, String... useProperty) {
        if (source == null) {
            return false;
        }
        if (useProperty.length == 0) {
            return false;
        }
        JSONObject json = (JSONObject) JSONObject.parse(source);
        for (String string : useProperty) {
            if (!json.containsKey(string)) {
                return false;
            }
            Object o = json.get(string);
            if (o instanceof JSONArray) {
                if (((JSONArray) o).size() == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    public static String getString(String source, String useProperty) {
        if (source == null) {
            return null;
        }
        JSONObject json = (JSONObject) JSONObject.parse(source);
        if (!json.containsKey(useProperty)) {
            return null;
        }
        String str = json.getString(useProperty);
        return str;
    }
}