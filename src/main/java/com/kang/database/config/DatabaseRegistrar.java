package com.kang.database.config;

import com.kang.EnableAutoDB;
import com.kang.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;


/**
 * 该类通过实现{@link ImportBeanDefinitionRegistrar}接口将包内的bean都注入到spring容器中
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-01-10 11:19
 **/
@Slf4j
public class DatabaseRegistrar extends MapperScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //扫描当前代码包，保证将当前代码包中的bean都注入到spring容器中
        ClassPathBeanDefinitionScanner scanner =
                new ClassPathBeanDefinitionScanner(registry);
        scanner.scan(Constants.PACKAGE_NAME);

        // 获取注解属性
        Map<String, Object> annotationAttributes =
                importingClassMetadata.getAnnotationAttributes(EnableAutoDB.class.getName());

        // 当启动类上带有EnableAutoDB注解时，仅做日志记录
        if (annotationAttributes != null) {
            log.debug("检测到EnableAutoDB注解，自动建表功能已配置");
        }
    }
}
