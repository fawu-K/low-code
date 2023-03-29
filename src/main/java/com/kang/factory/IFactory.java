package com.kang.factory;

/**
 * 构建工厂类
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-29 14:59
 **/


public interface IFactory {

    void init(String mainClassPath) throws Exception;
}
