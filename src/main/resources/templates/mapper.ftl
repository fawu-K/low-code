package ${packageName}.mapper;

<#if importClassList?? && (importClassList?size > 0) >
    <#list importClassList as importClass>
        import ${importClass} ;
    </#list>
</#if>
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packageName}.entity.${className};
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ${className}Mapper extends BaseMapper<${className}> {
}
