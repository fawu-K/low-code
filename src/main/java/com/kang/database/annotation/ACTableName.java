package com.kang.database.annotation;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.core.annotation.AliasFor;

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
@TableName
public @interface ACTableName {

    /**
     * 指定表名，若不指定则使用实体类名作为数据表名
     * @return 表名
     */
    @AliasFor(annotation = TableName.class, attribute = "value")
    String value() default "";


    /**
     * 是否保持使用全局的 tablePrefix 的值
     * <p> 只生效于 既设置了全局的 tablePrefix 也设置了上面 {@link #value()} 的值 </p>
     * <li> 如果是 false , 全局的 tablePrefix 不生效 </li>
     *
     * @since 3.1.1
     */
    @AliasFor(annotation = TableName.class, attribute = "keepGlobalPrefix")
    boolean keepGlobalPrefix() default true;
}
