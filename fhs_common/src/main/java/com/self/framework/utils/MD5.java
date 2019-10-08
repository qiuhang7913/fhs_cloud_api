package com.self.framework.utils;

import java.security.NoSuchAlgorithmException;

/**
 * @description：md5 加密工具类
 * @author qiuhang
 * @date 2019/9/25/025
 */
public class MD5 {
	public static String digest(byte[] source) {

		java.security.MessageDigest md;
		try {
			md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			return StrTool.encode(tmp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String digest(String source) {
		if (source == null) {
			return null;
		}
		return digest(source.getBytes());
	}
	
	public static void main(String[] a) {
		System.out.println(digest(""));
		System.out.println(digest(""));
		System.out.println(digest(""));
	}
}
