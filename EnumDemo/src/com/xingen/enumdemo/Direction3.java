package com.xingen.enumdemo;

/**
 * author: xinGen
 * date : 2019/7/11
 * blog: https://blog.csdn.net/hexingen
 */
public enum Direction3 {
    // 定义枚举项,构造传参
    FRONT("front->前面"),
    BEHIND("behind->后面"),
    LEFT("left->左边"),
    RIGHT("right->右边");

    //定义属性
    private String name;
    Direction3(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
