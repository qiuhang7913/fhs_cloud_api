package com.self.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 字符串工具类
 * @author qiuhang
 * @date 2019/9/25/025
 */
public class StrTool {
	private static String hexString = "0123456789ABCDEF";

	/**
	 * @des 在字符串左侧补齐空格
	 */
	public final static int LEFT_SPACE = 0;

	/**
	 * @des 在字符串右侧补齐空格
	 */
	public final static int RIGHT_SPACE = 1;

	/**
	 * @des 如果字符串实际长度超出指定长度，将左侧截断
	 */
	public final static int TRUNC_LEFT = 0;

	/**
	 * @des 如果字符串实际长度超出指定长度，将右侧截断
	 */
	public final static int TRUNC_RIGHT = 1;

	public final static String EMPTY_STR = "";

	/**
	 * @des 剔除特殊字符
	 * @param text
	 * @param split
	 * @return
	 */
	public static String removeSpecialchar(String text, String split)
	{
		if (null != text && !"".equals(text.trim()))
		{
			return text.replaceAll(split, "");
		}
		return "";

	}

	/**
	 * @des 查找是否存在关键字
	 * @param keyword
	 * @param content
	 * @return
	 */
	public String getContentByKeyWord(String keyword, String content)
	{
		if (content.contains(keyword))
		{
			return content;
		}
		else
		{
			return "";
		}
	}

	/**
	 * @des 前后截断获取中间字符串
	 * @param head 前位置
	 * @param foot 后位置
	 * @param content
	 * @return
	 */
	public String getContentByCut(String head, String foot, String content)
	{
		int beginIndex = content.indexOf(head);
		int diff = head.length();
		int endIndex = content.indexOf(foot);
		String htmStr = "";
		if (beginIndex > 0 && endIndex > 0)
		{
			htmStr = content.substring(beginIndex + diff, endIndex);
		}
		return htmStr;

	}

	/**
	 * @des 分割字符串
	 * @param split
	 * @param content
	 * @return
	 */
	public static List<String> getContentBySplit(String split, String content) throws Exception {
		if (isEmpty(content) || isEmpty(split)){
			throw new Exception("错误的传值");
		}
		return Arrays.asList(content.split(split));
	}

	/**
	 * @des 除去转义符号和空字符
	 * @param src
	 * @return
	 */
	public static String removeEsc(String src)
	{
		String str =
				src.replaceAll("\\\\/", "/").replaceAll("\\\\\"", "\"").replaceAll("\\\\t", "").replaceAll("\\\\n", "");
		return str;
	}

	/**
	 * @des Unicode转中文
	 * @param dataStr
	 * @return
	 */
	public static String decodeUnicode(final String dataStr)
	{
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1)
		{
			start = dataStr.indexOf("\\u", start);
			if (start == -1)
			{
				buffer.append(dataStr.substring(end));
			}
			else
			{
				buffer.append(dataStr.substring(end, start));
				end = start + 6;

				String charStr = "";
				charStr = dataStr.substring(start + 2, end);
				char letter = '0';
				try
				{
					letter = (char)Integer.parseInt(charStr, 16);
					buffer.append(new Character(letter).toString());
				}
				catch (NumberFormatException e)
				{
					buffer.append("\\u" + charStr);
				}
				start = end;
			}
		}
		return buffer.toString();
	}

	/**
	 * @des 该方法计算字符串(包括中文)的实际长度
	 * @param str 需要计算长度的字符串
	 * @return int 字符串的实际长度
	 */
	public static int length(String str)
	{
		if (str == null)
		{
			return 0;
		}
		try
		{
			return new String(str.getBytes("GBK"), "8859_1").length();
		}
		catch (UnsupportedEncodingException e)
		{
			return -1;
		}
	}

	/**
	 * @des 将id类型的list用","拼接
	 * @param list 字符串集合
	 * @return 处理后的字符串
	 */
	public static String getStrForIntegerIn(List<String> list)
	{
		if (null == list || list.size() == 0)
		{
			return null;
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < list.size(); i++)
		{
			result.append(",").append(list.get(i));
		}
		return result.length() > 0 ? result.substring(1) : result.toString();
	}

	/**
	 * @des 验证字符串是否包含中文
	 *
	 * @param param
	 * @return 包含中文返回 ：true
	 */
	public static boolean validtIsChinese(String param)
	{
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]+$");
		Matcher matcher = pattern.matcher(param);
		return !matcher.matches();
	}

	/**
	 * @des 用分隔符连接数据
	 */
	public static String join(Collection<?> coll, String split, String defaultStr)
	{
		if (coll == null || coll.isEmpty())
		{
			return defaultStr;
		}
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (Object s : coll)
		{
			if (isFirst)
			{
				isFirst = false;
			}
			else
			{
				sb.append(split);
			}
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * @des 用分隔符连接数据
	 */
	public static String join(Collection<?> coll, String split)
	{
		return join(coll, split, "");
	}

	/**
	 * @description 字符串空判断
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	/**
	 * @description 根据时间获取唯一字符串
	 * @author qiuhang
	 * @date 2019/9/24/024
	 */
	public static synchronized String obtainOnlyByData(){
		String curtTime = DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L7);
		return curtTime + getRandom20(3);
	}

	/**
	 * @description 获取20位以内的随机数字字符串
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	public static synchronized String getRandom20(Integer length){
	 	String result = "";
		Random random = new Random();
		int n = 20;
		if (!ObjectCheckUtil.checkIsNullOrEmpty(length)){
			n = length;
		}
		int randInt = 0;
		for(int i = 0; i < n; i++){
			randInt = random.nextInt(10);
			result += randInt;
		}
		return  result;
	}
	/**
	 * @description 字符串加密
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	public static String encode(String str) {
		byte[] bytes = str.getBytes();
		return encode(bytes);
	}
	/**
	 * @description 字符串解密
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	public static String decode(String bytes) {
		return new String(decodeBytes(bytes));
	}

	/**
	 * @description 去空
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	public static String escape_null(String value) {
		if (null == value) {
			return "";
		}
		return value;
	}

	/**
	 * @description 是否为数字
	 * @author qiuhang
	 * @date 2019/9/25/025
	 */
	public static boolean is_num(String str) {
		if (null == str) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}
	
	public static long str2long(String str) {
		if (str == null || str.trim().length() == 0) {
			return 0;
		}
		
		try {
			return Long.parseLong(str);
		} catch (Exception e) {			
		}
		
		return 0;
	}

	/**
	 * 判断两个字符串是否相等 如果都为null则判断为相等,一个为null另一个not null则判断不相等 否则如果s1=s2则相等
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equals(String s1, String s2) {
		if (StrTool.isEmpty(s1) && StrTool.isEmpty(s2)) {
			return true;
		} else if (!StrTool.isEmpty(s1) && !StrTool.isEmpty(s2)) {
			return s1.equals(s2);
		}
		return false;
	}

	/**
	 * 生成uuid
	 * @return
	 */
	public synchronized static String getUUID(){
		return UUID.randomUUID().toString();
	}


	public static String encode(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	public static byte[] decodeBytes(String bytes) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(
					bytes.length() / 2);
			for (int i = 0; i < bytes.length(); i += 2) {
				baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
						.indexOf(bytes.charAt(i + 1))));
			}
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(encode("aaa"));
		System.out.println(decode("616161"));
	}
}
