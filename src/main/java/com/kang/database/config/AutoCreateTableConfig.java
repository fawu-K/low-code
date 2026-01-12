package com.kang.database.config;

import com.kang.database.enums.ACTableStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 自动创建数据库表配置
 *
 * @author <a href="https://github.com/fawu-K">fawu.K</a>
 * @since 2026-01-12 13:35
 **/

@Configuration
@Getter
@Component
public class AutoCreateTableConfig {

    /**
     * 是否启用自动建表
     */
    @Value("${fawu.table.auto-create.enable:true}")
    private boolean enable;

    /**
     * 创建数据库表的策略
     */
    @Value("${fawu.table.auto-create.strategy:ADD}")
    private ACTableStrategy strategy;

    /**
     * 表前缀
     */
    @Value("${fawu.table.auto-create.table-prefix:}")
    private String tablePrefix;

    /**
     * 字段前缀
     */
    @Value("${fawu.table.auto-create.field-prefix:}")
    private String fieldPrefix;

    /**
     * 扫描包名
     */
    @Value("${fawu.table.auto-create.entity-package:}")
    private String entityPackage;

}
