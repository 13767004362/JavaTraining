package com.xingen.collectiondemo;

import javafx.concurrent.Worker;

import java.util.*;

/**
 * author: xinGen
 * date : 2019/7/16
 * blog: https://blog.csdn.net/hexingen
 */
public class CompareDemo {
    private static final String TAG = CompareDemo.class.getSimpleName();

    /**
     * 测试：比较器Comparator使用
     */
    public static void test2() {
        List<String> dataList = new LinkedList<>();
        dataList.add("d");
        dataList.add("b");
        dataList.add("c");
        dataList.add("a");
        // 按照自然顺序(ASCII码表)排序
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 返回 正数，则o1大于o2.
                // 返回  0，则o1等于o2.
                // 返回 负数,则o1<o2.
                return o1.compareTo(o2);
            }
        };
        Collections.sort(dataList, comparator);
        for (String s : dataList) {
            System.out.println(TAG + " 排序后的输出：" + s);
        }
    }

    /**
     * 测试: 比较器Comparable使用
     */
    public static void test1() {
        final int size = 3;
        Worker[] workers = new Worker[size];
        workers[0] = Worker.create().setAge(120).setName("医生");
        workers[1] = Worker.create().setAge(110).setName("公安");
        workers[2] = Worker.create().setAge(119).setName("火警");
        Arrays.sort(workers);
        for (Worker worker:workers) {
            System.out.println(TAG + " 排序后的输出：" + worker.age+" , "+worker.name);
        }
    }

    /**
     *  实现Comparable接口
     */
    public static class Worker implements Comparable<Worker> {
        private int age;
        private String name;

        public Worker setName(String name) {
            this.name = name;
            return this;
        }

        public Worker setAge(int age) {
            this.age = age;
            return this;
        }

        public static Worker create() {
            return new Worker();
        }

        @Override
        public int compareTo(Worker o) {
            if (this.age > o.age) {
                return 1;
            } else if (this.age < o.age) {
                return -1;
            } else {
                return 0;
            }
        }
    }


}
