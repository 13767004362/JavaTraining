package com.xingen.enumdemo;

/**
 * author: xinGen
 * date : 2019/7/11
 * blog: https://blog.csdn.net/hexingen
 */
public enum Direction1 {
    // 定义枚举项
    FRONT, BEHIND, LEFT, RIGHT;
    Direction1() { //枚举的构造方法
        System.out.println("创建枚举项会,调用enum枚举的构造方法，有几个枚举项，就会调用几次构造方法");
    }
}
