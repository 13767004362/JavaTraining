package com.xingen.threaddemo.designmode;

/**
 * 产品处理中心：
 *
 */
public class ProductHandler {
    /**
     * 产品
     */
    private String product;
    /**
     * 一个标志，用于切换生产者和消费者线程。
     */
    private boolean isWait=true;

    /**
     * 生产过程： 消费者等待状态，生产者快马加鞭生产。
     * @param product
     * @throws InterruptedException
     */
    public synchronized void producer(String product) throws InterruptedException{
        if (!isWait){
            super.wait();
        }

        Thread.sleep(30);
        this.product = product;
        isWait=false;
        System.out.println(Thread.currentThread().getName()+ "生产了 "+product );
        super.notify();
    }

    /**
     * 消费过程： 生产者等待状态，消费者消费产品。
     * @throws InterruptedException
     */
    public synchronized void consumer() throws  InterruptedException {
        if (isWait){
            super.wait();
        }
        Thread.sleep(30);
        System.out.println(Thread.currentThread().getName()+ "消费了 "+product);
        isWait=true;
        super.notify();
    }
    public static ProductHandler newInstance() {
        return new ProductHandler();
    }

}
