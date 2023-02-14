package com.kang.database.annotation;

import java.lang.annotation.*;

/**
 * {@link com.kang.database.annotation.Field}注解的特殊形式，主键注解
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 14:00
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Field(fieldType = FieldType.BIGINT, length = 38, isNull = false, isMajorKey = true, comment = "ID")
public @interface Id{
}
