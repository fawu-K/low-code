package com.kang.factory;

/**
 * 工厂类
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-29 14:59
 **/


public interface IFactory {

    /**
     * 对工厂中的static属性进行初始化操作，
     *
     * @param mainClassPackage 启动类所在路径
     * @throws Exception 初始化static属性可能出现的异常
     */
    void init(String mainClassPackage) throws Exception;
}
