package com.kang.database.service;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kang.common.constant.Constants;
import com.kang.common.util.PackageScanner;
import com.kang.database.config.AutoCreateTableConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 自动建表服务类
 *
 * @author <a href="https://github.com/fawu-K">fawu.K</a>
 * @since 2026-01-12 11:29
 **/

@Service
@Slf4j
public class ACTableService {

    @Resource
    private TableService tableService;

    @Resource
    private DatabaseService databaseService;

    @Resource
    private AutoCreateTableConfig autoCreateTableConfig;

    @Async
    public void saveTable(String mainPackage) {
        log.debug("开始实现通过实体类建表的动作");

        Set<Class<?>> classes = new HashSet<>();

        // entityPackage可能会分号分割多个，需要处理
        String entityPackage = autoCreateTableConfig.getEntityPackage();
        if (entityPackage != null && !entityPackage.isEmpty()) {
            String[] entityPackages = entityPackage.split("[;；]");
            String[] packagePatterns = parsePackagePatterns(entityPackages);
            // 获取指定包下的所有类
            classes = PackageScanner.scanPackages(packagePatterns);
        }

        // 获取启动类所在包下所有所有带有@TableName注解的类，以及框架内所有带有@TableName注解的类
        String[] entityPackages = new String[]{mainPackage, Constants.ENTITY_PACKAGE_NAME};
        String[] packagePatterns = parsePackagePatterns(entityPackages);
        Set<Class<?>> tables = PackageScanner.scanClassesWithAnnotation(TableName.class, packagePatterns);
        classes.addAll(tables);

        databaseService.saveTable(new ArrayList<>(classes));
    }

    private String[] parsePackagePatterns(String[] rawPatterns) {
        if (rawPatterns == null || rawPatterns.length == 0) {
            // 如果没有指定，使用默认策略
            return getDefaultPackagePatterns();
        }

        return PackageScanner.parsePackagePatterns(rawPatterns);
    }

    /**
     * 获取默认包名模式
     */
    private String[] getDefaultPackagePatterns() {
        // 默认扫描 entity, model, domain 包
        return new String[]{
                "**.entity",
                "**.model",
                "**.domain",
                "**.entity.*",
                "**.model.*",
                "**.domain.*"
        };
    }
}
