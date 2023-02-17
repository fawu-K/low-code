package ${packageName}.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packageName}.entity.${className};
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author: K.faWu
**/
@Service
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements ${className}Service {
    @Autowired
    private ${className}Mapper ${className?uncap_first}Mapper;
}
