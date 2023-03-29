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

    public static void initAllFactory(String mainClassPath) throws Exception {
        //获取该路径下所有类
        Reflections reflections = new Reflections(Constants.PACKAGE_NAME, mainClassPath);
        //获取继承了IFactory的所有类
        Set<Class<? extends IFactory>> classSet = reflections.getSubTypesOf(IFactory.class);
        for (Class<? extends IFactory> aClass : classSet) {
            aClass.newInstance().init(mainClassPath);
        }
    }
}
