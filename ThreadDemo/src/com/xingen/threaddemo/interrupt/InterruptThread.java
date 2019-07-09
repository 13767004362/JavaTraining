package com.xingen.threaddemo.interrupt;

/**
 *  参考： https://www.zhihu.com/question/41048032?sort=created
 *
 */
public class InterruptThread implements Runnable{
    private static final String TAG=InterruptThread.class.getSimpleName();

    @Override
    public void run() {
        int i=0;
        System.out.println(TAG+" 开始执行任务");
        while (!isStop()){
            System.out.println(TAG+" 执行 "+(++i));
        }
        System.out.println(TAG+" 检查到线程中断状态，停止任务");
    }

    /**
     * 检查当前线程是否被告知，该中断。
     * @return
     */
    private  boolean isStop(){
        return  Thread.currentThread().isInterrupted();
    }
    public static  InterruptThread newInstance(){
        return new InterruptThread();
    }
}
