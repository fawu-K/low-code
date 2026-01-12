package com.kang.database.service;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kang.common.constant.Constants;
import com.kang.common.util.PackageScanner;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Async
    public void saveTable(String entityPackage) {
        // TODO: 实现通过实体类建表的动作
        log.debug("开始实现通过实体类建表的动作");

        if (entityPackage != null) {
            entityPackage += ";" + Constants.PACKAGE_NAME;
        } else {
            entityPackage = Constants.PACKAGE_NAME;
        }

        // entityPackage可能会分号分割多个，需要处理
        String[] entityPackages = entityPackage.split("[;；]");
        String[] packagePatterns = parsePackagePatterns(entityPackages);

        // 使用PackageScanner扫描
        Set<Class<?>> classes = PackageScanner.scanClassesWithAnnotation(TableName.class, packagePatterns);

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
