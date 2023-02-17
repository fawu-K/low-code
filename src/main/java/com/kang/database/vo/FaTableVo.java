package com.kang.database.vo;

import com.kang.database.entity.Column;
import com.kang.database.entity.FaField;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 13:56
 **/

@Data
@Builder
public class FaTableVo implements Serializable {

    private String tableName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 生成文件需要保存的全路径
     */
    private String path;

    /**
     * 注释
     */
    private String comment;

    /**
     * 作者
     */
    private String author;

    private List<FaField> fields;

    /**
     * 属性
     */
    private List<Column> columns;

    /**
     * 需要导入的包
     */
    private List<String> importClassList;
}
