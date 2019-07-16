package com.xingen.collectiondemo;

import com.xingen.collectiondemo.queue.TicketQueue;
import com.xingen.collectiondemo.queue.WorkQueue;

/**
 * author: xinGen
 * date : 2019/7/15
 * blog: https://blog.csdn.net/hexingen
 */
public class TestClient {


    public static void main(String[] args) {
            test2();
    }

    /**
     * 测试费阻塞队列
     */
    private  static void test1(){
        TicketQueue.test();
    }

    /**
     *  测试阻塞队列
     */
    private static void test2(){
        WorkQueue.test();
    }
}
