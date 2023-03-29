package com.kang;

import com.kang.common.constant.Constants;
import com.kang.common.util.ClassUtil;
import com.kang.database.service.DatabaseService;
import com.kang.database.service.TableService;
import com.kang.factory.FactoryBuilder;
import com.kang.freeMarker.service.FreeMarkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
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
public class DatabaseCommandLineRunner implements CommandLineRunner {

    /**
     * 启动方法路径
     */
    public static String mainClassPath;
    private final ApplicationContext applicationContext;
    private final TableService tableService;
    private final DatabaseService databaseService;
    private final FreeMarkerService freeMarkerService;

    /**
     * 构造方法
     */
    public DatabaseCommandLineRunner(ApplicationContext applicationContext, TableService tableService, DatabaseService databaseService, FreeMarkerService freeMarkerService) {
        this.applicationContext = applicationContext;
        this.tableService = tableService;
        this.databaseService = databaseService;
        this.freeMarkerService = freeMarkerService;
    }

    @Override
    public void run(String... args) throws Exception {

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
                // 该框架下的所有类
                Set<Class<?>> aClass = ClassUtil.getClassSet(Constants.PACKAGE_NAME);
                // 用户的所有类
                aClass.addAll(getClass(mainClazz));

                List<Class<?>> tables = tableService.classIsBaseEntity(aClass);
                databaseService.saveTable(tables);
            }

            // 数据表转实体类
            if (enableAutoDB.tableToEntity()) {
                // 数据表转实体类操作

                //只有当启动数据表转实体类的功能时才对数据表视图工厂进行创建
                FactoryBuilder.initAllFactory(mainClassPath);

                //默认保存路径
                String path = mainClazz.getResource("").getPath();
                path = path.replace(Constants.TARGET_CLASSES, Constants.SRC_MAIN_JAVA_PATH);
                //生成文件
                freeMarkerService.createEntity(path, mainClazz.getPackage().getName());
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
        mainClassPath = clazz.getPackage().getName();
        return ClassUtil.getClassSet(mainClassPath);
    }

}
