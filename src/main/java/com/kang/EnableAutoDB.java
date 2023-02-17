package com.kang;

import com.kang.database.config.DatabaseRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 放在启动类上的注解
 * 默认开启实体类生成数据表，可手动进行关闭
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 15:33
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(DatabaseRegistrar.class)
public @interface EnableAutoDB {
    /**
     * 是否开启实体类生成数据表
     * @return true 开启
     */
    boolean entityToTable() default true;

    /**
     * 是否开启数据表转实体类
     * @return false 不开启
     */
    boolean tableToEntity() default false;
}
