# faLowCode
基于[Spring Boot](https://spring.io/projects/spring-boot/) v2.7.5框架书写的辅助开发项目的功能包

## 启动流程

### 1、创建``application-dev.yml``文件
内容格式如下：
```yml
# 更新可能不及时，仅供参考，可根据application.yml文件中需要填写的位置进行书写
SERVLET_CONTEXT_PATH: /
SERVER_PORT: 8080

#数据库配置
MYSQL_HOST: localhost
MYSQL_PORT: 3306
MYSQL_USERNAME: root
MYSQL_PASSWORD: root
DATABASE: lowCode

ENTITY_PATH: com.kang.entity
MAPPER_PATH: classpath:mapper/*.xml

```

## 实体类生成数据表
代码包：com.kang.database

使用方式：在启动类上添加[@EnableAutoDB](src/main/java/com/kang/database/EnableAutoDB.java)注解

在需要生成数据表的实体类上添加[@Table](src/main/java/com/kang/database/annotation/Table.java)注解且继承[BaseEntity](src/main/java/com/kang/database/entity/BaseEntity.java)类
@Table注解是作为识别为实体类的标志，而BaseEntity类作为所有实体类的母类它有着数据表必须的几个字段：
> id、creator、updator、created、updated

到这里你会发现还有一个[@NotTable](src/main/java/com/kang/database/annotation/NotTable.java)注解，正如它的表意一样，表示该类不为实体类。由于@Table注解具有可继承性，这就导致实体类的子类同样会被识别为数据表从而消耗数据库的资源。因此若实体类的子类不为实体类时，需要添加@NotTable作为非实体类的标志。
