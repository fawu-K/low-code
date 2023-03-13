package com.kang.database.service.impl;

import com.kang.database.entity.FaField;
import com.kang.database.annotation.Id;
import com.kang.database.annotation.NotTable;
import com.kang.database.annotation.Table;
import com.kang.database.service.TableService;
import com.kang.common.util.ClassUtil;
import com.kang.common.util.CommonsUtils;
import com.kang.database.vo.FaTableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author K.faWu
 * @program low-code
 * @date 2023-01-10 17:28
 **/

@Slf4j
@Service
public class TableServiceImpl implements TableService {
    @Override
    public List<Class<?>> classIsBaseEntity(Set<Class<?>> classSet) {
        List<Class<?>> list = new ArrayList<>();
        for (Class<?> clazz : classSet) {
            if (CommonsUtils.isNotEmpty(clazz.getAnnotation(Table.class)) &&
                    CommonsUtils.isEmpty(clazz.getAnnotation(NotTable.class))) {
                list.add(clazz);
            }
        }
        return list;
    }

    @Override
    public FaTableVo entityToVo(String tableName, Class<?> clazz) {
        FaTableVo faTableVo = new FaTableVo();
        faTableVo.setTableName(tableName);

        // 获取所有字段
        List<Field> fields = ClassUtil.getAllFields(clazz);
        List<FaField> faFields = new ArrayList<>();
        for (Field field : fields) {
            // 获取所有带有Field注解的字段
            com.kang.database.annotation.Field annotation = field.getAnnotation(com.kang.database.annotation.Field.class);
            // 当存在id注解时，使用id注解
            Id id = field.getAnnotation(Id.class);
            if (CommonsUtils.isNotEmpty(id)) {
                annotation = id.annotationType().getAnnotation(com.kang.database.annotation.Field.class);
            }
            if (CommonsUtils.isNotEmpty(annotation)) {
                // 例用field注解中的信息组装成数据表中的字段信息
                FaField faField = new FaField(clazz.getName(), CommonsUtils.humpToLine(field.getName()), annotation);
                faFields.add(faField);
            }
        }
        faTableVo.setFields(faFields);

        return faTableVo;
    }

    @Override
    public List<String> entityToTable(List<Class<?>> classes) {
        List<String> tableNames = new ArrayList<>();
        for (Class<?> clazz : classes) {
            tableNames.add(entityToTable(clazz));
        }
        return tableNames;
    }

    @Override
    public String entityToTable(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation != null) {
            return annotation.name().equals("")? CommonsUtils.humpToLine(clazz.getSimpleName()): annotation.name();
        }
        return null;
    }
}
