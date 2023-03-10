# faLowCode
>基于[Spring Boot](https://spring.io/projects/spring-boot/) v2.7.5框架书写的辅助开发项目的功能包。

将已有实体类构建为数据表，或者将数据表生成实体类

## 实体类生成数据表
代码包：com.kang.database

使用方式：在启动类上添加[@EnableAutoDB](src/main/java/com/kang/EnableAutoDB.java)注解

在需要生成数据表的实体类上添加[@Table](src/main/java/com/kang/database/annotation/Table.java)注解且继承[BaseEntity](src/main/java/com/kang/database/entity/BaseEntity.java)类
@Table注解是作为识别为实体类的标志，而BaseEntity类作为所有实体类的母类它有着数据表必须的几个字段：
> id、creator、updator、created、updated

到这里你会发现还有一个[@NotTable](src/main/java/com/kang/database/annotation/NotTable.java)注解，正如它的表意一样，表示该类不为实体类。由于@Table注解具有可继承性，这就导致实体类的子类同样会被识别为数据表从而消耗数据库的资源。因此若实体类的子类不为实体类时，需要添加@NotTable作为非实体类的标志。
## 代码生成器
代码包：com.kang.freeMarker

使用方式：[@EnableAutoDB](src/main/java/com/kang/EnableAutoDB.java)注解增加```tableToEntity = true```参数，开启自动根据数据表生成实体类、Dao层、service层以及controller层

### 自定义模板
关于自定义模板，当框架中的默认模板无法满足开发者的需求或开发者需要个性化配置，则开发者可以使用自定义的模板。但当模板放在非默认的文件夹下时需要开发者在配置文件中表明模板包路径。 当然，该模板包需要在resources包下。

我们可以看到[FreeMarkerConfig](src/main/java/com/kang/freeMarker/config/FreeMarkerConfig.java)中对于模板包默认的路径为```templates/```，当你需要放在非默认包下放置模板时请按照以下格式配置：
```yml
kang:
  low-code:
    template-path: test/templates/
```
模板的名称言简意赅，如下所示：
```text
controller.ftl
entity.ftl
mapper.ftl
mapper.xml.ftl
service.ftl
serviceImpl.ftl
```
