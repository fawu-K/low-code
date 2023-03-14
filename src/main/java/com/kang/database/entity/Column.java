package com.kang.database.entity;

import com.kang.common.util.CommonsUtils;
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
     * 字段类型加字段长度
     */
    private String columnType;

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

    /**
     * 实体类中的字段名
     */
    private String entityColumnName;

    /**
     * 实体类中的字段类型
     */
    private String entityColumnType;

    /**
     * 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
     */
    private String queryType;


    /**
     * 判断是否属于基础字段
     */
    public boolean isSuperColumn() {
        return isSuperColumn(this.columnName);
    }

    /**
     * 判断是否属于基础字段
     */
    public static boolean isSuperColumn(String javaField) {
        return CommonsUtils.strContains(javaField,
                // BaseEntity
                "ID", "CREATOR", "UPDATOR", "CREATED", "UPDATED", "DEL_FLAG");
    }
}
