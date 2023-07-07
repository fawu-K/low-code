package com.kang.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具处理类
 *
 * @author <a href="https://github.com/fawu-K">fawu.K</a>
 * @since 2023-05-16 16:08
 **/

public class DateUtil {

    /**
     * 获取当前时间，以yyyy-MM-dd HH:mm:ss字符串形式
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateIsStr() {
        return dateToStr(new Date());
    }

    /**
     * 将{@link Date}格式时间换成yyyy-MM-dd HH:mm:ss字符串格式
     *
     * @param date 指定时间
     * @return 时间的字符串格式
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前月份, yyyy-MM格式
     *
     * @return yyyy-MM
     */
    public static String getNowYearMonth() {
        return dateToStr(new Date(), "yyyy-MM");
    }

    /**
     * 获取当前年份, yyyy格式
     *
     * @return yyyy
     */
    public static String getNowYear() {
        return dateToStr(new Date(), "yyyy");
    }


    /**
     * 输出指定格式的时间
     *
     * @param date    时间
     * @param pattern 格式
     * @return 时间的字符串格式
     */
    public static String dateToStr(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 字符串转{@link Date}格式
     *
     * @param str 指定时间
     * @return {@link Date}
     * @throws ParseException 输入字符串格式错误
     */
    public static Date strToDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(str);
    }
}
