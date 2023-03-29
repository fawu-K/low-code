package com.kang.freeMarker.config;

import com.kang.common.constant.FtlConstants;
import com.kang.common.type.Type;
import com.kang.common.util.CommonsUtils;
import com.kang.common.vo.ITableVo;
import com.kang.database.entity.Column;
import com.kang.database.mapper.DatabaseMapper;
import com.kang.freeMarker.FreeMarkerTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 该方法用来对tableVo类进行赋值。
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-28 11:04
 **/

@Component
public class TableVoProperty implements ITableVoProperty {

    @Autowired
    private DatabaseMapper databaseMapper;

    @Override
    public Type getType() {
        return Type.SYSTEM;
    }

    /**
     * 对tableVo的属性进行封装。
     *
     * @param itableVo    需要赋值的tableVo
     * @param tableName   数据表名
     * @param packageName 要存放的包名
     */
    @Override
    public void setProperty(ITableVo itableVo, String tableName, String packageName) {
        String entityName = CommonsUtils.getEntityName(tableName);
        itableVo.setPackageName(packageName);
        itableVo.setClassName(entityName);
        itableVo.setColumns(this.getColumn(tableName));
        itableVo.setTableName(tableName);
        getImportList(itableVo);
    }

    /**
     * 获取该实体类中的所有字段且不包含父类的类
     *
     * @param tableName 表名
     * @return 列属性
     */
    public List<Column> getColumn(String tableName) {
        List<Column> columns = databaseMapper.getTableFields(tableName);
        columns = columns.stream().filter(FreeMarkerTools::initNotSuperColumns).collect(Collectors.toList());
        return columns;
    }

    /**
     * 根据列类型获取导入包
     *
     * @param iTableVo 业务表对象
     */
    public static void getImportList(ITableVo iTableVo) {
        List<Column> columns = iTableVo.getColumns();
        List<String> importList = new ArrayList<>();
        for (Column column : columns) {
            if (!column.isSuperColumn() && FtlConstants.TYPE_DATE.equals(column.getEntityColumnType())) {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (!column.isSuperColumn() && FtlConstants.TYPE_BIGDECIMAL.equals(column.getEntityColumnType())) {
                importList.add(FtlConstants.BIGDECIMAL_PACKAGE);
            }
        }
        iTableVo.setImportClassList(importList);
    }


}
