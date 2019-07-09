package com.xingen.threaddemo.join;

public class WorkerThread extends Thread {
    private static final String TAG = WorkerThread.class.getSimpleName();

    @Override
    public void run() {

        for (int i = 0; i < 10; ++i) {
            System.out.println(TAG + " 执行 " + i);
            if (i == 2) {
                try {
                    Thread thread = new Thread(new JoinThread());
                    thread.start();
                    //会让调用join()的线程，停止正在执行的任务，处于等待状态。而join所属的线程，会开始强制的执行
                    //这里是WorkerThread线程处于等待状态 ， JoinThread会强制执行。
                    thread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
