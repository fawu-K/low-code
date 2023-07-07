package com.kang.database.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZonedDateTime;

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
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            //自动添加
            @Override
            public void insertFill(MetaObject metaObject) {
                //第二参数要和实体类中字段名一致，第三个参数字段类型要和实体类中字段类型一致，最后一个参数是待填入的数据
                this.strictInsertFill(metaObject, "creator", String.class, "创建者");
                this.strictInsertFill(metaObject, "created", ZonedDateTime.class, ZonedDateTime.now());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "updator", String.class, "修改者");
                this.strictUpdateFill(metaObject, "updated", ZonedDateTime.class, ZonedDateTime.now());
            }
        };
    }

}
