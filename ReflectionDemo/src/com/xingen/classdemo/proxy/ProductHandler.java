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
