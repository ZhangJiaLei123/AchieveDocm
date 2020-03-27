package com.yfhl.commons.utils;

import java.rmi.dgc.VMID;

/**
 * 产生唯一Key。<br>
 * 
 * @author ZHH
 */

public class UniqueUtil {
	/**
	 * 默认构造函数
	 */
	public UniqueUtil() {
	}

	/**
	 * 产生唯一KEY值．
	 * <p/>
	 * String key = UniqueKeyUtil.getKey();
	 * <p/>
	 * 
	 * @return　String 唯一KEY值
	 */
	public static String getKey() {
		String strResult = "";
		String strKey = (new VMID()).toString();
		for (int i = 0; i < strKey.length(); i++) {
			char ch = strKey.charAt(i);
			if (ch != ':' && ch != '-')
				strResult = strResult + ch;
		}

		return strResult;

	}

	/**
	 * 使用java.util.UUID生成 UUID。<br>
	 * 并去掉字符串中的“-”，且将字母全部转成大写。
	 * 
	 * @return String
	 */
	public static String UUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "")
				.toUpperCase();
	}

}
