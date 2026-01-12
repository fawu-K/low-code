package com.kang.database.entity;

import com.kang.database.annotation.ACTableField;
import com.kang.database.annotation.ACTableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据库表
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 11:05
 **/

@Data
@Builder
@ACTableName
public class FaTable extends BaseEntity implements Serializable {

    /**
     * 表名
     */
    @ACTableField(length = 64, comment = "表名")
    private String tableName;

    /**
     * 包名
     */
    @ACTableField(comment = "包名")
    private String packageName;

    /**
     * 类名
     */
    @ACTableField(length = 64, comment = "类名")
    private String className;

    /**
     * 生成文件需要保存的全路径
     */
    @ACTableField(comment = "生成文件需要保存的全路径")
    private String path;
}
