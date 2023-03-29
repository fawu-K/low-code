package com.kang.database.service;

import com.kang.common.vo.impl.FaTableVo;

import java.util.List;

/**
 * 数据库持久化
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 13:52
 **/


public interface DatabaseService {

    /**
     * 创建数据表操作，根据表名以及字段创建数据表
     * @param faTableVo 表格vo对象
     */
    void newTable(FaTableVo faTableVo);

    /**
     * 判断该表是否存在
     * @param entityName 表名
     * @return 判断结果
     */
    boolean isHaveTable(String entityName);

    /**
     * 对表进行生成
     */
    void saveTable(List<Class<?>> tables);

    /**
     * 对表格的字段进行更新操作。
     * 注意：一般情况下字段长度不应改小，因之前已经存在的数据有可能会超出长度
     */
    boolean updateTableField(FaTableVo faTableVo);

    /**
     * 判断表格中的字段是否和实体类中的字段一致
     */
    boolean isTableToEntityField(FaTableVo faTableVo);

    /**
     * 获取数据表的所有实体类化信息
     */
    List<FaTableVo> getEntityToTable();

}
