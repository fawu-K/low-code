package com.kang.database.mapper;

import com.kang.database.entity.Column;
import com.kang.database.vo.FaTableVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 14:20
 **/

@Mapper
public interface DatabaseMapper {

    /**
     * 查询数据库是否存在该表
     * @param tableName 表名
     * @return 1表示有，0表示没有
     */
    int isTable(String tableName);

    void newTable(@Param("tableVo") FaTableVo tableVo);

    /**
     * 获取表的所有字段
     * @param tableName 数据表名
     * @return 所有字段
     */
    List<Column> getTableFields(String tableName);
}
