<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${className}Mapper">

    <!-- 根据id进行逻辑删除 -->
    <update id="deleteByIds">
        update ${tableName} set delete_time = now()
        where id in
        <foreach collection="ids" item="id" open="(" separator=", " close=")">
            ${r"#{id}"}
        </foreach>
    </update>

</mapper>
