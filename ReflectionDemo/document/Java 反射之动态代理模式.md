
Java中动态代理模式主要是通过InvocationHandler类和Proxy类来实现，创建一个代理对象，去实现代理的功能，从而动态代理功能。

**大致思路**：

- **创建代理者**：通过Proxy类中`newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)`反射创建一个代理者。

- **代理者与被代理者的交互**：当代理者需要调用一个行为时候，通知到InvocationHandler类，InvocationHandler对象会调用被代理者的对应的行为，从而实现代理者与被代理者的交互操作。



**实战案例**
---
生活中很常见的一个消费行为，消费者，代购人员，国外厂商三者可以构建一个代理场景。


**1. 代购人员和国外厂商共性，拿出海外产品**

先定义一个接口，定义一个产生产品的行为，花钱消费，得到商品。
```
package com.xingen.classdemo.proxy;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public interface Product {
    /**
     * 花钱购买商品
     * @param money
     * @return
     */
    public String buy(double money);
}
```
**2. 国外厂商，生产海外产品**

一个接口的实现类，实际干活的家伙，也就是被代理的对象。

```
package com.xingen.classdemo.proxy;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 *
 *  真实的被代理者，案例演示：海外购
 */
public class ForeignProduct implements Product{
    @Override
    public String buy(double money) {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("花费");
        stringBuffer.append(money);
        stringBuffer.append("美元代购国外产品");
        return stringBuffer.toString();
    }
    public static ForeignProduct newInstance(){
        return new ForeignProduct();
    }
}
```

**3. 代购人员和国外厂商的联系点**：

一个Handler类用于创建代理对象，调用代理对象的方法时候，Handler类将调用被代理的对象的方法。


```
package com.xingen.classdemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 */
public class ProductHandler implements InvocationHandler {
    /**
     * 被代理者，即真实操作者。
     */
    private  Object object;

    /**
     *
     * @param object 传入被代理者
     * @return 返回代理者
     */
    public  Object bind(Object object){
        this.object=object;
        //类加载器
        ClassLoader classLoader=object.getClass().getClassLoader();
        //获取实现的接口
        Class<?>[] interfaces= object.getClass().getInterfaces();
        //创建代理者
        Object proxyObject= Proxy.newProxyInstance(classLoader,interfaces,this);
        return proxyObject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //通过反射中Method类，读写被代理者中的方法，返回结果
        Object returnMessage=method.invoke(this.object,args);
        return returnMessage;
    }
    public  static  ProductHandler newInstance(){
        return new ProductHandler();
    }
}
```

**4. Client客户，也就是消费者 , 下单操作**。
```
public class Client {

    public static void main(String[] args) {
        useProxy();
    }
    
    /**
     * 反射动态代理
     */
    public static void useProxy() {
        ProductHandler handler = ProductHandler.newInstance();
        //创建代理者，类似海外代购员
        Product product = (Product) handler.bind(ForeignProduct.newInstance());
        //进行代购操作，返回需要的商品结果
        String result = product.buy(2.5);
        
        System.out.println("反射动态代理：\n" + result);
    }
    
}
```

**5. 最后,消费者看到海外产品到手中**

控制台输出结果是：

```
反射动态代理：

花费2.5美元代购国外产品

```

