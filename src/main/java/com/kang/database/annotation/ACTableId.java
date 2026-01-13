package com.kang.database.annotation;

import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.ibatis.type.JdbcType;

import java.lang.annotation.*;

/**
 * {@link ACTableField}注解的特殊形式，主键注解
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 14:00
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@ACTableField(JdbcType = JdbcType.BIGINT, length = "38", isNull = false, isMajorKey = true, comment = "ID")
@TableId
public @interface ACTableId {
}
