package com.kang.database.annotation;

import com.kang.common.type.FieldType;

import java.lang.annotation.*;

/**
 * 该注解表示当前字段为表格字段
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 11:09
 **/

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Field {
    /**
     * 字段名称，不填写则以实体类的属性名为字段名
     * return
     */
    String name() default "";

    /**
     * 表示字段类型
     * default: {@code Type.VARCHAR}
     */
    FieldType fieldType() default FieldType.VARCHAR;

    /**
     * 字段的长度
     * default: 255
     */
    int length() default 255;

    /**
     * 数字类型使用，小数点后位数，
     * default: 0
     */
    int decimal() default 0;

    /**
     * 是否为空
     * default: true
     */
    boolean isNull() default true;

    /**
     * 是否为主键
     * default: false
     */
    boolean isMajorKey() default false;

    /**
     * 注释
     */
    String comment() default "";
}

