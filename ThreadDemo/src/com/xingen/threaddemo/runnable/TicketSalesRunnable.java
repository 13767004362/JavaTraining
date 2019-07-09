package com.xingen.threaddemo.runnable;

/**
 * 卖票者
 */
public class TicketSalesRunnable implements Runnable{
    /**
     * 共享的属性
     */
    private  int ticketSize;
    public TicketSalesRunnable(){
        this.ticketSize=50;
    }
    @Override
    public void run() {
        for ( int i=0;i<50;++i){
            //通过持有锁，在同一时刻只有一个线程操作卖票。
            synchronized (this){
                if (ticketSize>0){
                    System.out.println(Thread.currentThread().getName() +" 卖出一张票,剩余总票数: "+(--ticketSize));
                }else{
                    System.out.println(Thread.currentThread().getName() +" 发现票卖光了，结束买票任务 ");
                    break;
                }
            }
        }
    }

    public static  TicketSalesRunnable newInstance(){
        return  new TicketSalesRunnable();
    }
}
