package com.xingen.threaddemo.join;

public class JoinThread implements Runnable {
    private static final String TAG=JoinThread.class.getSimpleName();
    @Override
    public void run() {
        for (int i=0;i<10;++i){
            System.out.println(TAG+" 执行 "+i);
        }
    }
}
