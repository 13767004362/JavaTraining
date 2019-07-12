package com.xingen.genericdemo;

/**
 * author: xinGen
 * date : 2019/7/12
 * blog: https://blog.csdn.net/hexingen
 */
public class GenericDemo1<T> {
    private T t;
    public GenericDemo1 setT(T t) {
        this.t = t;
        return this;
    }
    public void print(){
        System.out.println("定义泛型类,指定泛型变量的值："+t+" ,指定的泛型变量是"+t.getClass().getName());
    }
    public static <K> GenericDemo1<K> create(){
        return new GenericDemo1<>();
    }
}
