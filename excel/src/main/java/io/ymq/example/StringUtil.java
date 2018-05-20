package io.ymq.example;

/**
 * Copyright © 2010 Higinet Tech.All Rights Reserved
 * 
 * @author xuyanhua xuyh@higinet.com.cn
 * @date 2011-4-26
 * @description 字符串工具类
 */
public class StringUtil {
	/**
	 * 判空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}
}
