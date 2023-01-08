package com.kang.database;

import java.lang.annotation.*;

/**
 * 放在启动类上的注解
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 15:33
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EnableAutoDB {
}
