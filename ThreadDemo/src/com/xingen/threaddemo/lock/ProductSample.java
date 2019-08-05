package com.xingen.threaddemo.lock;

import java.util.Locale;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author: xinGen
 * date : 2019/8/5
 * blog: https://blog.csdn.net/hexingen
 */
public class ProductSample {


    public static void test() {
        ProductHandler productHandler = new ProductHandler();
        new ConsumerThread(productHandler).start();
        new ProductThread(productHandler).start();
    }

    private static class ProductThread extends Thread {
        private static final String TAG = ProductThread.class.getSimpleName();
        private ProductHandler handler;

        public ProductThread(ProductHandler handler) {
            super("生产线程-->" + TAG);
            this.handler = handler;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; ++i) {
                handler.product("" + i);
            }
        }
    }

    private static class ConsumerThread extends Thread {
        private static final String TAG = ConsumerThread.class.getSimpleName();
        private ProductHandler handler;

        public ConsumerThread(ProductHandler handler) {
            super("消费线程-->" + TAG);
            this.handler = handler;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; ++i) {
                handler.consumer();
            }
        }
    }

    private static class ProductHandler {
        //控制锁
        private final Lock lock = new ReentrantLock();
        // 生产的条件
        private Condition productCondition = lock.newCondition();
        // 消费的条件
        private Condition consumerCondition = lock.newCondition();
        //产品
        private volatile String product;

        public void consumer() {
            lock.lock();
            try {
                if (product == null) {
                    // 产品还未生产，需要等待
                    this.consumerCondition.await();
                }
                Thread.sleep(50);
                System.out.println(Thread.currentThread().getName() + " ，消费了产品:" + product);
                this.product = null;
                // 消费完后，通知生产线程进行生产
                this.productCondition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void product(String p) {
            lock.lock();
            try {
                if (product != null) {
                    // 产品还未消费，需要等待
                    productCondition.await();
                }
                Thread.sleep(50);
                this.product = p;
                System.out.println(Thread.currentThread().getName() + " ,生产了产品:" + product);
                // 生产好产品后，通知消费线程进行消费
                this.consumerCondition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

    }

}
