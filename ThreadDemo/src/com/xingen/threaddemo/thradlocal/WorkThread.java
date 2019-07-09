package com.xingen.threaddemo.thradlocal;

/**
 * Created by ${新根} on 2018/12/23.
 * 博客：http://blog.csdn.net/hexingen
 */
public class WorkThread extends Thread {
    public WorkThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        while (ThreadLocalUtils.getDataBean().getI() < 5) {
            ThreadLocalUtils.getDataBean().increase();
            System.out.println(this.getName() + " " + ThreadLocalUtils.getDataBean().getI());
        }
    }
}
