package com.xingen.annotation.override;

/**
 * @Override 这个annotation注解只能使用在方法上，不能修饰属性或者类。
 */
public class JavaDeveloper implements  Developer{
    private final  String TAG=JavaDeveloper.class.getSimpleName();
    @Override
    public void work() {
        System.out.println("我的工作是"+TAG);
    }

    public static  Developer create(){
        return new JavaDeveloper();
    }
}
