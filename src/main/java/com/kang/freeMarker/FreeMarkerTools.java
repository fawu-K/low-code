package com.kang.freeMarker;

import com.kang.common.constant.FtlConstants;
import com.kang.common.util.CommonsUtils;
import com.kang.database.entity.Column;
import com.kang.database.mapper.DatabaseMapper;
import com.kang.database.vo.FaTableVo;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FreeMarkerTools {
    @Autowired
    DatabaseMapper databaseMapper;

    /**
     * 判断包路径是否存在
     */
    private void pathJudgeExist(String path){
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 输出到文件
     */
    public  void printFile(FaTableVo table, Template template, String filePath, String fileName) throws Exception  {
        pathJudgeExist(filePath);
        File file = new File(filePath, fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        // 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        // 第七步：调用模板对象的process方法输出文件
        // root参数是模板使用的数据集，out参数是生成的文件
        template.process(table,  out);
        // 第八步：关闭流
        out.close();
    }

    /**
     * 通过表名获取实体类名
     * 即输入的为带下划线的表名，输出为首字母大写，且驼峰的实体类名称
     * @param tableName
     * @return
     */
    public static String getEntityName(String tableName){
        return CommonsUtils.topCharBig(getEntityNameLower(tableName.toLowerCase()));
    }

    /**
     * 获取首字母小写符合驼峰写法的名称
     */
    public static String getEntityNameLower(String name){
        return CommonsUtils.lineToHump(name.toLowerCase());
    }

    /**
     * 匹配字符串中的英文字符
     */
    public String matchResult(String str) {
        String regEx2 = "[a-z||A-Z]";
        Pattern pattern = Pattern.compile(regEx2);
        StringBuilder sb = new StringBuilder();
        Matcher m = pattern.matcher(str);
        while (m.find()){
            for (int i = 0; i <= m.groupCount(); i++)
            {
                sb.append(m.group());
            }
        }
        return sb.toString();
    }

    /**
     * 获取表信息
     */
    public FaTableVo getDataInfo(String tableName, String packageName){
        String entityName = getEntityName(tableName);

        FaTableVo javaBeanGenerate = FaTableVo.builder()
                .packageName(packageName)
                .className(entityName)
                .columns(getColumn(tableName))
                .build();
        getImportList(javaBeanGenerate);
        return javaBeanGenerate;
    }

    /**
     * 获取该实体类中的所有字段且不包含父类的类
     * @param tableName 表名
     * @return 列属性
     */
    public List<Column> getColumn(String tableName) {
        List<Column> columns = databaseMapper.getTableFields(tableName);
        columns = columns.stream().filter(FreeMarkerTools::initNotSuperColumns).collect(Collectors.toList());
        return columns;
    }

    /**
     * 初始化列属性字段
     * 并判断该列是否属于父类
     */
    private static boolean initNotSuperColumns(Column column) {
        if (!column.isSuperColumn()) {
            initColumnField(column);
            return true;
        }
        return false;
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(Column column) {
        String dataType = column.getDataType();
        //设置实体类的字段名
        column.setEntityColumnName(getEntityNameLower(column.getColumnName()));
        //设置实体类的默认类型
        column.setEntityColumnType(FtlConstants.TYPE_STRING);
        column.setQueryType(FtlConstants.QUERY_EQ);

        //设置实际的字段类型
        if (CommonsUtils.strContains(dataType, FtlConstants.COLUMN_TYPE_STRING, FtlConstants.COLUMN_TYPE_TEXT)) {
            column.setEntityColumnType(FtlConstants.TYPE_STRING);
        } else if (CommonsUtils.strContains(dataType, FtlConstants.COLUMN_TYPE_TIME)) {
            column.setEntityColumnType(FtlConstants.TYPE_DATE);
        } else if (CommonsUtils.strContains(dataType, FtlConstants.COLUMNTYPE_NUMBER)) {
            // 如果是浮点型 统一用BigDecimal
            String substring = column.getColumnType().substring(column.getColumnType().indexOf("(") + 1, column.getColumnType().indexOf(")"));
            String[] str = substring.split(",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setEntityColumnType(FtlConstants.TYPE_BIGDECIMAL);
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                column.setEntityColumnType(FtlConstants.TYPE_INTEGER);
            }
            // 长整形
            else {
                column.setEntityColumnType(FtlConstants.TYPE_LONG);
            }
        }

    }

    /**
     * 生成代码
     */
    public void generate(FaTableVo table, String templateName, String saveUrl, String entityName) throws Exception {
        log.debug("文件-[{}/{}]开始生成...", saveUrl, entityName, templateName);

        // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是FreeMarker对于的版本号
        Configuration freeMarker = new Configuration();

        // 第二步：设置模板文件所在的路径

        //获取templates的路径
        String targetUrl = saveUrl.substring(0, saveUrl.indexOf("src/main/")) + "target/classes/templates/";
        File file = FileUtils.getFile(targetUrl, templateName);

        String file1 = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        URL url = new URL("jar:file:" + file1 + "!/templates/" + templateName);
        InputStream in = url.openStream();

        FileUtils.copyInputStreamToFile(in, file);

        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(targetUrl));
        freeMarker.setTemplateLoader(fileTemplateLoader);

        // 第三步：设置模板文件使用的字符集。一般就是utf-8
        freeMarker.setDefaultEncoding("utf-8");

        // 第四步：加载一个模板，创建一个模板对象
        Template template = freeMarker.getTemplate(templateName);
        //输出文件
        printFile(table, template, saveUrl, entityName);

        log.debug("文件-[{}/{}]生成完毕！", saveUrl, entityName);
    }

    /**
     * 根据列类型获取导入包
     *
     * @param table 业务表对象
     * @return 返回需要导入的包列表
     */
    public static void getImportList(FaTableVo table) {
        List<Column> columns = table.getColumns();
        List<String> importList = new ArrayList<>();
        for (Column column : columns) {
            if (!column.isSuperColumn() && FtlConstants.TYPE_DATE.equals(column.getEntityColumnType())) {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (!column.isSuperColumn() && FtlConstants.TYPE_BIGDECIMAL.equals(column.getEntityColumnType())) {
                importList.add("java.math.BigDecimal");
            }
        }
        table.setImportClassList(importList);
    }

}
