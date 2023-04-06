package com.kang.factory;

import com.kang.common.constant.Constants;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 构建工厂的工厂
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-29 13:54
 **/

@Component
public class FactoryBuilder {

    /**
     * 对工厂进行初始化，这里是对工厂中的static属性进行初始化，而IFactory接口中的init方法便是用来对工厂的static属性进行初始化的方法。
     *
     * @param mainClassPackage 启动类所在路径
     * @throws Exception 工厂的构建方法异常
     */
    public static void initAllFactory(String mainClassPackage) throws Exception {
        //获取该路径下所有类
        Reflections reflections = new Reflections(Constants.PACKAGE_NAME, mainClassPackage);
        //获取继承了IFactory的所有类
        Set<Class<? extends IFactory>> classSet = reflections.getSubTypesOf(IFactory.class);
        for (Class<? extends IFactory> aClass : classSet) {
            aClass.newInstance().init(mainClassPackage);
        }
    }
}
