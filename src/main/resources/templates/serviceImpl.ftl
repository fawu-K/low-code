package ${packageName}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packageName}.entity.${className};
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.service.${className}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kang.common.dto.AdvancedQueryDto;
import com.kang.common.util.WrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author K.faWu
 **/
@Service
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements ${className}Service {
    @Autowired
    private ${className}Mapper ${className?uncap_first}Mapper;


    @Override
    public PageInfo<${className}> query(List<AdvancedQueryDto> dtoList, Integer pageNum, Integer pageSize) {
        //参数转换
        QueryWrapper<${className}> wrapper = WrapperUtil.queryByDto(new QueryWrapper<>(), dtoList);
        //分页
        PageHelper.startPage(pageNum, pageSize);
        List<${className}> list = ${className?uncap_first}Mapper.selectList(wrapper);
        PageInfo<${className}> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void delete(List<Long> ids) {
        ${className?uncap_first}Mapper.deleteByIds(ids);
    }
}
