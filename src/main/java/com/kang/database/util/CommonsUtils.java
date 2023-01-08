package com.kang.database.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: service
 * @description:
 * @author: K.faWu
 * @create: 2022-06-10 14:18
 **/

public class CommonsUtils {

    /**
     * 判断对象是否为空
     * @param obj 需要判断的对象
     * @return 是否为空
     */
    public static Boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        //当是集合的时候
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).size() == 0;
        }
        //当是string的时候
        if (obj instanceof String) {
            return "".equals(obj);
        }
        return false;
    }

    /**
     * 判断是否不为空
     * @param obj 需要判断的对象
     * @return 是否不为空
     */
    public static Boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线,最后转为大写
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toUpperCase();
    }

    /**
     * 下划线转驼峰,正常输出
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String topCharSmall(String str){
        //如果字符串str为null和""则返回原数据
        if (str==null||"".equals(str)){
            return str ;
        }

        if(str.length()==1){
            //如果字符串str的长度为1，则调用专门把字符串转换为小写的string方法tuUpperCase()
            return str.toLowerCase() ;
        }
        //用字符串截取方法subString()截取第一个字符并调用toUpperCase()方法把它转换为小写字母
        //再与从str第二个下标截取的字符串拼接
        return str.substring(0,1).toLowerCase()+str.substring(1) ;
    }

}
