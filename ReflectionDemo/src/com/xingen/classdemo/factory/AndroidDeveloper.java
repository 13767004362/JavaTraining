package com.xingen.classdemo.factory;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public class AndroidDeveloper implements Worker {
    private final  String TAG=AndroidDeveloper.class.getSimpleName();
    public AndroidDeveloper() {
    }

    @Override
    public void work() {
        System.out.println(TAG+" 工作是：android 造轮子");
    }
}
