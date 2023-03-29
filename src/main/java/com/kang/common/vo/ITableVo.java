package com.kang.common.vo;

import com.kang.common.type.ClassType;
import com.kang.database.entity.Column;
import lombok.Data;

import java.util.List;

/**
 * 数据表的接口类
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-29 14:37
 **/

@Data
public abstract class ITableVo implements ClassType {

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

    /**
     * 属性
     */
    private List<Column> columns;

    /**
     * 需要导入的包
     */
    private List<String> importClassList;

}
