package com.guohuai.basic.common;

public class IDCardUtil {

	public static boolean valid(String id18) {

		char code = xcode(id18);

		return code == id18.charAt(17);
	}

	// 根据前17位, 算最后一位
	private static char xcode(String id) {
		char[] xcode = { '1', '0', 'x', '9', '8', '7', '6', '5', '4', '3', '2' };

		int[] ai = new int[17];
		// 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
		int[] wi = wi();
		int[] aimulwi = new int[17];
		int s = 0;
		int c = 0;

		char[] idh = id.toCharArray();

		for (int i = 0; i < 17; i++) {
			ai[i] = Character.getNumericValue(idh[i]);
			aimulwi[i] = ai[i] * wi[i];
			s += aimulwi[i];
		}

		c = s % 11;

		char code = xcode[c];

		return code;
	}

	public static String up18(String id15) {
		String id17 = String.format("%s19%s", id15.substring(0, 6), id15.substring(6));
		return String.format("%s%s", id17, xcode(id17));
	}

	private static int[] wi() {
		int[] wi = new int[17];
		for (int i = 16; i >= 0; i--) {
			wi[16 - i] = (2 << i) % 11;
		}
		return wi;
	}

}
