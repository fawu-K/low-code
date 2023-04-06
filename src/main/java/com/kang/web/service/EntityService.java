package com.kang.web.service;

import com.github.pagehelper.PageInfo;
import com.kang.common.dto.AdvancedQueryDto;
import com.kang.web.entity.Entity;

import java.util.List;

/**
 * @author K.faWu
 * @program low-code
 * @date 2023-03-30 17:18
 **/


public interface EntityService {
    PageInfo<Entity> query(List<AdvancedQueryDto> dtoList, String tableName, Integer pageNum, Integer pageSize);
}
