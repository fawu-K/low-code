package ${packageName}.controller;

import ${packageName}.entity.${className};
import ${packageName}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* @author: K.faWu
**/
@RestController
@RequestMapping("/${className?uncap_first}")
public class ${className}Controller {
    @Autowired
    private ${className}Service ${className?uncap_first}Service;
}
