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
    @Id
    private Long id;

    @Field(length = 20, comment = "创建者")
    private String creator;

    @Field(length = 20, comment = "修改者")
    private String updator;

    @Field(fieldType = FieldType.TIMESTAMP, length = 0, comment = "创建时间")
    private ZonedDateTime created;

    @Field(fieldType = FieldType.TIMESTAMP, length = 0, comment = "修改时间")
    private ZonedDateTime updated;

    @Field(fieldType = FieldType.INT, length = 1, comment = "是否删除：1未删除，0删除")
    private Integer delFlag;

}
