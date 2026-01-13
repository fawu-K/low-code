package com.kang.database.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kang.database.annotation.ACTableField;
import com.kang.database.annotation.ACTableId;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 表格的初始类
 *
 * @author K.faWu
 * @program LowCode
 * @date 2023-01-06 14:00
 **/
@Data
public class BaseEntity implements Serializable {

    /**
     * id
     */
    @ACTableId
    private Long id;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    @ACTableField(length = "20", comment = "创建者")
    private String creator;

    /**
     * 修改者
     */
    @TableField(fill = FieldFill.UPDATE)
    @ACTableField(length = "20", comment = "修改者")
    private String updator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ACTableField(jdbcType = JdbcType.TIMESTAMP, comment = "创建时间")
    private ZonedDateTime created;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @ACTableField(jdbcType = JdbcType.TIMESTAMP, comment = "修改时间")
    private ZonedDateTime updated;

    /**
     * 删除时间
     * 当业务操作删除时，会进行逻辑删除，即给该字段填写值，以表示该条数据被删除
     * 之所以使用time，当数据量过多时查询sql会变慢，所以可以根据该字段进行定时清理
     */
    @ACTableField(length = "20", comment = "是否删除：null未删除，yyyy-MM-dd HH:mm:ss删除")
    private String deleteTime;

}
