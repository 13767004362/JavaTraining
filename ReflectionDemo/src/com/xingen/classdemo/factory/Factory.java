package com.xingen.classdemo.factory;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public class Factory {
    public static Worker newInstance(String className){
        Worker worker=null;
        try {
          Class< Worker> mClass=  (Class< Worker>)Class.forName(className);
          worker=mClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return worker;
    }
}
