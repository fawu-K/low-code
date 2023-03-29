package com.kang.common.vo.impl;

import com.kang.common.type.Type;
import com.kang.common.vo.ITableVo;
import com.kang.database.entity.FaField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 13:56
 **/

@Data
public final class FaTableVo extends ITableVo implements Serializable {

    @Override
    public Type getType() {
        return Type.SYSTEM;
    }

    /**
     * 字段信息列表
     */
    private List<FaField> fields;
}
