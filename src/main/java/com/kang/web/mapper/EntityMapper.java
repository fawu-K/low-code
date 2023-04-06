package com.kang.web.mapper;

import com.kang.web.entity.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 持久层接口
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-30 17:13
 **/

@Mapper
public interface EntityMapper {
    List<Entity> queryPage(@Param("tableName") String tableName, @Param("whereSql") String whereSql);
}
