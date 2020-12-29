package com.middleyun.spi2;

import javax.servlet.annotation.HandlesTypes;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        List<MySpringSpi> mySpringSpis = null;
        ServiceLoader<ServiceSpi> spiServiceLoader = ServiceLoader.load(ServiceSpi.class);
        Iterator<ServiceSpi> iterator = spiServiceLoader.iterator();
        while (iterator.hasNext()) {
            ServiceSpi serviceSpi = iterator.next();
            List<MySpringSpi> springSpis = getAllClassByHandlesType(serviceSpi);
            HashSet<Class<?>> classes = new HashSet<>();
            springSpis.forEach(item -> {
                classes.add(item.getClass());
            });
            // 获取类名和@HandlesTypes的值相同的类
            serviceSpi.execute(classes);
        }

        // 获取类名和@HandlesTypes的值相同的类

    }

    private static List<MySpringSpi> getAllClassByHandlesType(ServiceSpi serviceSpi) throws IOException {
        HandlesTypes handlesTypes = serviceSpi.getClass().getAnnotation(HandlesTypes.class);
        Class<?> annotationValue = handlesTypes.value()[0];

        // 获取在某个包下 ServiceSpi 类上HandlesTypes value 相对应的类
        return getAnnotationClass("com.middleyun.spi2", annotationValue);
    }

    private static List<MySpringSpi> getAnnotationClass(String packageName, Class<?> clazz) throws IOException {
        List<String> classNames = new ArrayList<>();

        String packageNamePath = packageName.replace(".", "/");
        URL url = Main.class.getClassLoader().getResource(packageNamePath);
        if (url != null) {
            File file = new File(url.getFile());
            File[] files = file.listFiles();
            if (files == null) {
                return new ArrayList<>();
            }
            Stream.of(files).forEach(item -> {
                classNames.add(packageName + "." + item.getName().replace(".class", ""));
            });
        }

        if (classNames.size() > 0) {
            return getClasses(classNames, clazz);
        }
        return null;
    }

    private static List<MySpringSpi> getClasses(List<String> classNames, Class<?> clazz) {
        ArrayList<MySpringSpi> mySpringSpis = new ArrayList<>();
        classNames.forEach(item -> {
            try {
                Class<?> cls = Class.forName(item);
                Object ms;
                if (!cls.isInterface() && (ms = cls.newInstance()) instanceof MySpringSpi) {
                    mySpringSpis.add((MySpringSpi) ms);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
        return mySpringSpis;
    }
}
