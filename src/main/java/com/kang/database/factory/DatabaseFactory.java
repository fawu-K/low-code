package com.kang.database.factory;

import com.kang.database.EnableAutoDB;
import com.kang.database.service.DatabaseService;
import com.kang.database.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 * 数据库启动调用类，自动装配数据库
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 14:57
 **/
@Slf4j
@Component
public class DatabaseFactory implements CommandLineRunner {
    private final ApplicationContext applicationContext;

    public DatabaseFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {

        String[] beans = applicationContext.getBeanDefinitionNames();
        Class<?> mainClazz = null;
        EnableAutoDB enableAutoDB = null;
        // 寻找启动类
        a: for (String bean : beans) {
            Object o = applicationContext.getBean(bean);
            Annotation[] annotations = o.getClass().getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof EnableAutoDB) {
                    mainClazz = o.getClass();
                    enableAutoDB = (EnableAutoDB) annotation;
                    break a;
                }
            }
        }
        //表示数据库自动操作已经开启
        if (enableAutoDB != null) {
            // 实体类生成数据表
            if (enableAutoDB.entityToTable()) {
                Set<Class<?>> aClass = getClass(mainClazz);
                DatabaseService databaseService = (DatabaseService) applicationContext.getBean("mySqlDatabaseServiceImpl");
                List<Class<?>> tables = databaseService.classIsBaseEntity(aClass);
                databaseService.saveTable(tables);
            }
            // 数据表转实体类
            if (enableAutoDB.tableToEntity()) {
                // TODO: 数据表转实体类操作，可能还会有生成mapper、service等操作
            }
        }


    }

    /**
     * 通过KangApplication注解以及其中的value值去寻找需要载入的包
     *
     * @param clazz 启动类
     * @return 需要加载的包中的所哟类
     */
    private static Set<Class<?>> getClass(Class<?> clazz) {
        String packageName = clazz.getPackage().getName();

        return ClassUtil.getClassSet(packageName);
    }

}
