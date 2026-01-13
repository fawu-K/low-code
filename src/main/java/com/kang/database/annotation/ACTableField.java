package com.kang.database.annotation;

import com.baomidou.mybatisplus.annotation.TableField;
import org.apache.ibatis.type.JdbcType;
import org.springframework.core.annotation.AliasFor;

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
@TableField
public @interface ACTableField {
    /**
     * 字段名称，不填写则以实体类的属性名为字段名
     * return
     */
    @AliasFor(annotation = TableField.class, attribute = "value")
    String value() default "";

    /**
     * 字段在数据库中是否存在
     * default: true
     */
    @AliasFor(annotation = TableField.class, attribute = "exist")
    boolean exist() default true;

    /**
     * 表示字段类型
     * default: {@code JdbcType.NULL}
     */
    @AliasFor(annotation = TableField.class, attribute = "jdbcType")
    JdbcType JdbcType() default JdbcType.NULL;

    /**
     * 字段的长度
     * default: 0
     */
    String length() default "";

    /**
     * 数字类型使用，小数点后位数
     */
    @AliasFor(annotation = TableField.class, attribute = "numericScale")
    String numericScale() default "";

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

