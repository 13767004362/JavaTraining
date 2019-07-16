package com.xingen.collectiondemo;

import java.util.HashSet;
import java.util.Set;

/**
 * author: xinGen
 * date : 2019/7/16
 * blog: https://blog.csdn.net/hexingen
 */
public class SetDemo {
    private static final String TAG=SetDemo.class.getSimpleName();
    public static void test(){
        Set<String> stringSet=new HashSet<>();
        final  String value1="110",value2="119",value3="120";
        stringSet.add(value1);
        stringSet.add(value2);
        stringSet.add(value3);
        // 重复添加,验证元素是否唯一。
        stringSet.add(value1);
        for (String value:stringSet){
            System.out.println(TAG+"  for循环输出 value:"+value);
        }
    }
}
