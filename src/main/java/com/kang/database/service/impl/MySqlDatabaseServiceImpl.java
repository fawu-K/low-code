package com.kang.database.service.impl;

import com.kang.database.entity.Column;
import com.kang.database.mapper.DatabaseMapper;
import com.kang.database.service.DatabaseService;
import com.kang.database.service.TableService;
import com.kang.common.util.CommonsUtils;
import com.kang.database.vo.FaTableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private TableService tableService;

    public MySqlDatabaseServiceImpl(DatabaseMapper dbMapper, TableService tableService) {
        this.dbMapper = dbMapper;
        this.tableService = tableService;
    }

    @Override
    public void newTable(FaTableVo tableVo) {
      log.debug("[{}] 表格构建", tableVo.getTableName());
      dbMapper.newTable(tableVo);
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
                FaTableVo faTableVo = tableService.entityToVo(tableName, clazz);
                newTable(faTableVo);
            }
        }
    }

    @Override
    public boolean updateTableField(FaTableVo faTableVo) {
        return false;
    }

    @Override
    public boolean isTableToEntityField(FaTableVo faTableVo) {
        List<Column> list = dbMapper.getTableFields(faTableVo.getTableName());
        return false;
    }

    @Override
    public List<FaTableVo> getEntityToTable() {
        return null;
    }

}
