package com.kang.database.entity;

import lombok.Data;

/**
 * 字段类
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-01-11 14:59
 **/

@Data
public class Column {

    /**
     * 数据库名称
     */
    private String tableSchema;

    /**
     * 数据表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 是否可以为空
     */
    private String isNullable;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段最大长度
     * CHARACTER_MAXIMUM_LENGTH
     */
    private Integer characterMaximumLength;

    /**
     * 数字类型的长度
     * NUMERIC_PRECISION
     */
    private Integer numericPrecision;

    /**
     * 数字刻度，小数位数
     * NUMERIC_SCALE
     */
    private Integer numericScale;

    /**
     * 主键
     */
    private String columnKey;

    /**
     * 字段备注
     * COLUMN_COMMENT
     */
    private String columnComment;
}
