package com.xingen.threaddemo.designmode;

/**
 * 生产线程
 */
public class ProducerThread implements  Runnable {
    private static final  String TAG=ProducerThread.class.getSimpleName();
    private ProductHandler product;
    public ProducerThread(ProductHandler product) {
        this.product = product;
    }
    @Override
    public void run() {

        try {
            Thread.currentThread().setName(TAG);
            for (int i=0;i<10;++i){
                product.producer(createProduct(i+1));
            }
        }catch (Exception e){
         e.printStackTrace();
        }

    }
    private String createProduct(int i){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("第");
        stringBuffer.append(i);
        stringBuffer.append("个产品");
        return  stringBuffer.toString();
    }
}
