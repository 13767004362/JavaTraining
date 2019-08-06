package com.xingen.threaddemo.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * author: xinGen
 * date : 2019/8/6
 * blog: https://blog.csdn.net/hexingen
 *
 *  Callable与Future 、线程池结合使用
 */
public class FutureTest {
    public static void test(){
        //构建线程
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        // 传递Callable ,执行线程任务
        Future<String> future= executorService.submit(new ResultCallable());
        try {
            System.out.println(Thread.currentThread().getName() + " 在执行启动任务 ");
            Thread.sleep(50);
            // 获取到异步线程的执行结果
            String result = future.get();
            System.out.println(result);
            // 等待线程任务执行完，关闭线程池
            executorService.shutdown();
        }catch (Exception e){
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
