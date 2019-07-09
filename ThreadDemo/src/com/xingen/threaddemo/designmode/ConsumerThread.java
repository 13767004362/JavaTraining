package com.xingen.threaddemo.designmode;

/**
 * 消费者线程
 */
public class ConsumerThread implements Runnable {
    private static final  String TAG=ConsumerThread.class.getSimpleName();
    private ProductHandler product;
    public ConsumerThread(ProductHandler product) {
        this.product = product;
    }
    @Override
    public void run() {
        try {
            Thread.currentThread().setName(TAG);
            for (int i=0;i<10;++i){
                product.consumer();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
