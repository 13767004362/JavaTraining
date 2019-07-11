package com.xingen.enumdemo;

/**
 * author: xinGen
 * date : 2019/7/11
 * blog: https://blog.csdn.net/hexingen
 */
public enum Direction4 implements BaseDirection {
    // 定义匿名内部类形式的枚举项
    FRONT() {
        @Override
        public void print() {
            System.out.println("枚举项以匿名内部类形式,复写抽象方法：" + direction());
        }

        @Override
        public String direction() {
            return "front->前面";
        }
    },
    BEHIND() {
        @Override
        public void print() {
            System.out.println("枚举项以匿名内部类形式,复写抽象方法：" + direction());
        }

        @Override
        public String direction() {
            return "behind->后面";
        }
    };
    // 定义枚举中的抽象方法
    public abstract void print();

}
