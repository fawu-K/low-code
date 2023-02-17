package com.kang.database.service;

import com.kang.database.vo.FaTableVo;

import java.util.List;
import java.util.Set;

/**
 * 表的服务类，该类仅作用于处理表，不会对表的持久化进行操作
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-01-10 17:25
 **/


public interface TableService {
    /**
     * 从所有类中摘出继承自BaseEntity的类
     * @param classSet
     * @return
     */
    List<Class<?>> classIsBaseEntity(Set<Class<?>> classSet);

    /**
     * 根据传入实体类构建出vo对象
     * @param baseEntity 实体类
     * @param tableName 表名
     * @return vo对象
     */
    FaTableVo entityToVo(String tableName, Class<?> baseEntity);

    /**
     * 从实体类中获取他们所对应的表格信息
     * @param classes 实体类
     * @return 表格名称
     */
    List<String> entityToTable(List<Class<?>> classes);

    /**
     * 从实体类中获取他所对应的表格信息
     * @param clazz 实体类
     * @return 表格名称
     */
    String entityToTable(Class<?> clazz);
}
