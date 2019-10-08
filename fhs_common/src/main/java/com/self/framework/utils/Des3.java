package com.self.framework.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * @description 3des 加密工具类
 * @author qiuhang
 * @date 2019/9/25/025
 */
public class Des3 {
	private static final String Algorithm = "DESede"; 

	/**
	 * @description 加密
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	public static String enc(String key, String src) {
		if (src == null || key == null) {
			return null;
		}
		try {
			byte[] bs = encryptMode(formatKey(key), src.getBytes("GBK"));
			return StrTool.encode(bs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @description 解密
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	public static String dec(String key, String src) {
		if (src == null) {
			return null;
		}
		byte[] bs = StrTool.decodeBytes(src);

		if (bs == null) {
			return null;
		}

		byte[] rv = decryptMode(formatKey(key), bs);
		if (rv == null) {
			return null;
		}
		return new String(rv);
	}

	/**
	 * @description
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * @description：
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;

	}

	private static byte[] formatKey(String keys) {
		byte[] key = new byte[24];
		byte[] tmp = keys.getBytes();
		for (byte i = 0; i < 24; i++) {
			if (tmp != null && i < tmp.length) {
				key[i] = tmp[i];
			} else {
				key[i] = i;
			}
		}
		return key;
	}

	public static void main(String[] a) {
		String str = "123";
		str = enc("sss",str);
		String decStr = str;
		decStr = dec("sss", decStr);
		System.out.println(str);
		System.out.println(decStr);
	}

}