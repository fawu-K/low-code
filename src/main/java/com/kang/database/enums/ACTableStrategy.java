package com.kang.database.enums;

import lombok.Getter;

/**
 * 自动创建数据库表的策略
 *
 * @author <a href="https://github.com/fawu-K">fawu.K</a>
 * @since 2026-01-12 13:37
 **/

@Getter
public enum ACTableStrategy {

    NONE(0),
    ADD(1),
    UPDATE(2),
    RESET(3);

    private int code;

    ACTableStrategy(int code) {
        this.code = code;
    }
}
