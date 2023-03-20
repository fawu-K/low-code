package com.kang.factory;

import com.kang.common.constant.Constants;
import com.kang.database.annotation.Type;
import com.kang.database.vo.TableVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
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
public class TableVoFactory {

    private static final List<Class<? extends TableVo>> TABLE_VO_LIST = new ArrayList<>();
    private static final Map<Type, List<Class<? extends TableVo>>> TABLE_VO_MAPS = new HashMap<>();

    /**
     * 初始化方法
     * @param mainClassPath 启动类路径
     */
    public static void init(String mainClassPath) throws IllegalAccessException, InstantiationException {
        //获取该路径下所有类
        Reflections reflections = new Reflections(Constants.PACKAGE_NAME, mainClassPath);
        //获取继承了TableVo的所有类
        Set<Class<? extends TableVo>> classSet = reflections.getSubTypesOf(TableVo.class);
        TABLE_VO_LIST.addAll(classSet);

        for (Class<? extends TableVo> clazz : TABLE_VO_LIST){
            TableVo obj = clazz.newInstance();
            List<Class<? extends TableVo>> list = TABLE_VO_MAPS.containsKey(obj.getType())? TABLE_VO_MAPS.get(obj.getType()) : new ArrayList<>();
            list.add(clazz);
            TABLE_VO_MAPS.put(obj.getType(), list);
        }
    }

    /**
     * 获取指定类型的tableVo类的数量
     */
    public static Integer getVoSize(Type type) {
        List<Class<? extends TableVo>> classes = TABLE_VO_MAPS.get(type);
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
     * @return 数据表视图类
     */
    @SneakyThrows
    public static TableVo build(Type type, Integer index) {
        return getVoSize(type) > 0 && getVoSize(type) >= index? TABLE_VO_MAPS.get(type).get(index).newInstance() : null;
    }

    /**
     * 构建数据表视图类
     * 根据参数来选择构建的视图类
     * @return 数据表视图类
     */
    public static TableVo build(Type type){
        return build(type, getVoSize(type) - 1);
    }

    /**
     * 构建数据表视图类
     * 在使用该方式获取视图类的时候，会优先构建用户自定义的视图类
     * @return 数据表视图类
     */
    public static TableVo build(){
        TableVo iTableVo = build(Type.CUSTOM);
        if (iTableVo == null) {
            iTableVo = build(Type.SYSTEM);
        }
        return iTableVo;
    }

    /**
     * 构建数据表视图类
     * @return 数据表视图类
     */
    @SneakyThrows
    public static TableVo build(Class<? extends TableVo> clazz) {
        return clazz.newInstance();
    }

    /**
     * 构建数据表视图类
     * @return 数据表视图类
     */
    public static TableVo build(String clazzName) {
        return TABLE_VO_LIST.stream().filter(aClass -> aClass.getSimpleName().equals(clazzName)).findFirst().map(TableVoFactory::build).orElse(null);
    }

}
