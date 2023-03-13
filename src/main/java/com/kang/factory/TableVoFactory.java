package com.kang.factory;

import com.kang.common.constant.Constants;
import com.kang.database.vo.TableVo;
import com.kang.database.annotation.Type;
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

    private static List<Class<? extends TableVo>> tableVoList = new ArrayList<>();
    private static Map<Type, List<Class<? extends TableVo>>> tableVoMaps = new HashMap<>();

    public static void init(String mainClassPath) throws IllegalAccessException, InstantiationException {
        //获取该路径下所有类
        Reflections reflections = new Reflections(Constants.PACKAGE_NAME, mainClassPath);
        //获取继承了IAnimal的所有类
        Set<Class<? extends TableVo>> classSet = reflections.getSubTypesOf(TableVo.class);
        tableVoList.addAll(classSet);

        log.debug("对视图进行区分：{}", tableVoList);

        for (Class<? extends TableVo> clazz : tableVoList){
            TableVo obj = clazz.newInstance();
            List<Class<? extends TableVo>> list = tableVoMaps.containsKey(obj.getType())? tableVoMaps.get(obj.getType()) : new ArrayList<>();
            list.add(clazz);
            tableVoMaps.put(obj.getType(), list);
        }
    }

    public static Integer getSize(Type type) {
        List<Class<? extends TableVo>> classes = tableVoMaps.get(type);
        if (classes != null) {
            return classes.size();
        } else {
            return -1;
        }
    }

    public static Integer getSize() {
        return getSize(Type.CUSTOM);
    }

    @SneakyThrows
    public static TableVo build(Type type, Integer index) {
        return getSize(type) > 0 && getSize(type) >= index? tableVoMaps.get(type).get(index).newInstance() : null;
    }

    public static TableVo build(Type type){
        return build(type, getSize(type) - 1);
    }

    /**
     * 构建数据表视图类
     * @return 数据表视图类
     */
    public static TableVo build(){
        TableVo iTableVo = build(Type.CUSTOM);
        if (iTableVo == null) {
            iTableVo = build(Type.SYSTEM);
        }
        return iTableVo;
    }

    @SneakyThrows
    public static TableVo build(Class<? extends TableVo> clazz) {
        return clazz.newInstance();
    }

    public static TableVo build(String clazzName) {
        return tableVoList.stream().filter(aClass -> aClass.getSimpleName().equals(clazzName)).findFirst().map(TableVoFactory::build).orElse(null);
    }

}
