package com.xingen.collectiondemo.queue;

import java.util.Random;
import java.util.concurrent.*;

/**
 * author: xinGen
 * date : 2019/7/15
 * blog: https://blog.csdn.net/hexingen
 *
 * 阻塞队列
 */
public class WorkQueue {
    public static WorkQueue create() {
        return new WorkQueue();
    }

    private BlockingQueue<Product> queue;
    private volatile boolean isStop;

    private WorkQueue() {
        this.queue = new LinkedBlockingQueue();
    }

    /**
     * 从阻塞队列中生产
     *
     * @throws InterruptedException
     */
    public void produce() throws InterruptedException {
        Thread.sleep(500);
        if (!isStop) {
            int random = new Random().nextInt(100) + 1;
            Product product = Product.create().setMoney(random + "元");
            this.queue.put(product);
            System.out.println("插入元素:-->往阻塞队列中添加,进行生产" + random + "元的商品");
        }
    }

    /**
     * 从阻塞中队列中获取
     *
     * @throws InterruptedException
     */
    public void consume() throws InterruptedException {
        long start_time = System.currentTimeMillis();
        Product product = this.queue.take();
        long end_time = System.currentTimeMillis();
        System.out.println(new StringBuffer()
                .append("获取元素:-->从阻塞队列中，进行消费 ")
                .append(product.money)
                .append(" ,阻塞等待时间  ")
                .append(end_time - start_time)
                .append(" 毫秒"));
    }

    public boolean isStop() {
        return isStop;
    }

    /**
     * 产品
     */
    public static class Product {
        private String money;

        public static Product create() {
            return new Product();
        }

        public Product setMoney(String money) {
            this.money = money;
            return this;
        }
    }

    private static class ProductThread implements Runnable {
        private WorkQueue workQueue;

        public ProductThread(WorkQueue workQueue) {
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            try {
                while (!workQueue.isStop) {
                    workQueue.produce();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ConsumeThread implements Runnable {
        private WorkQueue workQueue;

        public ConsumeThread(WorkQueue workQueue) {
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            try {
                while (!workQueue.isStop) {
                    workQueue.consume();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void test() {
        WorkQueue workQueue = WorkQueue.create();
        new Thread(new ProductThread(workQueue)).start();
        new Thread(new ConsumeThread(workQueue)).start();
        try {
            Thread.sleep(2000);
            workQueue.isStop = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
