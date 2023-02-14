package com.kang.database.vo;

import com.kang.database.entity.FaField;
import com.kang.database.entity.FaTable;
import com.kang.database.annotation.NotTable;
import lombok.Data;

import java.util.List;

/**
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 13:56
 **/

@Data
@NotTable
public class FaTableVo extends FaTable {

    private List<FaField> fields;
}
