package ${packageName}.mapper;

<#if importClassList?? && (importClassList?size > 0) >
    <#list importClassList as importClass>
        import ${importClass} ;
    </#list>
</#if>
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packageName}.entity.${className};
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ${className}dao层
 * @author K.faWu
 **/
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}> {

    /**
     * 根据id对数据进行逻辑删除
     * @param ids 要删除的数据id
     */
    void deleteByIds(List<Long> ids);
}
