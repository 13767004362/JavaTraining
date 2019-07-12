package com.xingen.genericdemo;

/**
 * author: xinGen
 * date : 2019/7/12
 * blog: https://blog.csdn.net/hexingen
 */
public class TestClient {

    public static void main(String[] args) {
               test4();
    }

    /**
     * 测试泛型类
     */
    public static void test1(){
        GenericDemo1<String> generic1=  GenericDemo1.create();
        generic1.setT("字符串").print();
        GenericDemo1<Integer> generic2= GenericDemo1.create();
        generic2.setT(100).print();
    }

    /**
     *  测试泛型接口
     */
    public static void test2(){
        GenericInterface<String> generic1=GenericDemo2.create();
        generic1.print(" 字符串123");
    }

    /**
     *  测试通配符 ？
     */
    public static void test3(){
        GenericDemo3.test3();
    }

    /**
     *  测试通配符的上限和下限
     */
    public static void test4(){
        GenericDemo4.test3();
        GenericDemo4.test4();
    }
}
