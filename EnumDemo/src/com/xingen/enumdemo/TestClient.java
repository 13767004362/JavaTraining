package com.xingen.enumdemo;

/**
 * author: xinGen
 * date : 2019/7/11
 * blog: https://blog.csdn.net/hexingen
 */
public class TestClient {
    public static void main(String[] args) {
            test4();
    }

    /**
     * 测试枚举项与枚举的构造方法
     */
    public static void test1(){
        Direction1 direction1= Direction1.FRONT;
    }

    /**
     * 测试枚举与switch语句
     */
    public static void test2(){
        Direction2.testSwitch();
    }

    /**
     *  测试枚举中属性和方法
     */
    public static void test3(){
        Direction3 direction=Direction3.LEFT;
        System.out.println("调用枚举中属性："+direction.getName());
    }

    /**
     * 测试枚举类抽象方法，枚举项匿名内部类实现。
     */
    public static void test4(){
        Direction4 direction=Direction4.FRONT;
        direction.print();
    }

}
