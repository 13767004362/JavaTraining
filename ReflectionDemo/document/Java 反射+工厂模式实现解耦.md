在实际开发中，工厂模式是经常被用到的。

工厂模式的好处：

>工厂模式可以达到类的解耦。

工厂类中工厂方法过多也是个问题，每新增一个类，则新增一个工厂方法，这会导致工厂方法过多。恰好，反射可以创建类的实例对象，而且可以采用统一操作`Class.forName()`产生方法，而不会新增工厂方法。

**案例实战**
---

以公司程序员开发业务模块为场景，一个Java程序员开发Java Library，一个Android程序员造轮子，各自负责相应的工作。


**1. 定义程序员的工作行为**

每个程序员的工作是共同行为，但不确定具体的工作内容，因此抽象一个`work()`方法。
```
package com.xingen.classdemo.factory;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public interface Worker {
    void work();
}
```

**2. 多个程序员快马加鞭的工作**：

一个android开发人员，专业造轮子

```
package com.xingen.classdemo.factory;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public class AndroidDeveloper implements Worker {
    private final  String TAG=AndroidDeveloper.class.getSimpleName();
    public AndroidDeveloper() {
    }

    @Override
    public void work() {
        System.out.println(TAG+" 工作是：android 造轮子");
    }
}
```

一个Java开发人员，专业造轮子
```
package com.xingen.classdemo.factory;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public class JavaDeveloper implements Worker {
    private final String TAG=JavaDeveloper.class.getSimpleName();
    public JavaDeveloper() {
    }

    @Override
    public void work() {
        System.out.println(TAG+" 工作是：Java 造轮子");
    }
}
```
**3. 经理抽调程序员，通知那些程序员负责**

定义一个工厂类，专门创建类的实例化对象

```
package com.xingen.classdemo.factory;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public class Factory {
    public static Worker newInstance(String className){
        Worker worker=null;
        try {
          Class< Worker> mClass=  (Class< Worker>)Class.forName(className);
          worker=mClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return worker;
    }
}
```
**4. 项目管控的文档，记录各个模块的负责人**

在src文件夹下，创建一个配置文件load.properties。

```
android=com.xingen.classdemo.factory.AndroidDeveloper
java=com.xingen.classdemo.factory.JavaDeveloper
```
**5. 业务项目计划，从开启到顺利结尾的实施过程**

在`main()`程序入口：

```
public class Client {

    public static void main(String[] args) {
        useFactory();
    }
    
      /**
     * 反射工厂模式,考虑IO流用异步
     */
    public static void useFactory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    // 单个/代表当前目录下,详情参考：http://riddickbryant.iteye.com/blog/436693
                    String filePath = "/load.properties";
                    Properties properties = new Properties();
                    inputStream = Client.class.getResourceAsStream(filePath);
                    //加载属性文件
                    properties.load(inputStream);
                    //根据属性文件进行，创建实例
                    Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
                    while (enumeration.hasMoreElements()) {
                        String key = enumeration.nextElement();
                        String values = properties.getProperty(key);
                        Worker worker = Factory.newInstance(values);
                        worker.work();
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
}
```
**6. 公司高层看到业务结果**：

高层看到程序员带来的盈利，笑呵呵!

控制台输出的结果：
```
AndroidDeveloper 工作是：android 造轮子

JavaDeveloper 工作是：Java 造轮子
```

