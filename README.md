# faLowCode
>基于[Spring Boot](https://spring.io/projects/spring-boot/) v2.7.5框架书写的辅助开发项目的功能包。

将已有实体类构建为数据表，或者将数据表生成实体类.

写这个东西只是因为我懒，懒到不想用插件生成文件，只想重启一项目就可以的那种.

## 快速开始
### maven依赖
```xml
<dependency>
    <groupId>io.github.fawu-k</groupId>
    <artifactId>kang-lowCode-spring-boot</artifactId>
    <version>1.0-SNAPSHOP</version>
    <!-- 目前最新版本为<version>1.0.1</version> -->
    <!-- but，我并不清楚阿里云仓库什么时候能同步过去，如果你的maven远程仓库用的是maven中央仓库，则可以使用1.0.1版本 -->
    <!-- 2023.03.14 -->
</dependency>
```

### 启动类
```java
@SpringBootApplication
@EnableAutoDB(tableToEntity = true)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```

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
version：1.0.1之后增加

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

### 自定义数据表视图类
version：1.0.1之后增加

当进行个性化生成模板的时候，我们会发现框架中默认的数据表视图类[TableVo](src/main/java/com/kang/database/vo/TableVo.java)中的字段不太够用了。

此为，特地设置了[TableVoFactory](src/main/java/com/kang/factory/TableVoFactory.java)工厂类，只需要构建一个继承[TableVo](src/main/java/com/kang/database/vo/TableVo.java)的类即可在使用自定义模板时使用更多的字段。
需要注意的是在[TableVoFactory](src/main/java/com/kang/freeMarker/FreeMarkerTools.java)的```getDataInfo```方法，即**获取表信息的方法**中使用的是```TableVoFactory.build()```方法来生成TableVo的对象，因此当有多个```TableVo```的实现类时只会根据加载顺序启动最后一种。

