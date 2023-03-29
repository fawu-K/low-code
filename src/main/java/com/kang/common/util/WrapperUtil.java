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

    /**
     * 该方法通过{@link AdvancedQueryDto#getOperation()}的值当作{@link QueryWrapper}类的方法名，来实现sql的拼接。
     * 注意：在该方法中默认设置了查询delete_time字段为空的操作，所以在使用该方法时需要注意要查询的数据表中存在delete_time字段
     *
     * @param wrapper QueryWrapper
     * @param dtoList 查询参数
     * @param <T>     要查询的实体类
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> queryByDto(QueryWrapper<T> wrapper, List<AdvancedQueryDto> dtoList) {
        if (CommonsUtils.isNotEmpty(dtoList)) {
            for (AdvancedQueryDto dto : dtoList) {
                wrapper = queryByDto(wrapper, dto);
            }
        }
        wrapper.isNull("delete_time");
        return wrapper;
    }

    /**
     * 该方法通过{@link AdvancedQueryDto#getOperation()}的值当作{@link QueryWrapper}类的方法名，来实现sql的拼接。
     *
     * @param wrapper QueryWrapper
     * @param dto     查询参数
     * @param <T>     要查询的实体类
     * @return QueryWrapper
     */
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
        return wrapper;
    }
}
