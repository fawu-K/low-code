package com.kang.freeMarker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 自动生成代码的配置类
 * 使用者通过该类的属性来决定生成的实体类应该存在的位置
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-10 13:48
 **/

@Component
public class FreeMarkerConfig {

    /**
     * 模板包路径
     */
    private static String templatePath;

    /**
     * 获取模板包路径
     */
    public static String getTemplatePath(){
        return templatePath;
    }

    /**
     * 开发者可以使用自定义的模板，但当模板放在非默认的文件夹下时需要开发者在配置文件中表明模板包路径。
     * 当然，该模板包需要在resources包下。
     * @param templatePath 模板路径
     */
    @Value("${kang.low-code.template-path:templates/}")
    private void setTemplatePath(String templatePath) {
        FreeMarkerConfig.templatePath = templatePath;
    }

}
