package com.xingen.builder.complex;

public interface Buidler {
    void setName(String name);

    void setAge(int age);

    Person builder();
}
