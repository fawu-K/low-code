package com.kang.database.entity;

import com.kang.database.annotation.Field;
import com.kang.database.annotation.FieldType;
import com.kang.database.annotation.Id;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 表格的初始类
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 14:00
 **/
@Data
public class BaseEntity implements Serializable {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 创建者
     */
    @Field(length = 20, comment = "创建者")
    private String creator;

    /**
     * 修改者
     */
    @Field(length = 20, comment = "修改者")
    private String updator;

    /**
     * 创建时间
     */
    @Field(fieldType = FieldType.TIMESTAMP, length = 0, comment = "创建时间")
    private ZonedDateTime created;

    /**
     * 修改时间
     */
    @Field(fieldType = FieldType.TIMESTAMP, length = 0, comment = "修改时间")
    private ZonedDateTime updated;

    /**
     * 是否删除
     */
    @Field(fieldType = FieldType.INT, length = 1, comment = "是否删除：1未删除，0删除")
    private Integer delFlag;

}
