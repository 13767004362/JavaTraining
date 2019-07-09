# **线程锁之Volatile锁**

**volatile特性的理解**：

对volatile变量的单个读/写，看成是使用同一个锁对这些单个读/写操作做了同步。

通过一个简单例子，进一步理解其特性:
```
public class VolatileBean {
    private  volatile  long vl=0L;
    
    public long getVl() {
        return vl;
    }
    public void setVl(long vl) {
        this.vl = vl;
    }
    public void increase(){
        vl++;
    }
}
```
以上多线程调用其中的方法，等价于以下代码：
```
public class VolatileBean {
    private  long vl=0L;
    public synchronized long getVl() {
        return vl;
    }
    public synchronized void setVl(long vl) {
        this.vl = vl;
    }
    public void increase(){
            long temp=getVl();//调用同步的读方法
            temp+=1L;
            setVL(temp);//调用同步的写方法
    }
    
```

**锁的happens-before规则**：保证锁的释放和获取的两个线程之间的内存可见性。这意味着，对一个volatile变量的读，总是能看到任意线程对这个volatile变量最后的写入。


**volatile的作用**：

- 可见性：对于一个volatile变量的读，总是能看到(任意线程)对这个volatile变量的最后的写入。
- 原子性： 对任意单个volatile变量的读/写具备原子性，但类似于**volatile++这种复合操作不具备原子性**

**实战开发**

创建volatile修饰的变量：
```
public class VolatileBean {
    private  volatile  long vl=0L;
    public void setVl(long vl) {
        this.vl = vl;
    }
    public long getVl() {
        return vl;
    }
}
```
创建执行输出任务的线程：
```
public class WorkThread extends Thread {
    private VolatileBean volatileBean;
    public WorkThread(VolatileBean volatileBean) {
        this.volatileBean = volatileBean;
    }

    @Override
    public void run() {
        super.run();

        while (volatileBean.getVl()!=1){
            System.out.println(WorkThread.class.getSimpleName()+" 执行任务 ");
            try {
                this.sleep(500);
            }catch (Exception e){

            }
        }
        System.out.println(WorkThread.class.getSimpleName()+" 获取到volatile变量的值 "+volatileBean.getVl());
    }
}
```

客户端测试：
```
public class Client {
    public static void main(String[] args) {
        new Client().testVolatile();
    }
   private void testVolatile(){
       VolatileBean bean=new VolatileBean();
       new WorkThread(bean).start();
       try {
           Thread.currentThread().sleep(1000);
       }catch (Exception e){
           e.printStackTrace();
       }
       bean.setVl(1);
       System.out.println(Thread.currentThread().getName()+" 修改volatile变量的值 "+1);
   }
    
}
```

控制台输出结果：
```
WorkThread 执行任务 
WorkThread 执行任务 
main 修改volatile变量的值 1
WorkThread 获取到volatile变量的值 1
```