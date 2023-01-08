package com.kang.database.service;

import com.kang.database.entity.BaseEntity;
import com.kang.database.vo.FaTableVo;

import java.util.List;
import java.util.Set;

/**
 * 数据库的服务接口在此接口下进行构建数据表等操作
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
     * 根据传入实体类构建出vo对象
     * @param baseEntity 实体类
     * @return vo对象
     */
    FaTableVo entityToVo(String tableName, Class<?> baseEntity);

    /**
     * 判断该表是否存在
     * @param entityName 表名
     * @return 判断结果
     */
    boolean isHaveTable(String entityName);

    /**
     * 对表进行生成
     * @param tables
     */
    void saveTable(List<Class<?>> tables);

    /**
     * 从所有类中摘出继承自BaseEntity的类
     * @param classSet
     * @return
     */
    List<Class<?>> classIsBaseEntity(Set<Class<?>> classSet);
}
