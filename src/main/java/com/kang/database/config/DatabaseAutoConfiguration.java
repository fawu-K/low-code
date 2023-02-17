package com.kang.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 自动装配类
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-09 17:25
 **/
@Configuration
@Import(DatabaseRegistrar.class)
public class DatabaseAutoConfiguration {
}
