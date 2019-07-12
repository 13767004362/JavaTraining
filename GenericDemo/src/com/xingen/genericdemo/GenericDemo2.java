package com.xingen.genericdemo;

/**
 * author: xinGen
 * date : 2019/7/12
 * blog: https://blog.csdn.net/hexingen
 */
public class GenericDemo2<K> implements GenericInterface<K> {
    @Override
    public void print(K k) {
        System.out.println("继承泛型接口,指定泛型变量的值："+k+" ,指定的泛型变量是"+k.getClass().getName());
    }
    public static <K> GenericDemo2<K> create(){
        return new GenericDemo2();
    }

}
