

使用`sleep()`方法可以，使当前线程沉睡。


实战案例：

```
public class SleepThread extends Thread {
    private static final String TAG=SleepThread.class.getSimpleName();
    @Override
    public void run() {
        try {
            long startTime=System.currentTimeMillis();
            System.out.println(TAG+" 开始沉睡，时间是 "+startTime);
            Thread.sleep(1000);
            long endTime=System.currentTimeMillis();
            System.out.println(TAG+" 结束沉睡，时间是 "+endTime+"  , 沉睡了"+(endTime-startTime)/1000+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  static SleepThread newInstance(){
        return new SleepThread();
    }
}
```
客户端开始调用该线程：
```
public class Client {
    public static void main(String[] args) {
        testSleepThread();
    }
    /**
     * 测试Sleep()
     */
    public static void testSleepThread(){
       Thread thread= SleepThread.newInstance();
       thread.start();
    }
}
```

控制台输出结果：
```
SleepThread 开始沉睡，时间是 1545553566934
SleepThread 结束沉睡，时间是 1545553567936  , 沉睡了1秒
```