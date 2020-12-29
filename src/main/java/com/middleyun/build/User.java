package com.middleyun.build;

public class User {

    private String name;
    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static Builder builder() {
        return new UserBuilder();
    }

    public static final class UserBuilder implements Builder {

        private String name;
        private Integer age;

        @Override
        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        @Override
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public User build() {
            if (!checkField()) {
                throw new RuntimeException("name or age illegal");
            }
            return new User(this.name, this.age);
        }

        private Boolean checkField() {
            if (this.name == null || name.trim().equals("")) {
                return false;
            }
            if (age == null || age <= 0) {
                return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        User user = User.builder().age(10).name("张三").build();
        System.out.println(user);
        User user2 = User.builder().age(0).name("张三").build();
    }

}
