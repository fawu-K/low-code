package com.kang.database.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @program: kang-spring
 * @description: 处理类的工具
 * @author: K.faWu
 * @create: 2022-07-28 16:35
 **/
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * @param className 类名
     * @param isInitialized 是否初始化
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 获取包名下的所有类信息
     * @param packageName 包名
     * @return 类信息
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {

                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        print(packagePath, packageName);
                        addClass(classSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURlConnection = (JarURLConnection) url.openConnection();
                        if (jarURlConnection != null) {
                            JarFile jarFile = jarURlConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get class set failure", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }


    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            //当是文件且不是加载类下的文件才会进行加载类

            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (CommonsUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (CommonsUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (CommonsUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

    private static void print(String packagePath, String packageName) {
        packageName  = packageName.replaceAll("\\.", "/");
        packagePath = packagePath.replace(packageName, "") + "banner.txt";
        File file = new File(packagePath);
        if (file.exists()){
            try (FileReader fileReader = new FileReader(file)) {
                char[] cs= new char[2048];
                int len =0;
                while((len= fileReader.read(cs))!=-1){
                    System.out.println(new String(cs,0,len));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取一个类所有的字段
     * @param clazz 指定类
     * @return 类的字段
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null && !clazz.getName().toLowerCase().equals("java.lang.object")){
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }
}
