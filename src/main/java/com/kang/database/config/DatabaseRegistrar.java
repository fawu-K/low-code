package com.kang.database.config;

import com.kang.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;


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
        //扫描类
        ClassPathBeanDefinitionScanner scanner =
                new ClassPathBeanDefinitionScanner(registry);
        scanner.scan(Constants.PACKAGE_NAME);
    }
}
