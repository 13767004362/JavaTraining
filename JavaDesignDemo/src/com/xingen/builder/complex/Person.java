package com.xingen.builder.complex;

public class Person {
    private int age;
    private String name;

    public Person() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("姓名：");
        stringBuffer.append(name);
        stringBuffer.append("\n");
        stringBuffer.append("年龄：");
        stringBuffer.append(age);
        return stringBuffer.toString();
    }
}
