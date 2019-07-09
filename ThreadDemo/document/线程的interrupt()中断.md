

通过调用线程的`interrupt()`方法去标记结束状态，然后通过`isInterrupted()`检查去该状态，从而结束线程。

**实战开发**：


先编写一个执行输出任务，且检查线程状态的Runnable子类：
```
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
```
然后，编写一个开启执行线程，且对其中断的WorkThread线程：
```
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
```
最后，客户端调用测试：
```
public class Client {
    public static void main(String[] args) {
        testInterruptThread();
    }
    /**
     * 测试 interrupt()方法，中断该线程
     */
    public static void testInterruptThread(){
            Thread thread= new com.xingen.threaddemo.interrupt.WorkerThread();
            thread.start();
    }
}
```












控制台输出结果：
```
InterruptThread 开始执行任务
InterruptThread 执行 1
InterruptThread 执行 2
InterruptThread 执行 3
//............
InterruptThread 执行 119
InterruptThread 执行 120
InterruptThread 检查到线程中断状态，停止任务
```