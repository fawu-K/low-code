package com.kang.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.kang.common.dto.AdvancedQueryDto;
import com.kang.common.util.WrapperUtil;
import com.kang.web.entity.Entity;
import com.kang.web.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 接口
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-30 17:16
 **/
@RestController("{tableName}")
public class EntityController {
    @Autowired
    private EntityService entityService;

    /**
     * 查询接口，使用POST请求方式，分页通过url进行传值。
     * 查询条件以json的形式进行接收，以{@link AdvancedQueryDto}类作为查询条件的模板，
     * 其中{@link AdvancedQueryDto#getOperation()}的值是该字段在引入{@link QueryWrapper}时调用的方法
     * 可支持的方法名可在{@link WrapperUtil#queryByDto(QueryWrapper, AdvancedQueryDto)}中看到，后续有可能更新。
     *
     * @param dtoList  查询条件
     * @param pageNum  页码
     * @param pageSize 行数
     * @return 查询结果
     */
    @PostMapping("query")
    public ResponseEntity<?> query(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                   @RequestBody List<AdvancedQueryDto> dtoList,
                                   @PathVariable String tableName) {
        PageInfo<Entity> pageInfo = entityService.query(dtoList, tableName, pageNum, pageSize);
        return ResponseEntity.ok().body(pageInfo);
    }

}
