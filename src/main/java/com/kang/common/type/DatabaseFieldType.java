package com.kang.common.type;

import com.kang.database.annotation.ACTableField;
import lombok.Getter;

/**
 * {@link ACTableField}的value值使用枚举
 *
 * @author qawzf
 */

public enum DatabaseFieldType {
    /**
     * 字符串类型
     */
    VARCHAR("255"),

    NVARCHAR("255"),

    /**
     * 长字符串类型
     */
    LONGTEXT(""),

    /**
     * 数字类型
     */
    INT("16"),
    /**
     * 长数字类型
     */
    BIGINT("20"),

    /**
     * 浮点类型
     */
    FLOAT("20,4"),
    /**
     * 小数类型
     */
    DECIMAL("20,4"),

    /**
     * 布尔类型
     */
    TINYINT("1"),
    /**
     * 时间类型
     */
    DATE(""),
    /**
     * 时间戳类型
     */
    TIMESTAMP(""),
    BLOB("");

    @Getter
    private final String length;

    DatabaseFieldType(String length) {
        this.length = length;
    }
}
