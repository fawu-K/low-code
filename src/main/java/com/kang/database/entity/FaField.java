package com.kang.database.entity;

import com.kang.database.annotation.ACTableField;
import com.kang.database.annotation.ACTableName;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;

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
    @ACTableField(length = 64, comment = "数据表名字")
    private String tableName;

    /**
     * 字段名
     */
    @ACTableField(length = 50, comment = "字段名")
    private String fieldName;

    /**
     * 字段类型
     */
    @ACTableField(length = 50, comment = "字段类型")
    private String fieldType;

    /**
     * 字段长度
     */
    @ACTableField(JdbcType = JdbcType.INTEGER, length = 2, comment = "字段长度")
    private Integer length;

    /**
     * 小数点后位数
     */
    @ACTableField(JdbcType = JdbcType.INTEGER, length = 2, comment = "小数点后位数")
    private Integer decimals;

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
    public FaField(String tableName, String fieldName, ACTableField aCTableField) {
        this.tableName = tableName;
        if ("".equals(aCTableField.value())) {
            this.fieldName = fieldName;
        } else {
            this.fieldName = aCTableField.value();
        }
        this.fieldType = aCTableField.JdbcType().name();
        this.length = aCTableField.length();
        if (aCTableField.numericScale() != null && !aCTableField.numericScale().isEmpty()) {
            this.decimals = Integer.parseInt(aCTableField.numericScale());
        }
        this.isNull = aCTableField.isNull();
        this.isMajorKey = aCTableField.isMajorKey();
        this.comment = aCTableField.comment();
    }
}
