package com.kang.factory.impl;

import com.kang.common.constant.Constants;
import com.kang.common.type.Type;
import com.kang.common.util.CommonsUtils;
import com.kang.factory.IFactory;
import com.kang.freeMarker.config.ITableVoProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 生成ITableVoProperty类的工厂类
 * 通过该工厂类将用户自定义的{@link com.kang.freeMarker.config.ITableVoProperty}的实现类送入到框架中去
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-29 14:19
 **/

@Slf4j
@Component
public class TableVoPropertyFactory implements IFactory {

    @Autowired
    private ApplicationContext applicationContext;
    private static final List<Class<? extends ITableVoProperty>> TABLE_VO_PROPERTY_LIST = new ArrayList<>();
    private static final Map<Type, List<Class<? extends ITableVoProperty>>> TABLE_VO_PROPERTY_MAPS = new HashMap<>();

    static List<Class<? extends ITableVoProperty>> getList() {
        return TABLE_VO_PROPERTY_LIST;
    }

    static Map<Type, List<Class<? extends ITableVoProperty>>> getMap() {
        return TABLE_VO_PROPERTY_MAPS;
    }

    /**
     * 初始化工厂类
     *
     * @param mainClassPath 启动类路径
     */
    @Override
    public void init(String mainClassPath) throws IllegalAccessException, InstantiationException {
        //获取该路径下所有类
        Reflections reflections = new Reflections(Constants.PACKAGE_NAME, mainClassPath);
        //获取继承了TableVo的所有类
        Set<Class<? extends ITableVoProperty>> classSet = reflections.getSubTypesOf(ITableVoProperty.class);
        TABLE_VO_PROPERTY_LIST.addAll(classSet);

        for (Class<? extends ITableVoProperty> clazz : TABLE_VO_PROPERTY_LIST) {
            ITableVoProperty obj = clazz.newInstance();
            List<Class<? extends ITableVoProperty>> list = TABLE_VO_PROPERTY_MAPS.containsKey(obj.getType()) ? TABLE_VO_PROPERTY_MAPS.get(obj.getType()) : new ArrayList<>();
            list.add(clazz);
            TABLE_VO_PROPERTY_MAPS.put(obj.getType(), list);
        }
    }

    /**
     * 获取指定类型的tableVo类的数量
     */
    public static Integer getVoSize(Type type) {
        List<Class<? extends ITableVoProperty>> classes = TABLE_VO_PROPERTY_MAPS.get(type);
        if (classes != null) {
            return classes.size();
        } else {
            return -1;
        }
    }

    /**
     * 构建数据表视图类
     *
     * @return 数据表视图类
     */
    @SneakyThrows
    public ITableVoProperty build(Type type, Integer index) {
        ITableVoProperty iTableVoProperty = getVoSize(type) > 0 && getVoSize(type) >= index ? TABLE_VO_PROPERTY_MAPS.get(type).get(index).newInstance() : null;
        if (iTableVoProperty == null) {
            return null;
        }
        String className = iTableVoProperty.getClass().getSimpleName();
        log.debug("tablevo封装参数类-{}", className);
        return (ITableVoProperty) applicationContext.getBean(CommonsUtils.topCharSmall(className));
    }

    /**
     * 构建数据表视图类
     * 根据参数来选择构建的视图类
     *
     * @return 数据表视图类
     */
    public ITableVoProperty build(Type type) {
        return build(type, getVoSize(type) - 1);
    }

    /**
     * 封装属性，在这里优先使用自定义的封装方法
     *
     * @return 数据表视图类
     */
    public ITableVoProperty build() {
        ITableVoProperty iTableVoProperty = build(Type.CUSTOM);
        if (iTableVoProperty == null) {
            iTableVoProperty = build(Type.SYSTEM);
        }
        return iTableVoProperty;
    }

}
