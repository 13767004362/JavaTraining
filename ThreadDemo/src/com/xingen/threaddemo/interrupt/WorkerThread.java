package com.xingen.threaddemo.interrupt;

public class WorkerThread extends Thread{
    @Override
    public void run() {
        super.run();
         Thread childThread =new Thread( InterruptThread.newInstance());
         childThread.start();
         try {
             Thread.sleep(10);
             //通知该线程需要被中断，但不会主动停止。
             childThread.interrupt();
         }catch (Exception e){
             e.printStackTrace();
         }

    }
}
