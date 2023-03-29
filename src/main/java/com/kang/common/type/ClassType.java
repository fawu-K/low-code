package com.kang.common.type;

/**
 * 用来识别该类是系统内存在的还是用户实现
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-29 14:30
 **/


public interface ClassType {
    /**
     * 继承自该接口的类会被识别为用户自定义的类，但是额可以通过重写该接口来修改
     *
     * @return
     */
    default Type getType() {
        return Type.CUSTOM;
    }
}
