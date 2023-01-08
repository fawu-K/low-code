package com.kang.database.entity;

import com.kang.database.interfaces.Field;
import com.kang.database.interfaces.FieldType;
import com.kang.database.interfaces.Id;
import com.kang.database.interfaces.Table;
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

    @Field(value = FieldType.TIMESTAMP, length = 0, comment = "创建时间")
    private ZonedDateTime created;

    @Field(value = FieldType.TIMESTAMP, length = 0, comment = "修改时间")
    private ZonedDateTime updated;

}
