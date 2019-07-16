package com.xingen.collectiondemo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * author: xinGen
 * date : 2019/7/16
 * blog: https://blog.csdn.net/hexingen
 */
public class MapDemo {
    public static void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("工作", "java developer");
        map.put("年龄", "26");
        // 输出
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println(" 循环输出map中的key->value: " + entry.getKey() + " , " + entry.getValue());
        }
    }
}
