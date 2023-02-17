package com.kang.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
     * 驼峰转下划线（首字母大写不转下划线
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        str = topCharSmall(str);
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
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

    /**
     * 首字母小写
     * @param str
     * @return
     */
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
        return str.substring(0,1).toLowerCase() + str.substring(1) ;
    }

    /**
     * 首字母大写
     */
    public static String topCharBig(String str){
        //如果字符串str为null和""则返回原数据
        if (str==null||"".equals(str)){
            return str ;
        }

        if(str.length()==1){
            //如果字符串str的长度为1，则调用专门把字符串转换为大写的string方法tuUpperCase()
            return str.toUpperCase() ;
        }

        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    /**
     * 通过表名获取实体类名
     * 即输入的为带下划线的表名，输出为首字母大写，且驼峰的实体类名称
     * @param str
     * @return
     */
    public static String getEntityName(String str) {
        return lineToHump(topCharBig(str));
    }

    /**
     * 校验数组是否包含指定值
     * @param targetValue 值
     * @param args 多个数组
     * @return 是否包含
     */
    public static boolean strContains(String targetValue, String[] ...args) {
        List<String> list = new ArrayList<>();
        for (String[] arg : args) {
            list.addAll(Arrays.asList(arg));
        }
        return strContains(targetValue, list);
    }

    /**
     * 校验数组是否包含指定值
     * @param targetValue 值
     * @param args 数组
     * @return 是否包含
     */
    public static boolean strContains(String targetValue, String ...args) {
        return strContains(targetValue, Arrays.asList(args));
    }

    /**
     * 校验集合是否包含指定值
     * @param targetValue 值
     * @param list 集合
     * @return 是否包含
     */
    public static boolean strContains(String targetValue, List<String> list) {
        return list.contains(targetValue);
    }
}
