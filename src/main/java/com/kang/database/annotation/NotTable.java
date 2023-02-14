package com.kang.database.annotation;

import java.lang.annotation.*;

/**
 * 表示该类非数据表
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 16:07
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotTable {
}
