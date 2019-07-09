package com.xingen.classdemo;

import com.xingen.classdemo.factory.Factory;
import com.xingen.classdemo.factory.TesFactoryTask;
import com.xingen.classdemo.factory.Worker;
import com.xingen.classdemo.genericity.GenericityInterfaceImp;
import com.xingen.classdemo.proxy.ForeignProduct;
import com.xingen.classdemo.proxy.Product;
import com.xingen.classdemo.proxy.ProductHandler;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by ${新根} on 2018/2/14 0014.
 * 博客：http://blog.csdn.net/hexingen
 * <p>
 * 反射的作用：通过反射可以获取到类的属性和方法
 * <p>
 * 反射的核心概念：一切的操作都是讲使用Object完成，类或者数组的引用是可以用Object进行接收。
 */
public class Client {

    public static void main(String[] args) {
        useGenericity();
        useClassInstance();
        useClassArray();

        useClassInstance("xinGen","Java Developer");
    }

    /**
     * 创建Class类实例对象的三种方式
     */
    private static void createClassInstance() {
        ClassTest1.createClassInstance1();
        ClassTest1.createClassInstance2();
        ClassTest1.createClassInstance3();

    }

    /**
     * 使用Class类实例对象：去创建另外一个类的对象
     */
    public static void useClassInstance() {
        ClassTest2 test2 = ClassTest2.createDefaultInstance();
        test2.setName("xinGen");
        test2.setWork("Android Library ClassTestInterface");
        System.out.println("使用反射创建一个类的对象 \n" + test2.toString());
    }

    /**
     * 使用Class类实例对象：去创建另外一个类的对象,且传入构造参数
     *
     * @param name
     * @param work
     */
    public static void useClassInstance(String name, String work) {
        ClassTest2 test2 = ClassTest2.createInstance(name, work);
        System.out.println("使用反射创建一个类的对象 \n" + test2.toString());
    }

    /**
     * 使用Class类：读写属性和方法
     */
    public static void useClassFieldAndMethod() {
        ClassTest3.testInterface();
        ClassTest3.testSuperClass();
        ClassTest3.testSuperMethod();
        ClassTest3.testSelfField();
        ClassTest3.testSuperField();
    }

    /**
     * 使用Array反射操作数组
     */
    public static void useClassArray() {
        ClassTest4.testArray();
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

    /**
     * 反射工厂模式,考虑IO流用异步
     */
    public static void useFactory() {
        TesFactoryTask.test();
    }

    /**
     * 反射泛型信息
     */
    public static  void useGenericity(){
        GenericityInterfaceImp imp=GenericityInterfaceImp.newInstance();
        System.out.println("反射接口中泛型参数： K是 "+imp.getkClass().getName()+" T 是："+imp.gettClass().getName()+" R 是："+imp.getRClass());
    }

}
