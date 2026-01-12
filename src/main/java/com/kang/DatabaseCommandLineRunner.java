package com.kang;

import com.kang.common.constant.Constants;
import com.kang.common.util.ClassUtil;
import com.kang.database.config.AutoCreateTableConfig;
import com.kang.database.service.ACTableService;
import com.kang.factory.FactoryBuilder;
import com.kang.freeMarker.service.FreeMarkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
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
    public static String mainClassPackage;
    private final ApplicationContext applicationContext;
    private final FreeMarkerService freeMarkerService;
    private final ACTableService acTableService;
    private final AutoCreateTableConfig autoCreateTableConfig;

    /**
     * 构造方法
     */
    public DatabaseCommandLineRunner(ApplicationContext applicationContext, FreeMarkerService freeMarkerService, ACTableService acTableService) {
        this.applicationContext = applicationContext;
        this.freeMarkerService = freeMarkerService;
        this.acTableService = acTableService;
        this.autoCreateTableConfig = applicationContext.getBean(AutoCreateTableConfig.class);
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
                log.debug("开始执行实体类生成数据表功能");
                // 使用默认包名，或从配置中获取
                String mainPackage = mainClazz.getPackage().getName();
                log.debug("EnableAutoDB注解所在包：{}", mainPackage);
                acTableService.saveTable(mainPackage);
            }

            // 数据表转实体类
            if (enableAutoDB.tableToEntity()) {
                // 异步执行 数据表转实体类操作

                //只有当启动数据表转实体类的功能时才对数据表视图工厂进行创建
                FactoryBuilder.initAllFactory(mainClassPackage);

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
        mainClassPackage = clazz.getPackage().getName();
        return ClassUtil.getClassSet(mainClassPackage);
    }
}
