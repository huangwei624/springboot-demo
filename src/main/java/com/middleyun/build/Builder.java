package com.middleyun.build;

public interface Builder {

    Builder age(Integer age);

    Builder name(String name);

    User build();
}
