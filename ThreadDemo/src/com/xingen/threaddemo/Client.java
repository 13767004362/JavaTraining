package com.xingen.threaddemo;

import com.xingen.threaddemo.Volatile.VolatileBean;
import com.xingen.threaddemo.Volatile.WorkThread;
import com.xingen.threaddemo.designmode.ConsumerThread;
import com.xingen.threaddemo.designmode.ProducerThread;
import com.xingen.threaddemo.designmode.ProductHandler;
import com.xingen.threaddemo.future.FutureTaskTest;
import com.xingen.threaddemo.future.FutureTest;
import com.xingen.threaddemo.join.WorkerThread;
import com.xingen.threaddemo.lock.ProductSample;
import com.xingen.threaddemo.lock.WriteReadSample;
import com.xingen.threaddemo.runnable.TicketSalesRunnable;
import com.xingen.threaddemo.sleep.SleepThread;
import com.xingen.threaddemo.yield.YieldThread;


public class Client {
    public static void main(String[] args) {
        testFutureTask();
    }

    /**
     *  测试Future与Callable结合使用
     */
    public static  void testFuture(){
        FutureTest.test();
    }
    /**
     * 测试 Callable 与FutureTask类结合使用
     */
    public static void testFutureTask() {
        FutureTaskTest.test();
    }

    /**
     * 测试Lock实现生产月消费者模式
     */
    public static void testLockDesignMode() {
        ProductSample.test();
    }

    /**
     * 测试读写锁，提高效率
     */
    public static void testWriteReadLock() {
        WriteReadSample.test();
    }

    /**
     * 测试ThreadLocal
     */
    private static void testThreadLocal() {
        new com.xingen.threaddemo.thradlocal.WorkThread(" WorkThread1").start();
        new com.xingen.threaddemo.thradlocal.WorkThread(" WorkThread2").start();
    }

    /**
     * 测试volatile变量
     */
    private static void testVolatile() {
        VolatileBean bean = new VolatileBean();
        new WorkThread(bean).start();
        try {
            Thread.currentThread().sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bean.setStop(true);
        System.out.println(Thread.currentThread().getName() + " 修改volatile变量的值 " + true);

    }

    /**
     * 测试共享的Runnable对象
     */
    private static void testCommonRunnable() {
        TicketSalesRunnable runnable = TicketSalesRunnable.newInstance();
        new Thread(runnable, "1号卖票线程").start();
        new Thread(runnable, "2号卖票线程").start();
        new Thread(runnable, "3号卖票线程").start();
    }

    /**
     * 测试 join() 方法， 强制执行该线程
     */
    public static void testJoinThread() {
        Thread thread = new WorkerThread();
        thread.start();
    }

    /**
     * 测试 interrupt()方法，中断该线程
     */
    public static void testInterruptThread() {
        Thread thread = new com.xingen.threaddemo.interrupt.WorkerThread();
        thread.start();
    }

    /**
     * 测试Sleep()
     */
    public static void testSleepThread() {
        Thread thread = SleepThread.newInstance();
        thread.start();
    }

    /**
     * yield()线程的礼让
     */
    public static void testYieldThread() {
        YieldThread yieldThread1 = YieldThread.newInstance();
        yieldThread1.setName(" WorkerThread_1 ");
        YieldThread yieldThread2 = YieldThread.newInstance();
        yieldThread2.setName(" WorkerThread_2 ");
        yieldThread1.start();
        yieldThread2.start();
    }

    /**
     * 测试生产者与消费者模式：
     * <p>
     * 1. 生产者生产出一个产品
     * 2. 消费者再去消费这个产品
     * 3. 生产者需要等待，消费者消费完这个产品再去生产。
     */
    private static void testDesignMode() {
        ProductHandler product = ProductHandler.newInstance();
        new Thread(new ProducerThread(product)).start();
        new Thread(new ConsumerThread(product)).start();
    }
}
