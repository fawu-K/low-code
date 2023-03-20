package ${packageName}.entity;

<#if importClassList?? && (importClassList?size > 0) >
    <#list importClassList as importClass>
import ${importClass} ;
    </#list>
</#if>
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import com.kang.database.entity.BaseEntity;
@Data
public class ${className} extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

<#if columns??>
<#--循环生成变量-->
    <#list columns as col>
    /**
     * ${col["columnComment"]}
     */
    private ${col["entityColumnType"]} ${col["entityColumnName"]};
    </#list>

</#if>
}
