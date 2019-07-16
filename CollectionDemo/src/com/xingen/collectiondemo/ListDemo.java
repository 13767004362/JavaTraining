package com.xingen.collectiondemo;

import javax.swing.text.html.HTML;
import java.util.*;

/**
 * author: xinGen
 * date : 2019/7/16
 * blog: https://blog.csdn.net/hexingen
 */
public class ListDemo {

    private static final String TAG = ListDemo.class.getSimpleName();

   public static void test1() {
        List<String> dataList = new ArrayList<>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("c");
        for (String s : dataList) {
            System.out.println(TAG + " for循环输出：" + s);
        }
    }

    public static void test2() {
        List<String> dataList = new LinkedList<>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("c");
       Iterator<String> iterator=  dataList.iterator();
       while (iterator.hasNext()){
           System.out.println(TAG + " 迭代器Iterator循环输出：" + iterator.next());
       }
    }



}
