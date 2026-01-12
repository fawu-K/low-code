package com.kang.common.constant;

/**
 * @author K.faWu
 * @program low-code
 * @date 2023-02-17 10:44
 **/

public class Constants {

    /**
     * 该项目的默认包名
     */
    public final static String PACKAGE_NAME = "com.kang";

    public final static String ENTITY_PACKAGE_NAME = "com.kang.**.entity";

    /**
     * 启动类名称系统存储key
     */
    public final static String START_CLASS_NAME = "startAppClassName";

    /**
     * 启动类包名系统存储key
     */
    public final static String START_CLASS_PACKAGE = "startClassPackage";

    /**
     * idea生成的maven项目的class文件所在包
     */
    public final static String TARGET_CLASSES = "target/classes/";

    /**
     * 框架模板存放路径
     */
    public final static String SYS_TEMPLATES_PATH = "target/classes/templates/";

    /**
     * idea下的maven项目的文件所在包
     */
    public final static String SRC_MAIN_PATH = "src/main/";

    /**
     * idea下的maven项目的java文件所在包
     */
    public final static String SRC_MAIN_JAVA_PATH = SRC_MAIN_PATH + "java/";


    /**
     * 等于
     */
    public final static String EQ = "eq";

    /**
     * 不等于
     */
    public final static String NE = "ne";

    /**
     * 大于
     */
    public final static String GT = "gt";

    /**
     * 大于等于
     */
    public final static String GE = "ge";

    /**
     * 小于
     */
    public final static String LT = "lt";

    /**
     * 小于等于
     */
    public final static String LE = "le";

    /**
     * 存在
     */
    public final static String LIKE = "like";

    /**
     * 不存在
     */
    public final static String NOT_LIKE = "notLike";

    /**
     * 字段为空
     */
    public final static String IS_NULL = "isNull";

    /**
     * 字段不为空
     */
    public final static String IS_NOT_NULL = "isNotNull";


}
