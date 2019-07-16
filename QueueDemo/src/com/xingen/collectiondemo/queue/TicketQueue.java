package com.xingen.collectiondemo.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: xinGen
 * date : 2019/7/15
 * blog: https://blog.csdn.net/hexingen
 * <p>
 * 非阻塞队列:
 */
public class TicketQueue {

    private Queue<Integer> queue;
    private CountDownLatch countDownLatch;
    private final static int TICKET_SIZE = 10;

    private TicketQueue() {
        this.queue = new ConcurrentLinkedQueue<>();
        this.countDownLatch = new CountDownLatch(TICKET_SIZE);
    }

    public static TicketQueue create() {
        return new TicketQueue();
    }

    /**
     * 生产
     */
    public void product() {
        for (int i = 1; i <= TICKET_SIZE; ++i) {
            queue.offer(i);
        }
    }

    /**
     * 消费
     */
    public void consume(final  String mark) throws  InterruptedException{
        while (!this.queue.isEmpty()) {
            Integer ticket = queue.poll();
            if (ticket != null) {
                System.out.println(mark+" ,从队列中取出的票是 " + ticket);
                this.countDownLatch.countDown();
                //Thread.sleep(100);
            }
        }
    }

    /**
     * 等待，当计数减到0时，所在线程才执行
     */
    public void await() {
        try {
            this.countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class ConsumeThread implements Runnable {
        private TicketQueue ticketQueue;
        private String mark;

        public ConsumeThread(TicketQueue ticketQueue,String mark) {
            this.ticketQueue = ticketQueue;
            this.mark=mark;
        }

        @Override
        public void run() {
            try {
                this.ticketQueue.consume(mark);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void test() {
        TicketQueue ticketQueue = TicketQueue.create();
        // 主线程调用，充当生产者线程，先生产10张票
        ticketQueue.product();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // 构建三个消费者线程
        final  int product_size=3;
        for (int i=0;i<product_size;++i){
            executorService.submit(new ConsumeThread(ticketQueue,"消费者线程"+(i+1)));
        }
        // 等待消费完后，关闭线程池
        ticketQueue.await();
        executorService.shutdown();
    }


}
