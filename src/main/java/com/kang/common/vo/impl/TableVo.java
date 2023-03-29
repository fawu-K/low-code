package com.kang.common.vo.impl;

import com.kang.common.type.Type;
import com.kang.common.vo.ITableVo;

/**
 * 表视图的接口类
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-13 09:36
 **/
public final class TableVo extends ITableVo {
    /**
     * 当用户在实现该接口的时候，表示为用户自定义的class
     *
     * @return 系统标志
     */
    public Type getType() {
        return Type.SYSTEM;
    }
}
