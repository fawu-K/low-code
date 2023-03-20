package com.kang.common.exception;

/**
 * 数据库Wrapper异常类
 * 当出现在wrapper中的异常时,统一以此异常抛出
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-20 16:23
 **/

public class WrapperException extends RuntimeException {

    public WrapperException() {
        super();
    }

    public WrapperException(String msg) {
        super(msg);
    }
}
