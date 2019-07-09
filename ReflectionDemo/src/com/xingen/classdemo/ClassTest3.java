package com.xingen.classdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ${新根} on 2018/2/14 0014.
 * 博客：http://blog.csdn.net/hexingen
 * <p>
 * 代码演示： 反射获取类的方法和属性，和调用继承父类或者实现接口它们本身的属性和方法。
 */
public class ClassTest3 extends ClassTest2 implements ClassTestInterface {

    private int age;

    public ClassTest3() {
    }

    @Override
    public void testPrint() {
        System.out.println(" testPrint():反射调用继承或者实现的方法 ");
    }

    @Override
    public void testMethod(String name, String work) {
        this.setName(name);
        this.setWork(work);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (getName() != null) {
            stringBuffer.append("name: ");
            stringBuffer.append(getName());
            stringBuffer.append("\n");
        }
        if (getWork() != null) {
            stringBuffer.append("work: ");
            stringBuffer.append(getWork());
            stringBuffer.append("\n");
        }
        if (getAge() > 0) {
            stringBuffer.append("age: ");
            stringBuffer.append(getAge());
        }
        return stringBuffer.toString();
    }

    /**
     * 获取实现接口的信息
     */
    public static void testInterface() {
        try {
            Class<?> mClass = ClassTest3.class;
            /**
             * 获得全部实现的接口：Class<?>[] getInterfaces()得到的数组中，接口对象顺序和这个对象所表示的类中implements子句中接口名的顺序，是一致的。
             */
            Class<?>[] interfaceArray = mClass.getInterfaces();
            Class<?> interfaces = interfaceArray[0];
            System.out.println("获取实现接口的包路径： \n" + interfaces.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取父类的信息
     */
    public static void testSuperClass() {
        try {
            Class<?> mClass = ClassTest3.class;
            //获取继承的父类
            Class<?> superClass = mClass.getSuperclass();
            System.out.println("获取继承父类的包路径：\n" + superClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试覆盖重写的方法
     */
    public static void testSuperMethod() {
        try {
            Class<ClassTest3> mClass = ClassTest3.class;
            ClassTest3 instance = mClass.newInstance();
            Method method = mClass.getDeclaredMethod("testMethod", String.class, String.class);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(instance, "xinGen", "Android Lirary Developer");
            System.out.println("反射访问覆盖重写的方法：\n " + instance.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试调用本身定义的方法
     */
    public static void testSelfField() {
        try {
            Class<ClassTest3> mClass = ClassTest3.class;
            ClassTest3 instance = mClass.newInstance();
            Field field = mClass.getDeclaredField("age");
            field.setAccessible(true);
            field.set(instance, 24);
            System.out.println("反射访问本类中Private修饰的属性：\n " + instance.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 测试调用本身定义的方法
     */
    public static void testSuperField() {
        try {
            Class<ClassTest3> mClass = ClassTest3.class;
            ClassTest3 instance = mClass.newInstance();
            Field field = mClass.getSuperclass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(instance, "xinGen");
            System.out.println("反射访问父类中Private修饰的属性：\n " + instance.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
