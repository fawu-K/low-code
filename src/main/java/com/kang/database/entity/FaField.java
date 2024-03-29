package com.kang.database.entity;

import com.kang.common.type.FieldType;
import com.kang.database.annotation.Field;
import com.kang.database.annotation.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * 字段类
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 14:23
 **/
@Data
@Table
public class FaField extends BaseEntity implements Serializable {

    /**
     * 所属的表名
     */
    @Field(length = 64, comment = "数据表名字")
    private String tableName;

    /**
     * 字段名
     */
    @Field(length = 50, comment = "字段名")
    private String fieldName;

    /**
     * 字段类型
     */
    @Field(length = 50, comment = "字段类型")
    private String fieldType;

    /**
     * 字段长度
     */
    @Field(fieldType = FieldType.INT, length = 2, comment = "字段长度")
    private Integer length;

    /**
     * 小数点后位数
     */
    @Field(fieldType = FieldType.INT, length = 2, comment = "小数点后位数")
    private Integer decimals;

    /**
     * 是否为空
     */
    @Field(comment = "是否为空")
    private Boolean isNull;

    /**
     * 是否为主键
     */
    @Field(comment = "是否为空")
    private Boolean isMajorKey;

    /**
     * 备注
     */
    @Field(comment = "备注")
    private String comment;

    /**
     * 构造方法
     */
    public FaField(){}

    /**
     * 构造方法
     */
    public FaField(String tableName, String fieldName, Field field) {
        this.tableName = tableName;
        if ("".equals(field.name())) {
            this.fieldName = fieldName;
        } else {
            this.fieldName = field.name();
        }
        this.fieldType = field.fieldType().name();
        this.length = field.length();
        this.decimals = field.decimal();
        this.isNull = field.isNull();
        this.isMajorKey = field.isMajorKey();
        this.comment = field.comment();
    }
}
