package com.guohuai.payment.ucfpay.util;

import java.security.MessageDigest;

public class UcfDigestUtils {
    public UcfDigestUtils() {
    }

    public static String digest(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bDigests = md.digest(input.getBytes("UTF-8"));
            return byte2hex(bDigests);
        } catch (Exception var3) {
            return "";
        }
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    public static boolean isEqual(String digesta, String digestb) throws Exception {
        try {
            return MessageDigest.isEqual(digesta.toUpperCase().getBytes(), digestb.toUpperCase().getBytes());
        } catch (Exception var3) {
            throw var3;
        }
    }
}
