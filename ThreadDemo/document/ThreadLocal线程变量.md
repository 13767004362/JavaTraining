ThreadLocal：
>ThreadLocal提供了线程的局部变量，每个线程都可以通过set()和get()来对这个局部变量进行操作，但不会和其他线程的局部变量进行冲突，实现了线程的数据隔离。


实战开发：

创建一个Bean对象：

```
public class DataBean {
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    public void increase(){
        i++;
    }
    public static DataBean newInstance(){
        return  new DataBean();
    }
}

```

创建静态的ThreadLocal变量

```
public class ThreadLocalUtils {
    private  static final ThreadLocal<DataBean> beanThreadLocal=new ThreadLocal<DataBean>(){
        @Override
        protected DataBean initialValue() {
           DataBean bean= DataBean.newInstance();
            System.out.println(" ThreadLocal "+" initialValue() ,创建bean对象："+bean);
            return  bean;
        }
    };
    public static  final  DataBean getDataBean(){
        return  beanThreadLocal.get();
    }
}
```

创建线程：
```
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
```


客户端测试：
```
public class Client {
    public static void main(String[] args) {
        testThreadLocal();
    }
    /**
     * 测试ThreadLocal
     */
    private static void testThreadLocal() {
        new com.xingen.threaddemo.thradlocal.WorkThread(" WorkThread1").start();
        new com.xingen.threaddemo.thradlocal.WorkThread(" WorkThread2").start();
    }
}
```

控制台输出结果：
```
 ThreadLocal  initialValue() ,创建bean对象：com.xingen.threaddemo.thradlocal.DataBean@7f5b7c1b
 ThreadLocal  initialValue() ,创建bean对象：com.xingen.threaddemo.thradlocal.DataBean@6299d4f3
 WorkThread1 1
 WorkThread1 2
 WorkThread1 3
 WorkThread1 4
 WorkThread1 5
 WorkThread2 1
 WorkThread2 2
 WorkThread2 3
 WorkThread2 4
 WorkThread2 5
```