package com.kang.database.annotation;

import java.lang.annotation.*;

/**
 * 该注解表明此类为数据表的实体类，需要判断数据库中是否有该数据表
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 13:45
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Table {

    /**
     * 指定表名，若不指定则使用实体类名作为数据表名
     * @return
     */
    String name() default "";
}
