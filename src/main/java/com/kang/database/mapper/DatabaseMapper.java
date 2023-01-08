package com.kang.database.mapper;

import com.kang.database.vo.FaTableVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
