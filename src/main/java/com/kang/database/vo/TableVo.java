package com.kang.database.vo;

import com.kang.database.annotation.Type;
import com.kang.database.entity.Column;
import lombok.Data;

import java.util.List;

/**
 * 表视图的接口类
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-13 09:36
 **/

@Data
public class TableVo {

    /**
     * 当用户在实现该接口的时候，表示为用户自定义的class
     * @return
     */
    public Type getType() {
        return Type.CUSTOM;
    }

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
