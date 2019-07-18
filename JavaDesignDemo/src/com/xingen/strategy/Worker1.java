package com.xingen.strategy;

/**
 * author: xinGen
 * date : 2019/7/18
 * blog: https://blog.csdn.net/hexingen
 */
public class Worker1 {

    public static Worker1 create() {
        return new Worker1();
    }

    private WorkStrategy workStrategy;

    public Worker1 setWorkStrategy(WorkStrategy workStrategy) {
        this.workStrategy = workStrategy;
        return this;
    }

    public void work() {
        this.workStrategy.work();
    }
}
