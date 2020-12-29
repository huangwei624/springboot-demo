package com.middleyun;

import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

@Data
class Student {
    private String name;
    private Integer age;

    public Student(String name, Integer age) {
        this.age = age;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }
        Student student = (Student) obj;
        return student.name.equals(this.name);
    }
}

public class JavaDemo {

    @Test
    public void test1() throws NoSuchFieldException, IllegalAccessException {
        HashSet<Student> students = new HashSet<>();
        students.add(new Student("张三", 20));
        students.add(new Student("张三", 21));
        students.remove(new Student("张三", 21));
        System.out.println(students.size());
        System.out.println(students);

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "zhangsan ");
        map.put("name", "lisi");
        System.out.println(map.get("name"));
    }

    @Test
    public void test2() throws NoSuchFieldException, IllegalAccessException {
        int a = 20;
        if (a > 10) {
            System.out.println("大于10");
        } else if (a > 15) {
            System.out.println("大于15");
        }
    }
}
