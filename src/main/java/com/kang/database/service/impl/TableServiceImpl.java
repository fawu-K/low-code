package com.kang.database.service.impl;

import com.kang.common.util.ClassUtil;
import com.kang.common.util.CommonsUtils;
import com.kang.common.vo.impl.FaTableVo;
import com.kang.database.annotation.ACTableField;
import com.kang.database.annotation.ACTableId;
import com.kang.database.annotation.ACTableName;
import com.kang.database.annotation.NotTable;
import com.kang.database.entity.FaField;
import com.kang.database.service.TableService;
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
            if (CommonsUtils.isNotEmpty(clazz.getAnnotation(ACTableName.class)) &&
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
            // 获取所有带有ACTableField注解的字段
            ACTableField annotation = field.getAnnotation(ACTableField.class);
            // 当存在id注解时，使用id注解
            ACTableId ACTableId = field.getAnnotation(ACTableId.class);
            if (CommonsUtils.isNotEmpty(ACTableId)) {
                annotation = ACTableId.annotationType().getAnnotation(ACTableField.class);
            }
            if (CommonsUtils.isNotEmpty(annotation)) {
                if (annotation.exist()) {
                    // 例用ACTableField注解中的信息组装成数据表中的字段信息
                    FaField faField = new FaField(clazz.getName(), field, annotation);
                    faFields.add(faField);
                }
            } else {
                // 当没有ACTableField注解时，尝试从字段中获取字段信息
                FaField faField = new FaField(clazz.getName(), field);
                faFields.add(faField);
            }
        }

        // 只有当字段列表不为空时才设置，否则可能导致创建空表
        if (!faFields.isEmpty()) {
            faTableVo.setFields(faFields);
        }

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
        ACTableName annotation = clazz.getAnnotation(ACTableName.class);
        if (annotation != null) {
            return "".equals(annotation.value()) ? CommonsUtils.humpToLine(clazz.getSimpleName()) : annotation.value();
        }
        return null;
    }
}
