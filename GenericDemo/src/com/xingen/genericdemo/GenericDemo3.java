package com.xingen.genericdemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author: xinGen
 * date : 2019/7/12
 * blog: https://blog.csdn.net/hexingen
 */
public class GenericDemo3 {
    /**
     *  使用List<?>去接收不缺定类型的集合
     * @param dataList
     */
   private static void print(List<?> dataList) {
        Iterator<?> interator = dataList.iterator();
        while (interator.hasNext()) {
            System.out.println("使用？通配符,定义不缺定类型的List,输出存储的值：" + interator.next());
        }

    }
    public static void test3() {
        List<String> stringList=new ArrayList<>();
        stringList.add("字符串456");
        List<Integer> integerList=new ArrayList<>();
        integerList.add(110);
        print(stringList);
        print(integerList);
    }
}
