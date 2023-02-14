package com.kang.database.entity;

import com.kang.database.annotation.Field;
import com.kang.database.annotation.Table;
import lombok.Data;

/**
 * 数据库表
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 11:05
 **/

@Data
@Table
public class FaTable extends BaseEntity {

    @Field(length = 64, comment = "表名")
    private String name;
}
