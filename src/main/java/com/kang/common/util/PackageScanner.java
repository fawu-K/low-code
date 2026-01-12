package com.kang.common.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PackageScanner {

    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER =
            new PathMatchingResourcePatternResolver();

    private static final MetadataReaderFactory METADATA_READER_FACTORY =
            new CachingMetadataReaderFactory(RESOURCE_PATTERN_RESOLVER);

    /**
     * 解析通配符包名模式，获取所有类
     *
     * @param packagePatterns 包名模式，如: com.fawu.**.entity, com.fawu.**.*Entity
     * @return 类集合
     */
    public static Set<Class<?>> scanPackages(String... packagePatterns) {
        Set<Class<?>> classes = new HashSet<>();

        for (String packagePattern : packagePatterns) {
            try {
                String pattern = convertPackagePatternToResourcePattern(packagePattern);
                Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources(pattern);

                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader metadataReader = METADATA_READER_FACTORY.getMetadataReader(resource);
                        String className = metadataReader.getClassMetadata().getClassName();

                        try {
                            Class<?> clazz = Class.forName(className);
                            classes.add(clazz);
                        } catch (ClassNotFoundException | NoClassDefFoundError e) {
                            // 忽略无法加载的类
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("扫描包路径失败: " + packagePattern, e);
            }
        }

        return classes;
    }

    /**
     * 扫描指定包名下带有特定注解的类
     *
     * @param annotationClass 注解类型
     * @param packagePatterns 包名模式
     * @return 带有注解的类集合
     */
    public static Set<Class<?>> scanClassesWithAnnotation(
            Class<? extends Annotation> annotationClass,
            String... packagePatterns) {

        Set<Class<?>> allClasses = scanPackages(packagePatterns);
        Set<Class<?>> annotatedClasses = new HashSet<>();

        // 深度遍历注解，支持注解继承，处理注解上标记有父注解的情况
        for (Class<?> clazz : allClasses) {
            // 只处理普通类，排除枚举、注解、接口等
            if (clazz.isAssignableFrom(Enum.class) || clazz.isAnnotation() || clazz.isInterface()) {
                continue;
            }
            if (isAnnotationPresent(clazz, annotationClass)) {
                annotatedClasses.add(clazz);
            }
        }

        return annotatedClasses;
    }

    private static boolean isAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        // 首先检查类本身是否直接标注了该注解
        if (clazz.isAnnotationPresent(annotationClass)) {
            return true;
        }

        // 检查类的所有注解，包括元注解（支持注解继承）
        for (Annotation ann : clazz.getAnnotations()) {
            // 检查当前注解是否标注了目标注解
            if (ann.annotationType().isAnnotationPresent(annotationClass)) {
                return true;
            }
            // 递归检查注解的注解，直到找到目标注解或没有更多嵌套
            if (isAnnotationMetaPresent(ann.annotationType(), annotationClass)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 递归检查注解类型的元注解中是否包含指定注解
     */
    private static boolean isAnnotationMetaPresent(Class<?> annotationType, Class<? extends Annotation> targetAnnotation) {
        return isAnnotationMetaPresent(annotationType, targetAnnotation, new HashSet<>());
    }

    private static boolean isAnnotationMetaPresent(Class<?> annotationType, Class<? extends Annotation> targetAnnotation, Set<Class<?>> visited) {
        if (annotationType == null || !annotationType.isAnnotation()) {
            return false;
        }

        // 防止循环引用导致栈溢出
        if (visited.contains(annotationType)) {
            return false;
        }

        // 添加到已访问集合
        visited.add(annotationType);

        // 检查注解类型本身是否带有目标注解
        if (annotationType.isAnnotationPresent(targetAnnotation)) {
            return true;
        }

        // 递归检查注解的所有元注解
        for (Annotation metaAnn : annotationType.getAnnotations()) {
            Class<?> metaAnnType = metaAnn.annotationType();
            if (metaAnnType.isAnnotationPresent(targetAnnotation) ||
                    isAnnotationMetaPresent(metaAnnType, targetAnnotation, visited)) {
                return true;
            }
        }

        // 移除已访问标记，以便其他路径可以正常检查
        visited.remove(annotationType);

        return false;
    }

    /**
     * 将包名模式转换为资源路径模式
     *
     * @param packagePattern 包名模式，如: com.fawu.**.entity
     * @return 资源路径模式，如: classpath*:com/fawu/ ** /entity/*.class
     */
    private static String convertPackagePatternToResourcePattern(String packagePattern) {
        // 处理包名模式中的通配符
        String pattern = packagePattern.trim();

        // 将 . 替换为 /
        pattern = pattern.replace('.', '/');
        // 处理 ** 通配符（匹配多级目录），如 com/fawu/**/entity 转换为 com/fawu/**/entity/**/*.class
        if (pattern.contains("**")) {
            // 如果已经是完整的模式，直接使用
            if (pattern.endsWith("/**")) {
                pattern += "/*.class";
            } else if (pattern.contains("**/")) {
                // 确保路径正确，如 com/fawu/**/entity -> com/fawu/**/entity/**/*.class
                pattern = pattern.replace("**/", "**/");
                pattern += "/**/*.class";
            } else if (pattern.contains("/**")) {
                // 处理类似 com/fawu/**entity 的情况
                pattern = pattern.replace("/**", "/**/");
                pattern += "/**/*.class";
            }
        }
        // 处理 * 通配符（匹配单级目录或类名）
        else if (pattern.contains("*")) {
            // 如果包含类名模式，如 *Entity
            if (pattern.endsWith("*")) {
                pattern += ".class";
            } else {
                // 包路径中的通配符
                pattern += "/*.class";
            }
        }
        // 普通包名
        else {
            pattern += "/**/*.class";
        }

        // 确保以 classpath*: 开头
        if (!pattern.startsWith("classpath*:")) {
            pattern = "classpath*:" + pattern;
        }

        return pattern;
    }

    /**
     * 智能解析包名模式
     * 支持多种格式：
     * 1. com.fawu.**.entity
     * 2. com.fawu.**.*Entity
     * 3. com.fawu.*.entity.*
     * 4. com.fawu.entity (普通包名)
     */
    public static String[] parsePackagePatterns(String[] patterns) {
        if (patterns == null || patterns.length == 0) {
            return new String[0];
        }

        List<String> result = new ArrayList<>();
        for (String pattern : patterns) {
            if (StringUtils.hasText(pattern)) {
                // 处理多种模式
                String processedPattern = processPattern(pattern);
                result.add(processedPattern);
            }
        }

        return result.toArray(new String[0]);
    }

    private static String processPattern(String pattern) {
        pattern = pattern.trim();

        // 如果是类名模式，如 *Entity，需要特殊处理
        if (pattern.endsWith("*Entity") || pattern.endsWith("*Model") ||
                pattern.endsWith("*Dao") || pattern.endsWith("*Service")) {

            // 提取包路径部分
            int lastDot = pattern.lastIndexOf('.');
            if (lastDot > 0) {
                String packagePath = pattern.substring(0, lastDot);
                String classNamePattern = pattern.substring(lastDot + 1);

                // 转换为包路径模式
                return packagePath + "." + classNamePattern;
            }
        }

        return pattern;
    }
}
