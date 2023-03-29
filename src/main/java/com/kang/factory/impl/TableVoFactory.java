package com.kang.factory.impl;

import com.kang.common.constant.Constants;
import com.kang.common.type.Type;
import com.kang.common.vo.ITableVo;
import com.kang.factory.IFactory;
import com.kang.freeMarker.config.ITableVoProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 关于构建表视图实体类的工厂
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-13 09:33
 **/

@Slf4j
@Service
public class TableVoFactory implements IFactory {
    @Autowired
    private TableVoPropertyFactory tableVoPropertyFactory;

    private static final List<Class<? extends ITableVo>> TABLE_VO_LIST = new ArrayList<>();
    private static final Map<Type, List<Class<? extends ITableVo>>> TABLE_VO_MAPS = new HashMap<>();

    /**
     * 初始化方法
     *
     * @param mainClassPath 启动类路径
     */
    @Override
    public void init(String mainClassPath) throws IllegalAccessException, InstantiationException {
        //获取该路径下所有类
        Reflections reflections = new Reflections(Constants.PACKAGE_NAME, mainClassPath);
        //获取继承了TableVo的所有类
        Set<Class<? extends ITableVo>> classSet = reflections.getSubTypesOf(ITableVo.class);
        TABLE_VO_LIST.addAll(classSet);

        for (Class<? extends ITableVo> clazz : TABLE_VO_LIST) {
            ITableVo obj = clazz.newInstance();
            List<Class<? extends ITableVo>> list = TABLE_VO_MAPS.containsKey(obj.getType()) ? TABLE_VO_MAPS.get(obj.getType()) : new ArrayList<>();
            list.add(clazz);
            TABLE_VO_MAPS.put(obj.getType(), list);
        }
    }

    /**
     * 获取指定类型的tableVo类的数量
     */
    public static Integer getVoSize(Type type) {
        List<Class<? extends ITableVo>> classes = TABLE_VO_MAPS.get(type);
        if (classes != null) {
            return classes.size();
        } else {
            return -1;
        }
    }

    /**
     * 获取自定义的tableVo类的数量
     */
    public static Integer getCustomSize() {
        return getVoSize(Type.CUSTOM);
    }

    /**
     * 构建数据表视图类
     *
     * @return 数据表视图类
     */
    @SneakyThrows
    public static ITableVo build(Type type, Integer index) {
        return getVoSize(type) > 0 && getVoSize(type) >= index ? TABLE_VO_MAPS.get(type).get(index).newInstance() : null;
    }

    /**
     * 构建数据表视图类
     * 根据参数来选择构建的视图类
     *
     * @return 数据表视图类
     */
    public static ITableVo build(Type type) {
        return build(type, getVoSize(type) - 1);
    }

    /**
     * 构建数据表视图类
     * 在使用该方式获取视图类的时候，会优先构建用户自定义的视图类
     *
     * @return 数据表视图类
     */
    public static ITableVo build() {
        ITableVo iTableVo = build(Type.CUSTOM);
        if (iTableVo == null) {
            iTableVo = build(Type.SYSTEM);
        }
        return iTableVo;
    }

    /**
     * 构建数据表视图类
     *
     * @return 数据表视图类
     */
    @SneakyThrows
    public ITableVo build(Class<? extends ITableVo> clazz) {
        return clazz.newInstance();
    }

    /**
     * 构建数据表视图类
     *
     * @return 数据表视图类
     */
    public ITableVo build(String clazzName) {
        return TABLE_VO_LIST.stream().filter(aClass -> aClass.getSimpleName().equals(clazzName)).findFirst().map(this::build).orElse(null);
    }

    /**
     * 通过传入的tableName和packageName来获取对应的数据表Vo类。
     * 通过{@link ITableVoProperty#setProperty(ITableVo, String, String)} 方法对表信息进行封装。
     *
     * @param tableName   表名
     * @param packageName 该类所在的包名
     * @return
     */
    public ITableVo build(String tableName, String packageName) {
        ITableVo tableVo = build();
        tableVoPropertyFactory.build().setProperty(tableVo, tableName, packageName);
        log.debug("视图类信息：{}", tableVo.toString());
        return tableVo;
    }

}
