package com.kang.database.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 利用{@link org.mybatis.spring.annotation.MapperScan}将包内的持久层注入spring容器中
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-01-10 14:49
 **/
@Configuration
@EnableTransactionManagement(order = 2)
@MapperScan(value = "com.kang.database.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件。如果你不配置，分页插件将不生效
     */
/*    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 指定数据库方言为 MYSQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }*/
}
