package com.xingen.threaddemo.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * author: xinGen
 * date : 2019/8/6
 * blog: https://blog.csdn.net/hexingen
 * <p>
 * 测试 Callable 与FutureTask类结合 线程一起 使用
 */
public class FutureTaskTest {

    public static void test() {
        // 线程的执行体
        Callable callable = new ResultCallable();
        // FutureTask是一个具备执行结果，可取消的Runnable子类
        FutureTask<String> futureTask = new FutureTask<>(callable);
        // 开启异步线程
        new Thread(futureTask, "异步线程 ").start();
        try {
            System.out.println(Thread.currentThread().getName() + " 在执行启动任务 ");
            // 会阻塞,直到执行完任务，再获取执行结果
            String result = futureTask.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callable线程执行体，且返回结果
     */
    private static class ResultCallable implements Callable<String> {
        @Override
        public String call() {
            String threadName = Thread.currentThread().getName();
            return new StringBuffer().append(threadName).append("-->执行Callable的 call（）").toString();
        }
    }
}
