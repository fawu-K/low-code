package com.kang.freeMarker.config;

import com.kang.common.type.ClassType;
import com.kang.common.vo.ITableVo;

/**
 * 该接口用来对tableVo类进行赋值。
 * 当定制了更独特的tableVo类后，其中独有那些属性在框架自带的赋值方法中无法进行赋值。
 * 所以可以通过实现该接口的setProperty方法进行自定义的赋值。
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-28 10:57
 **/

public interface ITableVoProperty extends ClassType {

    /**
     * 对tableVo进行赋值。
     *
     * @param itableVo    需要赋值的tableVo
     * @param tableName   数据表名
     * @param packageName 要存放的包名
     */
    void setProperty(ITableVo itableVo, String tableName, String packageName);
}
