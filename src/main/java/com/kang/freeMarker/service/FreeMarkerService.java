package com.kang.freeMarker.service;

import com.kang.common.constant.Constants;
import com.kang.common.util.ClassUtil;
import com.kang.common.util.CommonsUtils;
import com.kang.database.entity.FaTable;
import com.kang.database.mapper.DatabaseMapper;
import com.kang.database.service.TableService;
import com.kang.database.vo.TableVo;
import com.kang.freeMarker.FreeMarkerTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author qawzf
 */
@Slf4j
@Service
public class FreeMarkerService {

    @Autowired
    private FreeMarkerTools freeMarkerTools;
    @Autowired
    private DatabaseMapper databaseMapper;
    @Autowired
    private TableService tableService;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 生成代码的方法
     *
     * @param tableName   表名
     * @param saveUrl     生成文件路径
     * @param packageName 生成上级包名
     */
    public void createEntity(String tableName, String saveUrl, String packageName) throws Exception {
        //bean类名
        String entityName = FreeMarkerTools.getEntityName(tableName);
        //防止热加载多次重启
        if (applicationContext.containsBean(CommonsUtils.topCharSmall(entityName + "Mapper"))) {
            return;
        }
        log.debug("数据表-[{}]开始生成基础代码...", tableName);

        // 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map
        //封装参数
        TableVo table = freeMarkerTools.getDataInfo(tableName, packageName);

        // 生成实体类
        freeMarkerTools.generate(table, "entity.ftl",
                saveUrl + "entity", entityName + ".java");
        // 生成dao
        freeMarkerTools.generate(table, "mapper.ftl",
                saveUrl + "mapper", entityName + "Mapper.java");
        //生成xml
        freeMarkerTools.generate(table, "mapper.xml.ftl",
                saveUrl.substring(0, saveUrl.indexOf("java/")) + "resources/mappers", entityName + "Mapper.xml");
        //生成service
        freeMarkerTools.generate(table, "service.ftl",
                saveUrl + "service", entityName + "Service.java");
        //生成serviceImpl
        freeMarkerTools.generate(table, "serviceImpl.ftl",
                saveUrl + "service/impl", entityName + "ServiceImpl.java");
        // 生成controller
        freeMarkerTools.generate(table, "controller.ftl",
                saveUrl + "controller", entityName + "Controller.java");
        log.debug("数据表-[{}]代码生成完毕", tableName);
    }

    /**
     * 将数据库中的表格生成java代码，并不对该框架中使用的系统表格进行基础代码生成
     *
     * @param saveUrl     代码要保存的位置
     * @param packageName 包名
     * @throws Exception
     */
    public void createEntity(String saveUrl, String packageName) throws Exception {
        // 该框架下的所有实体类
        Set<Class<?>> aClass = ClassUtil.getClassSet(Constants.PACKAGE_NAME);
        List<Class<?>> entityClass = tableService.classIsBaseEntity(aClass);
        List<String> list = tableService.entityToTable(entityClass);

        List<FaTable> tables = databaseMapper.getTables();
        for (FaTable table : tables) {
            String tableName = table.getTableName();
            //去除该框架下的实体类
            if (!list.contains(tableName)) {
                createEntity(tableName, saveUrl, packageName);
            }
        }
    }

}
