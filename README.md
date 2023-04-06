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

到这里你会发现还有一个[@NotTable](src/main/java/com/kang/database/annotation/NotTable.java)
注解，正如它的表意一样，表示该类不为实体类。由于@Table注解具有可继承性，这就导致实体类的子类同样会被识别为数据表从而消耗数据库的资源。因此若实体类的子类不为实体类时，需要添加@NotTable作为非实体类的标志。

## 代码生成器

代码包：com.kang.freeMarker
该代码生成器的核心是freeMarker框架，通过预先写好的模板去构建代码。

使用方式：[@EnableAutoDB](src/main/java/com/kang/EnableAutoDB.java)注解增加```tableToEntity = true```
参数，开启自动根据数据表生成实体类、Dao层、service层以及controller层

### 模板

在框架中自带的模板中有着三个接口，分别是**query、save以及delete**.

#### query接口

```java
/**
 * 查询接口，使用POST请求方式，分页通过url进行传值。
 * 查询条件以json的形式进行接收，以{@link AdvancedQueryDto}类作为查询条件的模板，
 * 其中{@link AdvancedQueryDto#getOperation()}的值是该字段在引入{@link QueryWrapper}时调用的方法
 * 可支持的方法名可在{@link WrapperUtil#queryByDto(QueryWrapper, AdvancedQueryDto)}中看到，后续有可能更新。
 *
 * @param dtoList 查询条件
 * @param pageNum 页码
 * @param pageSize 页数
 * @return 查询结果
 */
```

以上是query接口的注解.

一般来说，query方法会进行分页，所以在默认的query接口中会通过问号传值的方式来从接口中获取pageNum以及pageSize.
除此之外，常见的查询接口一般会带有查询条件，在默认的query接口中查询调价通过```List<AdvancedQueryDto> dtoList```来传递。
[AdvancedQueryDto](src/main/java/com/kang/common/dto/AdvancedQueryDto.java)类是一个高级数据查询dto，该类中含有字段名、查询值以及操作这三个关键属性。
其中的“操作”属性是用来表示MyBatis-Plus中QueryWrapper的将要调用的方法。
为此在框架中构建了[WrapperUtil](src/main/java/com/kang/common/util/WrapperUtil.java)工具类

### 自定义模板

> version：1.0.1之后增加

关于自定义模板，当框架中的默认模板无法满足开发者的需求或开发者需要个性化配置，则开发者可以使用自定义的模板。但当模板放在非默认的文件夹下时需要开发者在配置文件中表明模板包路径。
当然，该模板包需要在resources包下。

我们可以看到[FreeMarkerConfig](src/main/java/com/kang/freeMarker/config/FreeMarkerConfig.java)
中对于模板包默认的路径为```templates/```，当你需要放在非默认包下放置模板时请按照以下格式配置：

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

### 自定义TableVo(数据表视图类)

> version：1.0.1之后增加

当进行个性化生成模板的时候，我们会发现框架中默认的数据表视图类[ITableVo](src/main/java/com/kang/common/vo/ITableVo.java)
中的字段不太够用了。

此为，特地设置了[TableVoFactory](src/main/java/com/kang/factory/impl/TableVoFactory.java)
工厂类，只需要构建一个继承[ITableVo](src/main/java/com/kang/common/vo/ITableVo.java)的类即可在使用自定义模板时使用更多的字段。
需要注意的是在框架中默认使用的[TableVo](src/main/java/com/kang/common/vo/impl/TableVo.java)类是**final**
修饰的，该类不可被继承。你会发现该类中重写了[classType](src/main/java/com/kang/common/type/ClassType.java)类的getType()
方法，该方法是用来区分框架中自带的类与开发者自定义的类。
因此在自定义ITableVo类的时候最好不要重写getType()方法.

### 自定义TableVoProperty(数据表视图属性赋值类)

> version: 0.1.1-SNAPSHOP

当实现自定义TableVo后，如果需要TableVo的属性不通过框架中的逻辑进行赋值，则可以通过实现[ITableVoProperty](src/main/java/com/kang/freeMarker/config/ITableVoProperty.java)
接口的**setProperty**方法。
在框架里存在[TableVoPropertyFactory](src/main/java/com/kang/factory/impl/TableVoPropertyFactory.java)工厂类。
该工厂类和[TableVoFactory](src/main/java/com/kang/factory/impl/TableVoFactory.java)
工厂类的作用是相似的，都是将指定接口的实现类收集起来，并在其中寻找将要使用的实现类。
