package com.kang.database.entity;

import com.kang.common.type.DatabaseFieldType;
import com.kang.common.util.CommonsUtils;
import com.kang.database.annotation.ACTableField;
import com.kang.database.annotation.ACTableName;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 字段类
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 14:23
 **/
@Data
@ACTableName
public class FaField extends BaseEntity implements Serializable {

    /**
     * 所属的表名
     */
    @ACTableField(length = "64", comment = "数据表名字")
    private String tableName;

    /**
     * 字段名
     */
    @ACTableField(length = "50", comment = "字段名")
    private String fieldName;

    /**
     * 字段类型
     */
    @ACTableField(length = "50", comment = "字段类型")
    private DatabaseFieldType fieldType;

    /**
     * 字段长度
     */
    @ACTableField(JdbcType = JdbcType.INTEGER, length = "4", comment = "字段长度")
    private String length;

    /**
     * 是否为空
     */
    @ACTableField(comment = "是否为空")
    private Boolean isNull;

    /**
     * 是否为主键
     */
    @ACTableField(comment = "是否为空")
    private Boolean isMajorKey;

    /**
     * 备注
     */
    @ACTableField(comment = "备注")
    private String comment;

    /**
     * 构造方法
     */
    public FaField(){}

    /**
     * 构造方法
     */
    public FaField(String tableName, Field field, ACTableField aCTableField) {
        this.tableName = tableName;
        if ("".equals(aCTableField.value())) {
            this.fieldName = CommonsUtils.humpToLine(field.getName());
        } else {
            this.fieldName = aCTableField.value();
        }

        String length = aCTableField.length();
        if (aCTableField.numericScale() != null && !aCTableField.numericScale().isEmpty()) {
            length += "," + aCTableField.numericScale();
        }

        this.setFieldTypeAndLength(field, aCTableField.JdbcType(), length);

        this.isNull = aCTableField.isNull();
        this.isMajorKey = aCTableField.isMajorKey();
        this.comment = aCTableField.comment();
    }

    public FaField(String tableName, Field field) {
        this.tableName = tableName;
        this.fieldName = field.getName();
        this.setFieldTypeAndLength(field, null, null);
        this.isNull = true;
        this.isMajorKey = false;

    }

    public void setFieldTypeAndLength(Field field, JdbcType jdbcType, String length) {
        Class<?> fieldClass = field.getType();
        if (fieldClass.equals(int.class) || fieldClass.equals(Integer.class)) {
            this.fieldType = DatabaseFieldType.INT;
            this.length = length == null || length.isEmpty() ? this.fieldType.getLength() : length;
        } else if (fieldClass.equals(long.class) || fieldClass.equals(Long.class)) {
            this.fieldType = DatabaseFieldType.BIGINT;
            this.length = length == null || length.isEmpty() ? this.fieldType.getLength() : length;
        } else if (fieldClass.equals(double.class) || fieldClass.equals(Double.class)) {
            this.fieldType = DatabaseFieldType.FLOAT;
            this.length = length == null || length.isEmpty() ? this.fieldType.getLength() : length;
        } else if (fieldClass.equals(float.class) || fieldClass.equals(Float.class)) {
            this.fieldType = DatabaseFieldType.FLOAT;
            this.length = length == null || length.isEmpty() ? this.fieldType.getLength() : length;
        } else if (fieldClass.equals(BigDecimal.class)) {
            this.fieldType = DatabaseFieldType.DECIMAL;
            this.length = length == null || length.isEmpty() ? this.fieldType.getLength() : length;
        } else if (fieldClass.equals(boolean.class) || fieldClass.equals(Boolean.class)) {
            this.fieldType = DatabaseFieldType.TINYINT;
            this.length = this.fieldType.getLength();
        } else if (fieldClass.equals(Date.class) || fieldClass.equals(java.sql.Date.class) || fieldClass.equals(Timestamp.class) || fieldClass.equals(ZonedDateTime.class)) {
            this.fieldType = DatabaseFieldType.TIMESTAMP;
            this.length = this.fieldType.getLength();
        } else {
            this.fieldType = DatabaseFieldType.VARCHAR;
            this.length = length == null || length.isEmpty() ? this.fieldType.getLength() : length;
        }

        if (jdbcType != null && jdbcType != JdbcType.NULL) {
            this.fieldType = jdbcTypeMapping.getOrDefault(jdbcType, DatabaseFieldType.VARCHAR);
        }
    }

    private static Map<JdbcType, DatabaseFieldType> jdbcTypeMapping = new HashMap<>();

    static {
        // 字符串
        jdbcTypeMapping.put(JdbcType.VARCHAR, DatabaseFieldType.VARCHAR);
        jdbcTypeMapping.put(JdbcType.NVARCHAR, DatabaseFieldType.NVARCHAR);
        jdbcTypeMapping.put(JdbcType.CHAR, DatabaseFieldType.VARCHAR);
        // 长文本
        jdbcTypeMapping.put(JdbcType.LONGVARCHAR, DatabaseFieldType.LONGTEXT);
        jdbcTypeMapping.put(JdbcType.LONGNVARCHAR, DatabaseFieldType.LONGTEXT);
        // 数字
        jdbcTypeMapping.put(JdbcType.INTEGER, DatabaseFieldType.INT);
        jdbcTypeMapping.put(JdbcType.BIGINT, DatabaseFieldType.BIGINT);
        jdbcTypeMapping.put(JdbcType.FLOAT, DatabaseFieldType.FLOAT);
        jdbcTypeMapping.put(JdbcType.DOUBLE, DatabaseFieldType.FLOAT);
        jdbcTypeMapping.put(JdbcType.DECIMAL, DatabaseFieldType.DECIMAL);
        // 布尔
        jdbcTypeMapping.put(JdbcType.BOOLEAN, DatabaseFieldType.TINYINT);
        jdbcTypeMapping.put(JdbcType.TINYINT, DatabaseFieldType.TINYINT);
        // 时间
        jdbcTypeMapping.put(JdbcType.DATE, DatabaseFieldType.DATE);
        jdbcTypeMapping.put(JdbcType.TIME, DatabaseFieldType.TIMESTAMP);
        jdbcTypeMapping.put(JdbcType.TIMESTAMP, DatabaseFieldType.TIMESTAMP);
        // 二进制
        jdbcTypeMapping.put(JdbcType.BLOB, DatabaseFieldType.BLOB);
        jdbcTypeMapping.put(JdbcType.BINARY, DatabaseFieldType.BLOB);
    }
}
