package com.xingen.threaddemo.yield;

public class YieldThread extends Thread{
    private static final String TAG=YieldThread.class.getSimpleName();
    @Override
    public void run() {
        for (int i=0;i<30;++i){
            System.out.println(Thread.currentThread().getName()+" 执行 "+i);
            // 当i为15时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
            if (i==15){
                Thread.currentThread().yield();//线程礼让
            }
        }
    }
    public static  YieldThread newInstance(){
        return new YieldThread();
    }
}
