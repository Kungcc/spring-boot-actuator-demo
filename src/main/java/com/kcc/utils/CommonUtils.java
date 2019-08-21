package com.kcc.utils;

public final class CommonUtils {

	private CommonUtils() {}

	public static boolean isChinese(String string) {
		int n = 0;
		for (int i = 0; i < string.length(); i++) {
			n = (int) string.charAt(i);
			if (19968 <= n && n < 40869) {
				return true;
			}
		}
		return false;
	}
}
