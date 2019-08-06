前言：
>通过直接继承Thread或者实现Runnable接口,都有一个缺陷就是：在执行完任务之后无法获取执行结果。从Java 1.5开始，就提供了Callable和Future，通过它们可以在任务执行完毕之后得到任务执行结果


先来了解一下Callable接口与Future接口与FutureTask类。

查看Callable的源码:
```java
public interface Callable<V> {
    V call() throws Exception;
}
```
从上可知：是一个返回结果是泛型。


查看Future接口的源码：

```java
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```
大体解释下5个方法：

- cancel() : 用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，则表示可以取消正在执行过程中的任务。如果任务已经完成，则无论mayInterruptIfRunning为true还是false，此方法肯定返回false，即如果取消已经完成的任务会返回false；如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，则返回false；如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
- isCancelled() : 表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
- isDone() : 表示任务是否已经完成，若任务完成，则返回true；
- get()方法 : 用来获取执行结果，**这个方法会产生阻塞**，会一直等到任务执行完毕才返回；
- get(long timeout, TimeUnit unit) : 用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。


查看FutureTask的源码：

```
public class FutureTask<V> implements RunnableFuture<V> {
    
}

public interface RunnableFuture<V> extends Runnable, Future<V> {

    void run();
}
```
从上可以知, FutureTask是Runable接口和Future接口的实现类,具备取消和获取执行结果的特点。

---


### **Callable接口与Future接口 、线程池结合使用**

定义一个Callable的子类：

```java
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
```
启动线程，获取执行结果：
```java
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
```
输出结果：
```java
main 在执行启动任务 
pool-1-thread-1-->执行Callable的 call（）
```

---


### **Callable 与FutureTask类结合 线程一起 使用**
定义一个Callable的子类：

```java
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
```
启动线程，获取执行结果：
```java
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
```
输出结果：
```java
main 在执行启动任务 
异步线程 -->执行Callable的 call（）
```