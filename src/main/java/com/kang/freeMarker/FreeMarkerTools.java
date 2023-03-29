package com.kang.freeMarker;

import com.kang.common.constant.Constants;
import com.kang.common.constant.FtlConstants;
import com.kang.common.util.CommonsUtils;
import com.kang.common.vo.ITableVo;
import com.kang.database.entity.Column;
import com.kang.freeMarker.config.FreeMarkerConfig;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author fawu.K
 */
@Slf4j
public class FreeMarkerTools {

    /**
     * 判断包路径是否存在
     */
    private static void pathJudgeExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 输出到文件
     */
    public static void printFile(ITableVo iTableVo, Template template, String filePath, String fileName) throws Exception {
        pathJudgeExist(filePath);
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        /* 创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名 */
        Writer out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8));
        // 调用模板对象的process方法输出文件
        // root参数是模板使用的数据集，out参数是生成的文件
        template.process(iTableVo, out);
        // 关闭流
        out.close();
    }



    /**
     * 匹配字符串中的英文字符
     */
    public String matchResult(String str) {
        String regEx2 = "[a-z||A-Z]";
        Pattern pattern = Pattern.compile(regEx2);
        StringBuilder sb = new StringBuilder();
        Matcher m = pattern.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                sb.append(m.group());
            }
        }
        return sb.toString();
    }

    /**
     * 初始化列属性字段
     * 并判断该列是否属于父类
     */
    public static boolean initNotSuperColumns(Column column) {
        if (!column.isSuperColumn()) {
            initColumnField(column);
            return true;
        }
        return false;
    }

    /**
     * 初始化列属性字段
     * 需要在这里完成字段的数据库类型到java类型的转换。
     * 一般情况下，用到的java类型可以分为三种，字符、数字、时间。
     */
    public static void initColumnField(Column column) {
        String dataType = column.getDataType();
        //设置实体类的字段名
        column.setEntityColumnName(CommonsUtils.getEntityNameLower(column.getColumnName()));
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
            if (str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setEntityColumnType(FtlConstants.TYPE_BIGDECIMAL);
            }
            // 如果是整形
            else if (str.length == 1 && Integer.parseInt(str[0]) <= 10) {
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
    public static void generate(ITableVo iTableVo, String templateName, String saveUrl, String entityName) throws Exception {
        log.debug("生成文件-[{}/{}]", saveUrl, entityName);

        /* 创建一个Configuration对象，直接new一个对象。构造方法的参数就是FreeMarker对于的版本号 */
        Configuration freeMarker = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        /* 设置模板文件所在的路径 */

        //获取templates的路径
        String templateUrl = getTemplateUrl(saveUrl);

        //判断用户是否有模板
        if (!new File(templateUrl + templateName).isFile()) {
            File file = FileUtils.getFile(templateUrl, templateName);
            String jarName = FreeMarkerTools.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            URL url = new URL("jar:file:" + jarName + "!/templates/" + templateName);
            InputStream in = url.openStream();
            FileUtils.copyInputStreamToFile(in, file);
        }
        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(templateUrl));
        freeMarker.setTemplateLoader(fileTemplateLoader);

        /* 设置模板文件使用的字符集。一般就是utf-8 */
        freeMarker.setDefaultEncoding("utf-8");

        /* 加载一个模板，创建一个模板对象 */
        Template template = freeMarker.getTemplate(templateName);
        //输出文件
        printFile(iTableVo, template, saveUrl, entityName);
    }

    /**
     * 模板路径的获取，这里用了极其糟糕的生成方式，通过字符串的拼接替换，我甚至无法保证他在其他人电脑是是否能够正常运行
     * 不过同样需要注意的是在该方法中会去获取自定义的模板路径{@link FreeMarkerConfig#getTemplatePath()}
     *
     * @param saveUrl 路径
     * @return 模板路径
     */
    private static String getTemplateUrl(String saveUrl) {
        return saveUrl.substring(0, saveUrl.indexOf(Constants.SRC_MAIN_PATH)) + Constants.TARGET_CLASSES + FreeMarkerConfig.getTemplatePath();
    }


}
