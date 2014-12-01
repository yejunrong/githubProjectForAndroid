package com.flowcontrol.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FCUtils {
	/**
	 * 去掉符号，只取数字
	 */
	public static String getOnlyNumber(String str) {
		String returnStr = null;
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		returnStr = m.replaceAll("").trim();
		return returnStr;
	}
}
