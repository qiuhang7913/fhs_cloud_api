package com.self.framework.utils;

import com.self.framework.cenum.HttpResultEnum;
import com.self.framework.exception.BusinessException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author qiuhang
 * @version v1
 * @des: 时间工具类
 */
public class DateTool {
	public static final String FORMAT_L6 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_L5 = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_L4 = "yyyy-MM-dd HH";
	public static final String FORMAT_L3 = "yyyy-MM-dd";
	public static final String FORMAT_L_WEEKDAY = "yyyy-MM-dd E";
	public static final String FORMAT_L7 = "yyyyMMddHHmmssSSS";

	/**
	 * 获取当前时间的+多少秒
	 * 精确到秒
	 * @param i
	 * @return
	 */
	public static String getAddDaySecond(int i){
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis()+i*1000);
		return sdfTime.format(date);
	}

	/**
	 * 获取当前时间的YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdfTime.format(new Date());
	}

	/**
	 * 获取当前时间的后i天
	 * 精确到秒
	 * @param i
	 * @return
	 */
	public static String getAddDayTime(int i){
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis()+i*24*60*60*1000);
		return sdfTime.format(date);
	}

	/**
	 * 日期比较，如果s>=e 返回true 否则返回false
	 * @param s
	 * @param e
	 * @return
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return s.compareTo(e)>0;
	}

	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据localDateTime 获取对应的字符串时间
	 * 获取失败后默认返回当前时间的字符串时间
	 * @return
	 */
	public static String getDataStrByLocalDateTime(LocalDateTime localDateTime, String dataRule){
		try {
			if (dataRule.contains("E")){
				return new SimpleDateFormat(dataRule, Locale.CHINA).format (Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant()));
			}
			return new SimpleDateFormat(dataRule).format (Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant()));
		}catch (Exception e){
			e.printStackTrace ();
			return new SimpleDateFormat (dataRule).format (Date.from(LocalDateTime.now ().atZone( ZoneId.systemDefault()).toInstant()));
		}
	}

	/**
	 * 根据localDateTime 获取对应的字符串时间
	 * 获取失败后默认返回当前时间的字符串时间
	 * @return
	 */
	public static LocalDate getLocalDateByDateStr(String dataString, String dataRule){
		try {
			if (dataRule.contains("E")){
				return LocalDate.parse(dataString, DateTimeFormatter.ofPattern(dataRule, Locale.CHINA));
			}
			return LocalDate.parse(dataString, DateTimeFormatter.ofPattern(dataRule));
		}catch (Exception e){
			e.printStackTrace ();
			return LocalDate.now();
		}
	}

	/**
	 * 获取前几日的时间数据
	 * @param howDays
	 * @return
	 */
	public static List<String> getBeforeDateStrByHowDays(Integer howDays){
		List<String> beforeDateList = new ArrayList<String>(  );
		int i = 1;
		while (i <= howDays){
			beforeDateList.add (getDataStrByLocalDateTime(LocalDateTime.now ().plusDays ( -i ), FORMAT_L3));
			i ++ ;
		}
		return beforeDateList;
	}

	/**
	 * 计算时间年份差
	 * @param localDateStart 被减数
	 * @param localDateEnd 减数
	 * @return
	 */
	public static Integer calculationYearDifference(LocalDate localDateStart, LocalDate localDateEnd){
		int years = calculationDifference(localDateStart, localDateEnd).getYears();
		return Integer.valueOf(years);
	}

	/**
	 * 计算时间月份差
	 * @param localDateStart
	 * @param localDateEnd
	 * @return
	 */
	public static Integer calculationMonthDifference(LocalDate localDateStart, LocalDate localDateEnd){
		int months = calculationDifference(localDateStart, localDateEnd).getMonths();
		return Integer.valueOf(months);
	}

    /**
     * String转时间戳
     * @param time
     * @return 时间戳
     * @throws ParseException
     */
	public static Date String2Second(String time) {
		return String2Second(time,FORMAT_L6);
	}

    /**
     * String转时间戳
     * @param time
     * @param pattern
     * @return
     * @throws ParseException
     */
	public static Date String2Second(String time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BusinessException(HttpResultEnum.SYSTEM_RRROR, e.getMessage());
        }
        return date;
	}

	/**
	 * 计算
	 * @param localDateStart
	 * @param localDateEnd
	 * @return
	 */
	private static Period calculationDifference(LocalDate localDateStart, LocalDate localDateEnd){
		return Period.between(localDateStart, localDateEnd);
	}

}
