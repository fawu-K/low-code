package com.kang.database.annotation;

/**
 * {@link Field}的value值使用枚举
 * @author qawzf
 */

public enum FieldType {
    /**
     * 字符串类型
     */
    VARCHAR,
    /**
     * 数字类型
     */
    INT,
    /**
     * 长数字类型
     */
    BIGINT,
    /**
     * 小数类型
     */
    DECIMAL,
    /**
     * 长文本类型，长度不做限制
     */
    LONGTEXT,
    /**
     * 时间类型
     */
    DATE,
    /**
     * 时间戳类型
     */
    TIMESTAMP
}
