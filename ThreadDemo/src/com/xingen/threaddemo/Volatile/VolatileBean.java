package com.xingen.threaddemo.Volatile;

/**
 * Created by ${新根} on 2018/12/23.
 * 博客：http://blog.csdn.net/hexingen
 */
public class VolatileBean {
   private  volatile boolean isStop=false;

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
