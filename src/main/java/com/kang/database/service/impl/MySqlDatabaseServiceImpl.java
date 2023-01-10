package com.kang.database.service.impl;

import com.kang.database.entity.FaField;
import com.kang.database.interfaces.Id;
import com.kang.database.interfaces.NotTable;
import com.kang.database.interfaces.Table;
import com.kang.database.mapper.DatabaseMapper;
import com.kang.database.service.DatabaseService;
import com.kang.database.util.ClassUtil;
import com.kang.database.util.CommonsUtils;
import com.kang.database.vo.FaTableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * MySql版本的数据库构建服务
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 13:54
 **/

@Slf4j
@Service
public class MySqlDatabaseServiceImpl implements DatabaseService {
    private DatabaseMapper dbMapper;

    public MySqlDatabaseServiceImpl(DatabaseMapper dbMapper) {
        this.dbMapper = dbMapper;
    }

    @Override
    public void newTable(FaTableVo tableVo) {
      log.debug("[{}] 表格构建", tableVo.getName());
      dbMapper.newTable(tableVo);
    }

    @Override
    public FaTableVo entityToVo(String tableName, Class<?> clazz) {
        FaTableVo faTableVo = new FaTableVo();
        faTableVo.setName(tableName);

        // 获取所有字段
        List<Field> fields = ClassUtil.getAllFields(clazz);
        List<FaField> faFields = new ArrayList<>();
        for (Field field : fields) {
            // 获取所有带有Field注解的字段
            com.kang.database.interfaces.Field annotation = field.getAnnotation(com.kang.database.interfaces.Field.class);
            // 当存在id注解时，使用id注解
            Id id = field.getAnnotation(Id.class);
            if (CommonsUtils.isNotEmpty(id)) {
                annotation = id.annotationType().getAnnotation(com.kang.database.interfaces.Field.class);
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
    public boolean isHaveTable(String tableName) {
        log.debug("检测表[{}]", tableName);
        int isTable = dbMapper.isTable(tableName);

        return isTable != 0;
    }

    @Override
    public void saveTable(List<Class<?>> tables) {
        for (Class<?> clazz : tables) {

            String tableName = CommonsUtils.humpToLine(clazz.getSimpleName());
            if (!isHaveTable(tableName)) {
                FaTableVo faTableVo = entityToVo(tableName, clazz);
                newTable(faTableVo);
            }
        }
    }

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
}
