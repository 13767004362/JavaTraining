package com.xingen.classdemo.factory;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public class JavaDeveloper implements Worker {
    private final String TAG=JavaDeveloper.class.getSimpleName();
    public JavaDeveloper() {
    }

    @Override
    public void work() {
        System.out.println(TAG+" 工作是：Java 造轮子");
    }
}
