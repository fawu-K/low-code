package com.kang.common.dto;

import lombok.Data;

/**
 * 高级数据查询dto
 * @author K.faWu
 * @program low-code
 * @date 2023-03-14 15:31
 **/

@Data
public class AdvancedQueryDto {

    /**
     * 字段名
     */
    private String field;

    /**
     * 操作
     */
    private String operation;

    /**
     * 查询值
     */
    private String value;

    /**
     * 左括号
     */
    private String leftBracket;

    /**
     * 右括号
     */
    private String rightBracket;

    @Override
    public String toString() {
        return String.format("AdvancedQueryDto{field='%s', operation='%s', value='%s', leftBracket='%s', rightBracket='%s'}",
                field, operation, value, leftBracket, rightBracket);
    }
}
