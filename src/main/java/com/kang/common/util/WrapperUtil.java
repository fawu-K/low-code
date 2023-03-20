package com.kang.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.common.dto.AdvancedQueryDto;
import com.kang.common.exception.WrapperException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 适用于MyBatisPlus的工具类
 *
 * @author K.faWu
 * @program low-code
 * @date 2023-03-14 16:03
 **/

@Slf4j
public class WrapperUtil {

    public static <T> QueryWrapper<T> queryByDto(QueryWrapper<T> wrapper, List<AdvancedQueryDto> dtoList) {
        if (CommonsUtils.isNotEmpty(dtoList)) {
            for (AdvancedQueryDto dto : dtoList) {
                wrapper = queryByDto(wrapper, dto);
            }
        }
        wrapper.isNull("delete_time");
        return wrapper;
    }

    public static <T> QueryWrapper<T> queryByDto(QueryWrapper<T> wrapper, AdvancedQueryDto dto) {
        String field = dto.getField();
        String operation = dto.getOperation();
        String value = dto.getValue();
        try {
            Method method = wrapper.getClass().getMethod(operation, String.class, Object.class);
            method.invoke(wrapper, CommonsUtils.humpToLine(field), value);
        } catch (InvocationTargetException e) {
            throw new WrapperException(String.format("[%s]查询方式错误，请检查！", operation));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new WrapperException(String.format("[%s]该查询方式不存在！", operation));
        }

        /*switch (operation) {
            case Constants.EQ:
                //等于
                wrapper.eq(CommonsUtils.humpToLine(field), value);
                break;
            case Constants.NE:
                //不等于
                wrapper.ne(CommonsUtils.humpToLine(field), value);
                break;
            case Constants.GT:
                //大于
                wrapper.gt(CommonsUtils.humpToLine(field), value);
                break;
            case Constants.GE:
                //大于等于
                wrapper.ge(CommonsUtils.humpToLine(field), value);
                break;
            case Constants.LT:
                wrapper.lt(CommonsUtils.humpToLine(field), value);
                //小于
                break;
            case Constants.LE:
                wrapper.le(CommonsUtils.humpToLine(field), value);
                //小于等于
                break;
            case Constants.LIKE:
                wrapper.like(CommonsUtils.humpToLine(field), value);
                //存在
                break;
            case Constants.NOT_LIKE:
                wrapper.notLike(CommonsUtils.humpToLine(field), value);
                //不存在
                break;
            case Constants.IS_NULL:
                //字段为空
                wrapper.isNull(CommonsUtils.humpToLine(field));
                break;
            case Constants.IS_NOT_NULL:
                //字段不为空
                wrapper.isNotNull(CommonsUtils.humpToLine(field));
                break;
            default:
        }*/
        return wrapper;
    }

}
