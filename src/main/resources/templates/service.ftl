package ${packageName}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ${packageName}.entity.${className};
import com.github.pagehelper.PageInfo;
import com.kang.common.dto.AdvancedQueryDto;

import java.util.List;

/**
 * @author K.faWu
 **/
public interface ${className}Service extends IService<${className}> {

    /**
     * 查询方法
     * @param dtoList 查询条件
     * @param pageNum 页码
     * @param pageSize 行数
     * @return 该页数据
     */
    PageInfo<${className}> query(List<AdvancedQueryDto> dtoList, Integer pageNum, Integer pageSize);

    /**
    * 删除数据
    * 在该操作中仅对数据进行逻辑删除，即对{@link com.kang.database.entity.BaseEntity#getDeleteTime()}字段赋值
    * @param ids id数组
    */
    void delete(List<Long> ids);
}
