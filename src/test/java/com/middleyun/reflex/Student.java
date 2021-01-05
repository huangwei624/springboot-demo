package com.middleyun.reflex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private Integer age;
    private List<String> friends;
    private Set<String> pets;

    private Student student;

    public static void main(String[] args) {
        Student student = new Student("张三", 20, null, null, null);
        Class<? extends Student> studentClass = student.getClass();
        Field[] declaredFields = studentClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            String fieldName = field.getName();
            // 获取属性的类型
            String typeName = field.getGenericType().getTypeName();
            System.out.println(fieldName + ":" + typeName);

            String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            try {
                // 获取 get 方法
                Method method = studentClass.getDeclaredMethod(methodName);
                Object methodReturnValue = method.invoke(student);
                if (methodReturnValue != null) {
                    System.out.println(methodReturnValue);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

}
