package com.kang.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kang.common.dto.AdvancedQueryDto;
import com.kang.common.util.WrapperUtil;
import com.kang.web.entity.Entity;
import com.kang.web.mapper.EntityMapper;
import com.kang.web.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author K.faWu
 * @program low-code
 * @date 2023-03-30 17:18
 **/
@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public PageInfo<Entity> query(List<AdvancedQueryDto> dtoList, String tableName, Integer pageNum, Integer pageSize) {
        //参数转换
        String whereSql = WrapperUtil.dtoToSql(dtoList);
        //分页
        PageHelper.startPage(pageNum, pageSize);
        List<Entity> list = entityMapper.queryPage(tableName, whereSql);
        return new PageInfo<>(list);
    }
}
