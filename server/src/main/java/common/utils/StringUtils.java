package common.utils;

import java.util.Random;

public class StringUtils extends org.apache.commons.lang3.StringUtils{
	public static String randomName(){
		return RandomString(5);
	}
	
	/**
	 * 随机字符串
	 * @param length
	 * @return
	 */
	public static String RandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(62);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	
}
